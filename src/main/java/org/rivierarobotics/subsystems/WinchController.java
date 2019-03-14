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
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import org.rivierarobotics.util.AbstractPIDSource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WinchController extends Subsystem {
    private WPI_TalonSRX winch;

    private static final double P;
    private static final double I;
    private static final double D;
    private static final double F;
    private static final int VELOCITY_TICKS_PER_100MS;
    private static final int ACCELERATION_TICKS_PER_100MS_PER_SEC;
    private static final int VELOCITY_TICKS_PER_SEC = 1;
    private static final int ACCELERATION_TICKS_PER_SEC_PER_SEC = 1;
    private PIDController pidLoop;

    private static SimpleWidget ezWidget(String name, Object def) {
        return Shuffleboard.getTab("Winch Controller").addPersistent(name, def);
    }

    static {
        P = ezWidget("P", 0.0005).getEntry().getDouble(0.0005);
        System.err.println("P: " + P);

        I = ezWidget("I", 0).getEntry().getDouble(0);
        System.err.println("I: " + I);

        D = ezWidget("D", 0.0).getEntry().getDouble(0);
        System.err.println("D: " + D);

        F = ezWidget("F", 0.0).getEntry().getDouble(0);
        System.err.println("F: " + F);

        // CHANGE UNITS STUFF
        VELOCITY_TICKS_PER_100MS = VELOCITY_TICKS_PER_SEC * 10;
        System.err.println("velocity: " + VELOCITY_TICKS_PER_100MS);

        ACCELERATION_TICKS_PER_100MS_PER_SEC = ACCELERATION_TICKS_PER_SEC_PER_SEC * 10;
        System.err.println("accel: " + ACCELERATION_TICKS_PER_100MS_PER_SEC);
    }

    @Inject
    public WinchController(int ch) {
        winch = new WPI_TalonSRX(ch);
        winch.setInverted(false);
        winch.setNeutralMode(NeutralMode.Coast);

        pidLoop = new PIDController(P, I, D, F, new AbstractPIDSource(this::getAngle), this::rawSetPower, 0.01);

    }

    public void setAngle(double angle) {
        winch.setNeutralMode(NeutralMode.Brake);
        pidLoop.setSetpoint(angle);
        pidLoop.enable();
    }

    public int getAngle() {
        return winch.getSensorCollection().getPulseWidthPosition();
    }

    public void setPower(double pwr) {
        if (pwr != 0 && pidLoop.isEnabled()) {
            pidLoop.disable();
        }
        if (!pidLoop.isEnabled()) {
            rawSetPower(pwr);
        }
    }

    private void rawSetPower(double pwr) {
        winch.set(pwr);
    }

    public void stop() {
        if (pidLoop.isEnabled()) {
            pidLoop.disable();
        }
        pidLoop.setSetpoint(getAngle());
        pidLoop.enable();
        winch.setNeutralMode(NeutralMode.Brake);
    }

    public PIDController getPIDLoop() {
        return pidLoop;
    }

    public WPI_TalonSRX getWinch() {
        return winch;
    }

    @Override
    protected void initDefaultCommand() {

    }
}