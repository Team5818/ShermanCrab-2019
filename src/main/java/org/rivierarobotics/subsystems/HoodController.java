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
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import org.rivierarobotics.commands.ArmControl;
import org.rivierarobotics.commands.HoodControl;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class HoodController extends Subsystem {
    private Provider<HoodControl> command;
    private final WPI_TalonSRX rotate;
    private final WPI_TalonSRX spin;

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

    private static SimpleWidget ezWidget(String name, Object def) {
        return Shuffleboard.getTab("Hood Controller").addPersistent(name, def);
    }

    static {
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

    @Inject
    public HoodController(Provider<HoodControl> command, int rot, int spn) {
        rotate = new WPI_TalonSRX(rot);
        spin = new WPI_TalonSRX(spn);
        this.command = command;
        /* Spin should not follow rotate. They are two different things */

        /* Reset encoder before reading values */
        rotate.setSelectedSensorPosition(0);

        /* Factory default hardware to prevent unexpected behavior */
        rotate.configFactoryDefault();

        /* Configure Sensor Source for Primary PID */
        rotate.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_LOOP_IDX, TIMEOUT);

        /* Set relevant frame periods to be at least as fast as periodic rate */
        rotate.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, TIMEOUT);

        /* Set the peak and nominal outputs */
        rotate.configNominalOutputForward(0, TIMEOUT);
        rotate.configNominalOutputReverse(0, TIMEOUT);
        rotate.configPeakOutputForward(1, TIMEOUT);
        rotate.configPeakOutputReverse(-1, TIMEOUT);

        /* Set Motion Magic gains in slot0 - see documentation */
        rotate.selectProfileSlot(SLOT_IDX, PID_LOOP_IDX);
        rotate.config_kF(SLOT_IDX, F * 1023, TIMEOUT);
        rotate.config_kP(SLOT_IDX, P * 1023, TIMEOUT);
        rotate.config_kI(SLOT_IDX, I * 1023, TIMEOUT);
        rotate.config_kD(SLOT_IDX, D * 1023, TIMEOUT);

        rotate.configMotionCruiseVelocity(VELOCITY_TICKS_PER_100MS, TIMEOUT);
        rotate.configMotionAcceleration(ACCELERATION_TICKS_PER_100MS_PER_SEC, TIMEOUT);
    }

    public void setRotateAngle(double angle) {
        rotate.set(ControlMode.MotionMagic, angle);
    }

    public int getRotateAngle() {
        return (rotate.getSensorCollection().getQuadraturePosition());
    }

    public void setRotatePower(double pwr) {
        rotate.set(pwr);
    }

    public void setSpinPower(double pwr) {
        rotate.set(pwr);
    }

    public void stop() {
        setRotatePower(0.0);
        setSpinPower(0.0);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(command.get());
    }
}
