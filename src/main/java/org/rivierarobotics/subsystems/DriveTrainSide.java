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

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import org.rivierarobotics.util.AbstractPIDSource;

public class DriveTrainSide {
    private static final double INCHES_TO_TICKS;
    private static final double P;
    private static final double I;
    private static final double D;
    private static final double F;
    private static final int VELOCITY_INCHES_PER_SEC = 1;
    private static final int ACCELERATION_INCHES_PER_SEC_PER_SEC = 1;
    private static final int VELOCITY_TICKS_PER_100MS;
    private static final int ACCELERATION_TICKS_PER_100MS_PER_SEC;

    private static SimpleWidget ezWidget(String name, Object def) {
        return Shuffleboard.getTab("Drive Train").addPersistent(name, def);
    }

    static {
        INCHES_TO_TICKS = ezWidget("Inches to Ticks", 1).getEntry().getDouble(1);
        System.err.println("Inches to Ticks: " + INCHES_TO_TICKS);

        P = ezWidget("P", 0.2).getEntry().getDouble(0.2);
        System.err.println("P: " + P);

        I = ezWidget("I", 0.0).getEntry().getDouble(0);
        System.err.println("I: " + I);

        D = ezWidget("D", 0.0).getEntry().getDouble(0);
        System.err.println("D: " + D);

        F = ezWidget("F", 0.2).getEntry().getDouble(0.2);
        System.err.println("F: " + F);

        VELOCITY_TICKS_PER_100MS = (int) (VELOCITY_INCHES_PER_SEC * INCHES_TO_TICKS * 10);
        System.err.println("velocity: " + VELOCITY_TICKS_PER_100MS);

        ACCELERATION_TICKS_PER_100MS_PER_SEC = (int) (ACCELERATION_INCHES_PER_SEC_PER_SEC * INCHES_TO_TICKS * 10);
        System.err.println("accel: " + ACCELERATION_TICKS_PER_100MS_PER_SEC);
    }

    private WPI_TalonSRX talonMaster;
    private CANSparkMax sparkSlaveOne;
    private CANSparkMax sparkSlaveTwo;
    private int distanceInvert;
    private PIDController pidLoop;

    public DriveTrainSide(int master, int slaveOne, int slaveTwo, boolean invert) {
        talonMaster = new WPI_TalonSRX(master);
        sparkSlaveOne = new CANSparkMax(slaveOne, CANSparkMaxLowLevel.MotorType.kBrushless);
        sparkSlaveTwo = new CANSparkMax(slaveTwo, CANSparkMaxLowLevel.MotorType.kBrushless);
        if (invert) {
            distanceInvert = -1;
        } else {
            distanceInvert = 1;
        }

        talonMaster.setInverted(invert);

        sparkSlaveOne.follow(CANSparkMax.ExternalFollower.kFollowerPhoenix, master, true);
        sparkSlaveTwo.follow(CANSparkMax.ExternalFollower.kFollowerPhoenix, master, true);

        pidLoop = new PIDController(P, I, D, F, new AbstractPIDSource(this::getTicks), this::setMotorPower);
    }

    public double getDistance() {
        return getTicks() / INCHES_TO_TICKS;
    }

    public int getTicks() {
        return talonMaster.getSensorCollection().getQuadraturePosition() * distanceInvert;
    }

    public void addDistance(double inches) {
        double ticks = inches * INCHES_TO_TICKS;
        pidLoop.setSetpoint(getTicks() + ticks);
        pidLoop.enable();
    }

    public void setPower(double pwr) {
        pidLoop.disable();
        setMotorPower(pwr);
    }

    private void setMotorPower(double pwr) {
        talonMaster.set(pwr);
    }


}
