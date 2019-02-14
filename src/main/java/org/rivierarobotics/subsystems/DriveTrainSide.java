/*
 * This file is part of Placeholder-2019, licensed under the GNU General Public License (GPLv3).
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
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;

public class DriveTrainSide {
    private static final double INCHES_TO_TICKS;
    private static final double P;
    private static final double I;
    private static final double D;
    private static final double F;
    private static final int VELOCITY;
    private static final int ACCELERATION;
    private static final int SLOT_IDX = 0;
    private static final int PID_LOOP_IDX = 0;
    private static final int TIMEOUT = 30;

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

        VELOCITY = (int) ezWidget("Velocity", 0).getEntry().getDouble(0);
        System.err.println("velocity: " + VELOCITY);

        ACCELERATION = (int) ezWidget("Accel", 0).getEntry().getDouble(0);
        System.err.println("accel: " + ACCELERATION);
    }

    private WPI_TalonSRX talonMaster;
    private CANSparkMax sparkSlaveOne;
    private CANSparkMax sparkSlaveTwo;

    public DriveTrainSide(int master, int slaveOne, int slaveTwo, boolean invert) {
        talonMaster = new WPI_TalonSRX(master);
        sparkSlaveOne = new CANSparkMax(slaveOne, CANSparkMaxLowLevel.MotorType.kBrushless);
        sparkSlaveTwo = new CANSparkMax(slaveTwo, CANSparkMaxLowLevel.MotorType.kBrushless);

        /* Reset encoder before reading values */
        talonMaster.setSelectedSensorPosition(0);

        /* Factory default hardware to prevent unexpected behavior */
        talonMaster.configFactoryDefault();

        /* Set master Talon inversion */
        talonMaster.setSensorPhase(!invert);
        talonMaster.setInverted(!invert);

        /* Get Sparks to follow master Talon */
        // TODO confirm that kFollowerPhoenix is correct, not kFollowerSparkMax
        sparkSlaveOne.follow(CANSparkMax.ExternalFollower.kFollowerPhoenix, master, true);
        sparkSlaveTwo.follow(CANSparkMax.ExternalFollower.kFollowerPhoenix, master, true);

        /* Configure Sensor Source for Primary PID */
        talonMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_LOOP_IDX, TIMEOUT);

        /* Set relevant frame periods to be at least as fast as periodic rate */
        talonMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, TIMEOUT);

        /* Set the peak and nominal outputs */
        talonMaster.configNominalOutputForward(0, TIMEOUT);
        talonMaster.configNominalOutputReverse(0, TIMEOUT);
        talonMaster.configPeakOutputForward(1, TIMEOUT);
        talonMaster.configPeakOutputReverse(-1, TIMEOUT);

        /* Set Motion Magic gains in slot0 - see documentation */
        talonMaster.selectProfileSlot(SLOT_IDX, PID_LOOP_IDX);
        talonMaster.config_kF(SLOT_IDX, F * 1023, TIMEOUT);
        talonMaster.config_kP(SLOT_IDX, P * 1023, TIMEOUT);
        talonMaster.config_kI(SLOT_IDX, I * 1023, TIMEOUT);
        talonMaster.config_kD(SLOT_IDX, D * 1023, TIMEOUT);

        talonMaster.configMotionCruiseVelocity(VELOCITY, TIMEOUT);
        talonMaster.configMotionAcceleration(ACCELERATION, TIMEOUT);
    }

    public double getDistance() {
        int ticks = talonMaster.getSensorCollection().getQuadraturePosition();
        return ticks / INCHES_TO_TICKS;
    }

    public void setDistance(double inches) {
        talonMaster.set(ControlMode.MotionMagic, inches * INCHES_TO_TICKS);
    }

    public void setVelocity(double vel) {
        talonMaster.set(ControlMode.Velocity, (vel * INCHES_TO_TICKS) / 10);
    }

    public void setPower(double pwr) {
        talonMaster.set(pwr);
    }
}
