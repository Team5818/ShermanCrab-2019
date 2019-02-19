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

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import org.rivierarobotics.commands.ArmControl;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;

@Singleton
public class ArmController extends Subsystem {
    private Provider<ArmControl> command;
    private WPI_TalonSRX arm;
    private CANSparkMax sparkSlaveOne;
    private CANSparkMax sparkSlaveTwo;
    private static final int TIMEOUT = 30;
    private static final double P;
    private static final double I;
    private static final double D;
    private static final double F;
    private static final int SLOT_IDX = 0;
    private static final int PID_LOOP_IDX = 0;
    private static final int VELOCITY_TICKS_PER_100MS;
    private static final int ACCELERATION_TICKS_PER_100MS_PER_SEC;
    private static final int VELOCITY_TICKS_PER_SEC = 1;
    private static final int ACCELERATION_TICKS_PER_SEC_PER_SEC = 1;
    private static double TICKS_TO_DEGREES;
    private static final int TICK_BUFFER = 1185;

    private static SimpleWidget ezWidget(String name, Object def) {
        return Shuffleboard.getTab("Arm Controller").addPersistent(name, def);
    }

    static {
        TICKS_TO_DEGREES = ezWidget("Ticks to Degrees", 1).getEntry().getDouble(1);
        System.err.println("Ticks to Degrees: " + TICKS_TO_DEGREES);

        P = ezWidget("P", 0.2).getEntry().getDouble(0.2);
        System.err.println("P: " + P);

        I = ezWidget("I", 0.0).getEntry().getDouble(0);
        System.err.println("I: " + I);

        D = ezWidget("D", 0.0).getEntry().getDouble(0);
        System.err.println("D: " + D);

        F = ezWidget("F", 0.2).getEntry().getDouble(0.2);
        System.err.println("F: " + F);

        // CHANGE UNITS STUFF
        VELOCITY_TICKS_PER_100MS = VELOCITY_TICKS_PER_SEC * 10;
        System.err.println("velocity: " + VELOCITY_TICKS_PER_100MS);

        ACCELERATION_TICKS_PER_100MS_PER_SEC = ACCELERATION_TICKS_PER_SEC_PER_SEC * 10;
        System.err.println("accel: " + ACCELERATION_TICKS_PER_100MS_PER_SEC);
    }
    /*
    private final Notifier followerThread = new Notifier(() -> {
        double volts = -arm.getMotorOutputVoltage();
        SparkMaxVolts.set(sparkSlaveOne, volts);
        SparkMaxVolts.set(sparkSlaveTwo, volts);
    });*/

    @Inject
    public ArmController(Provider<ArmControl> command, int master, int slaveOne, int slaveTwo) {
        arm = new WPI_TalonSRX(master);
        sparkSlaveOne = new CANSparkMax(slaveOne, CANSparkMaxLowLevel.MotorType.kBrushless);
        sparkSlaveTwo = new CANSparkMax(slaveTwo, CANSparkMaxLowLevel.MotorType.kBrushless);

        //followerThread.startPeriodic(0.01);
        sparkSlaveOne.follow(CANSparkMax.ExternalFollower.kFollowerPhoenix, master, false);
        sparkSlaveTwo.follow(CANSparkMax.ExternalFollower.kFollowerPhoenix, master, false);

        arm.setNeutralMode(NeutralMode.Brake);
        sparkSlaveOne.setIdleMode(CANSparkMax.IdleMode.kBrake);
        sparkSlaveTwo.setIdleMode(CANSparkMax.IdleMode.kBrake);

        arm.setInverted(true);

        /* Reset encoder before reading values */
        arm.setSelectedSensorPosition(0);

        /* Factory default hardware to prevent unexpected behavior */
        arm.configFactoryDefault();

        /* Configure Sensor Source for Primary PID */
        arm.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, PID_LOOP_IDX, TIMEOUT);

        /* Set relevant frame periods to be at least as fast as periodic rate */
        arm.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, TIMEOUT);

        /* Set the peak and nominal outputs */
        arm.configNominalOutputForward(0, TIMEOUT);
        arm.configNominalOutputReverse(0, TIMEOUT);
        arm.configPeakOutputForward(1, TIMEOUT);
        arm.configPeakOutputReverse(-1, TIMEOUT);

        /* Set Motion Magic gains in slot0 - see documentation */
        arm.selectProfileSlot(SLOT_IDX, PID_LOOP_IDX);
        arm.config_kF(SLOT_IDX, F * 1023, TIMEOUT);
        arm.config_kP(SLOT_IDX, P * 1023, TIMEOUT);
        arm.config_kI(SLOT_IDX, I * 1023, TIMEOUT);
        arm.config_kD(SLOT_IDX, D * 1023, TIMEOUT);

        arm.configMotionCruiseVelocity(VELOCITY_TICKS_PER_100MS, TIMEOUT);
        arm.configMotionAcceleration(ACCELERATION_TICKS_PER_100MS_PER_SEC, TIMEOUT);
        this.command = command;
    }

    public void setAngle(double angle) {
        arm.set(ControlMode.MotionMagic, angle);
    }

    public int getAngle() {
        return (int)((arm.getSensorCollection().getPulseWidthPosition() + TICK_BUFFER) / TICKS_TO_DEGREES);
    }

    public void setPower(double pwr) {
        arm.set(pwr);
    }

    public void stop() {
        setPower(0.0);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(command.get());
    }
}