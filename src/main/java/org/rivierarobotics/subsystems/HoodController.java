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

import org.rivierarobotics.util.Logging;
import org.rivierarobotics.util.MechLogger;
import org.rivierarobotics.util.MathUtil;


import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class HoodController extends Subsystem {
    private Provider<HoodControl> command;
    private final WPI_TalonSRX hood;
    private final ArmController armController;
    private PIDController pidLoop;
    private final MechLogger logger;

    private static final double P = 0.0006;
    private static final double I = 0;
    private static final double D = 0;
    private static final double F = 0;
    private static final double GRAVITY_CONSTANT = 0.13;
    public static double ANGLE_SCALE = 4096 / 360.0;
    public static HoodPosition CURRENT_HOOD_POSITION;
    public static boolean HOOD_FRONT = true;
    private static final NetworkTableEntry SETPOINT_ANGLE;
    private static final NetworkTableEntry PWR;


    private static SimpleWidget ezWidget(String name, Object def) {
        return Shuffleboard.getTab("Hood Controller").add(name, def);
    }

    static {
        SETPOINT_ANGLE = ezWidget("Setpoint Angle", 0).getEntry();
        PWR = ezWidget("Power", 0).getEntry();
    }

    @Inject
    public HoodController(ArmController armController, Provider<HoodControl> command, int h) {
        this.hood = new WPI_TalonSRX(h);
        this.armController = armController;
        this.command = command;
        this.logger = Logging.getLogger(getClass());

        /* Disables limit switches on malfunctioning encoder */
        hood.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
        hood.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);

        logger.conditionChange("neutral_mode", "brake");
        hood.setNeutralMode(NeutralMode.Brake);
        pidLoop = new PIDController(P, I, D, F, new AbstractPIDSource(this::getAngle), this::rawSetPower, 0.01);

        pidLoop.setOutputRange(-0.3, 0.3);
    }

    public void setAngle(double angle) {
        pidLoop.setSetpoint(angle);
        logger.setpointChange(angle);
        SETPOINT_ANGLE.setDouble(angle);
        pidLoop.enable();
        logger.conditionChange("pid_loop", "enabled");
    }

    public int getAngle() {
        return hood.getSensorCollection().getQuadraturePosition();
    }

    public double getDegrees() {
       return (getAngle() / ANGLE_SCALE) % 360;
    }

    public void setPower(double pwr) {
        PWR.setDouble(hood.getMotorOutputPercent());
        if (pwr != 0) {
            pidLoop.disable();
            logger.conditionChange("pid_loop", "disabled");
            logger.conditionChange("neutral_mode", "coast");
            hood.setNeutralMode(NeutralMode.Coast);
        } else {
            logger.conditionChange("neutral_mode", "brake");
            hood.setNeutralMode(NeutralMode.Brake);
        }

        if (!pidLoop.isEnabled()) {
            pwr += Math.sin(Math.toRadians(this.getDegrees() - Math.abs(armController.getDegrees()))) * GRAVITY_CONSTANT;
            rawSetPower(pwr);
        }
    }

    private void rawSetPower(double pwr) {
        pwr = MathUtil.limit(pwr, 0.8);
        logger.powerChange(pwr);
        hood.set(pwr);
    }

    public void resetQuadratureEncoder() {
        hood.getSensorCollection().setQuadraturePosition(0, 0);
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
