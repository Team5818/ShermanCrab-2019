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
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

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

    private WPI_TalonSRX motorEnc;
    private WPI_TalonSRX motorZed;

    public DriveTrainSide(int enc, int zed, boolean invert) {
        motorEnc = new WPI_TalonSRX(enc);
        motorZed = new WPI_TalonSRX(zed);

        /* Reset encoder before reading values */
        motorEnc.setSelectedSensorPosition(0);

        /* Factory default hardware to prevent unexpected behavior */
        motorEnc.configFactoryDefault();
        motorZed.configFactoryDefault();
        motorZed.follow(motorEnc);
        motorEnc.setSensorPhase(!invert);
        motorEnc.setInverted(invert);
        motorZed.setInverted(InvertType.FollowMaster);

        /* Configure Sensor Source for Primary PID */
        motorEnc.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_LOOP_IDX, TIMEOUT);

        /* Set relevant frame periods to be at least as fast as periodic rate */
        motorEnc.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, TIMEOUT);

        /* Set the peak and nominal outputs */
        motorEnc.configNominalOutputForward(0, TIMEOUT);
        motorEnc.configNominalOutputReverse(0, TIMEOUT);
        motorEnc.configPeakOutputForward(1, TIMEOUT);
        motorEnc.configPeakOutputReverse(-1, TIMEOUT);

        /* Set Motion Magic gains in slot0 - see documentation */
        motorEnc.selectProfileSlot(SLOT_IDX, PID_LOOP_IDX);
        motorEnc.config_kF(SLOT_IDX, F * 1023, TIMEOUT);
        motorEnc.config_kP(SLOT_IDX, P * 1023, TIMEOUT);
        motorEnc.config_kI(SLOT_IDX, I * 1023, TIMEOUT);
        motorEnc.config_kD(SLOT_IDX, D * 1023, TIMEOUT);

        motorEnc.configMotionCruiseVelocity(VELOCITY, TIMEOUT);
        motorEnc.configMotionAcceleration(ACCELERATION, TIMEOUT);
    }

    public double getDistance() {
        int ticks = motorEnc.getSensorCollection().getQuadraturePosition();
        return ticks / INCHES_TO_TICKS;
    }

    public void setDistance(double inches) {
        motorEnc.set(ControlMode.MotionMagic, inches * INCHES_TO_TICKS);
    }

    public void setVelocity(double vel) {
        motorEnc.set(ControlMode.Velocity, (vel * INCHES_TO_TICKS) / 10);
    }

    public void setPower(double pwr) {
        motorEnc.set(pwr);
    }
}
