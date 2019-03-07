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

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
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

    private static final double P;
    private static final double I;
    private static final double D;
    private static final double F;
    private static final int VELOCITY_TICKS_PER_100MS;
    private static final int ACCELERATION_TICKS_PER_100MS_PER_SEC;
    private static final int VELOCITY_TICKS_PER_SEC = 1;
    private static final int ACCELERATION_TICKS_PER_SEC_PER_SEC = 1;
    private static double TICKS_TO_DEGREES;

    private static SimpleWidget ezWidget(String name, Object def) {
        return Shuffleboard.getTab("Hood Controller").addPersistent(name, def);
    }

    static {
        TICKS_TO_DEGREES = ezWidget("Ticks to Degrees", 1).getEntry().getDouble(1);
        System.err.println("Ticks to Degrees: " + TICKS_TO_DEGREES);

        P = ezWidget("P", 0.01).getEntry().getDouble(0.01);
        System.err.println("P: " + P);

        I = ezWidget("I", 0.0).getEntry().getDouble(0);
        System.err.println("I: " + I);

        D = ezWidget("D", 0.0).getEntry().getDouble(0);
        System.err.println("D: " + D);

        F = ezWidget("F", 0.0).getEntry().getDouble(0.0);
        System.err.println("F: " + F);

        // CHANGE UNITS STUFF
        VELOCITY_TICKS_PER_100MS = VELOCITY_TICKS_PER_SEC / 10;
        System.err.println("velocity: " + VELOCITY_TICKS_PER_100MS);

        ACCELERATION_TICKS_PER_100MS_PER_SEC = ACCELERATION_TICKS_PER_SEC_PER_SEC / 10;
        System.err.println("accel: " + ACCELERATION_TICKS_PER_100MS_PER_SEC);
    }

    @Inject
    public HoodController(Provider<HoodControl> command, int h) {
        hood = new WPI_TalonSRX(h);
        this.command = command;

        hood.setNeutralMode(NeutralMode.Brake);
        pidLoop = new PIDController(P, I, D, F, new AbstractPIDSource(this::getAngle), this::rawSetPower, 0.01);
        // pidLoop.setContinuous(true);
        pidLoop.setOutputRange(-0.4, 0.4);
    }

    public void setAngle(double angle) {
        pidLoop.setSetpoint(angle);
        pidLoop.enable();
    }

    public double getAngle() {
        return hood.getSensorCollection().getPulseWidthPosition();
    }

    public double getDegrees() {
        return (getAngle() / TICKS_TO_DEGREES);
    }

    public void setPower(double pwr) {
        if(pwr != 0) {
            pidLoop.disable();
        }

        if(!pidLoop.isEnabled()) {
            rawSetPower(pwr);
        }
    }

    public void rawSetPower(double pwr) {
        hood.set(pwr);
    }

    public PIDController getPIDLoop() {
        return pidLoop;
    }

    public void stop() {
        setPower(0.0);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(command.get());
    }
}
