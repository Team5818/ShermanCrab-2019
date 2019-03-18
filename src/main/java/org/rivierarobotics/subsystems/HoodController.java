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

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.rivierarobotics.commands.HoodControl;
import org.rivierarobotics.util.AbstractPIDSource;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class HoodController extends Subsystem {
    private Provider<HoodControl> command;
    private final WPI_TalonSRX hood;
    private PIDController pidLoop;

    private static final double P = 0.0003;
    private static final double I = 0;
    private static final double D = 0;
    private static final double F = 0;
    private static boolean offsetDone = false;
    public int offset = 0;
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
    public HoodController(Provider<HoodControl> command, int h) {
        hood = new WPI_TalonSRX(h);
        this.command = command;

        hood.setNeutralMode(NeutralMode.Brake);
        pidLoop = new PIDController(P, I, D, F, new AbstractPIDSource(this::getAngle), this::rawSetPower, 0.01);

        //pidLoop.setContinuous(false);
        //OFFSET = getRestingZero();
        pidLoop.setOutputRange(-0.4, 0.4);
    }

    public void setAngle(double angle) {
        //pidLoop.setSetpoint(MathUtil.fitHoodRotation(angle, 0, MAX_ROT));
        pidLoop.setSetpoint(angle + offset);
        //SETPOINT_ANGLE.setDouble(angle + offset);
        pidLoop.enable();
    }

    public int getAngle() {
        //TODO [Regional] [Software] change to getQuadraturePosition() for quadrature/relative testing
        int angle = hood.getSensorCollection().getPulseWidthPosition();
        SmartDashboard.putNumber("hood encoder", angle);
        return angle;
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

    public void rawSetPower(double pwr) {
        hood.set(pwr);
    }

    public int getRestingZero() {
        if(offset == 0 && !offsetDone) {
            offsetDone = true;
            return getAngle();
        } else {
            return offset;
        }
    }

    public void resetQuadratureEncoder() {
        hood.getSensorCollection().setQuadraturePosition(MAX_ROT / 2,0);
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
