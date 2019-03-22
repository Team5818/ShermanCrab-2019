/*
 * This file is part of ShermanCrab-2019, licensed under the GNU General Public License (GPLv3).
 *
 * Copyright (c) Riviera Robotics <https://github.com/Team5818>
 * Copyright (c) contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.rivierarobotics.subsystems;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import org.rivierarobotics.commands.HoodControl;
import org.rivierarobotics.util.AbstractPIDSource;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class HoodController extends Subsystem {
    private Provider<HoodControl> command;
    private final WPI_TalonSRX hood;
    private final ArmController armController;
    private PIDController pidLoop;

    private static final double P = 0.0003;
    private static final double I = 0;
    private static final double D = 0;
    private static final double F = 0;
    private static final double GRAVITY_CONSTANT = -0.042;
    private static double ANGLE_SCALE;
    private static boolean offsetDone = false;
    public static int RESTING_ZERO = 0;
    /* Accounts for 6:11 chain ratio */
    public static int MAX_ROT = ((4096 * 11) / 12);
    private static final NetworkTableEntry SETPOINT_ANGLE;


    private static SimpleWidget ezWidget(String name, Object def) {
        return Shuffleboard.getTab("Hood Controller").add(name, def);
    }

    static {
        SETPOINT_ANGLE = ezWidget("Setpoint Angle", 0).getEntry();
    }

    @Inject
    public HoodController(ArmController armController, Provider<HoodControl> command, int h) {
        hood = new WPI_TalonSRX(h);
        this.armController = armController;
        this.command = command;

        /* Disables limit switches on malfunctioning encoder */
        hood.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
        hood.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);

        hood.setNeutralMode(NeutralMode.Brake);
        pidLoop = new PIDController(P, I, D, F, new AbstractPIDSource(this::getAngle), this::rawSetPower, 0.01);

        RESTING_ZERO = getRestingZero();
        ANGLE_SCALE = (180) / (HoodPosition.RESTING_ARM_ONE_HUNDRED_EIGHTY.ticksFront - RESTING_ZERO);
        pidLoop.setOutputRange(-0.4, 0.4);
    }

    public void setAngle(double angle) {
        pidLoop.setSetpoint(angle + RESTING_ZERO);
        SETPOINT_ANGLE.setDouble(angle + RESTING_ZERO);
        pidLoop.enable();
    }

    public int getAngle() {
        return hood.getSensorCollection().getQuadraturePosition();
    }

    public double getDegrees() {
        return (getAngle() - RESTING_ZERO) * ANGLE_SCALE;
    }

    public void setPower(double pwr) {
        if (pwr != 0) {
            pidLoop.disable();
            hood.setNeutralMode(NeutralMode.Coast);
        } else {
            hood.setNeutralMode(NeutralMode.Brake);
        }

        if (!pidLoop.isEnabled()) {
            rawSetPower(pwr);
        }
    }

    private void rawSetPower(double pwr) {
        pwr += Math.sin(Math.toRadians(180 - this.getDegrees() - (90 - armController.getDegrees()))) * GRAVITY_CONSTANT;
        hood.set(pwr);
    }

    public int getRestingZero() {
        if ((RESTING_ZERO == 0 || Math.abs(RESTING_ZERO) == HoodController.MAX_ROT) && !offsetDone) {
            offsetDone = true;
            return hood.getSensorCollection().getPulseWidthPosition();
        } else {
            return RESTING_ZERO;
        }
    }

    public void resetQuadratureEncoder() {
        hood.getSensorCollection().setQuadraturePosition(RESTING_ZERO, 0);
    }

    public PIDController getPIDLoop() {
        return pidLoop;
    }

    public WPI_TalonSRX getHood() {
        return hood;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(command.get());
    }
}
