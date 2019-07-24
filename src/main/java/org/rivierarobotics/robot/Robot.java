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

package org.rivierarobotics.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import org.rivierarobotics.inject.DaggerGlobalComponent;
import org.rivierarobotics.inject.GlobalComponent;
import org.rivierarobotics.subsystems.Piston;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Robot extends TimedRobot {
    static {
        try {
            new CompressOldFiles().run();
        } catch (IOException e) {
            // Ignore here, we would rather run than crash!
            e.printStackTrace();
        }
    }

    private GlobalComponent globalComponent;
    private final NetworkTableEntry driveEncoderLeft = Shuffleboard.getTab("Drive Train")
            .add("Distance Left", 0).getEntry();
    private final NetworkTableEntry driveEncoderRight = Shuffleboard.getTab("Drive Train")
            .add("Distance Right", 0).getEntry();
    private final NetworkTableEntry armEncoder = Shuffleboard.getTab("Arm Controller")
            .add("Angle", 0).getEntry();
    private final NetworkTableEntry hoodEncoder = Shuffleboard.getTab("Hood Controller")
            .add("Angle", 0).getEntry();
    private final NetworkTableEntry armOut = Shuffleboard.getTab("Arm Controller")
            .add("Degrees", 0).getEntry();
    private final NetworkTableEntry hoodOut = Shuffleboard.getTab("Hood Controller")
            .add("Degrees", 0).getEntry();
    private final NetworkTableEntry hoodPID = Shuffleboard.getTab("Dev")
            .add("Hood PID Enabled", 0).getEntry();
    private final NetworkTableEntry driveTrainOutput = Shuffleboard.getTab("Dev")
            .add("Drive Train Output", 0).getEntry();

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private boolean loggedMatchNumber = false;


    @Override
    public void robotInit() {
        globalComponent = DaggerGlobalComponent.create();
        globalComponent.robotInit();
        UsbCamera jevois = CameraServer.getInstance().startAutomaticCapture();
        jevois.setVideoMode(VideoMode.PixelFormat.kMJPEG, 144, 108, 60);
    }

    @Override
    public void teleopInit() {
        globalComponent.getButtonConfiguration().initTeleop();
        globalComponent.getPistonController().retractPiston(Piston.CLAMP);
    }

    @Override
    public void autonomousInit() {
        globalComponent.getButtonConfiguration().initTeleop();
        globalComponent.getPistonController().retractPiston(Piston.CLAMP);
    }

    @Override
    public void autonomousPeriodic() {
        logMatchIfNeeded();
        displayShuffleboard();
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopPeriodic() {
        logMatchIfNeeded();
        displayShuffleboard();
        Scheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        globalComponent.getArmController().getPIDLoop().disable();
        globalComponent.getArmController().setBrake();
        globalComponent.getDriveTrain().setBrake();
    }

    @Override
    public void disabledPeriodic() {
        logMatchIfNeeded();
    }

    @Override
    public void testInit() {
        globalComponent.getButtonConfiguration().initTest();
    }

    @Override
    public void testPeriodic() {
        Scheduler.getInstance().run();
    }

    private void logMatchIfNeeded() {
        if (loggedMatchNumber) {
            return;
        }
        int num = DriverStation.getInstance().getMatchNumber();
        if (num == 0) {
            return;
        }

        logger.info("Starting robot, match_number=" + num);
        loggedMatchNumber = true;
    }

    private void displayShuffleboard() {
        driveTrainOutput.setDouble(globalComponent.getDriveTrain().getLeft().getTalon().getMotorOutputPercent());
        driveEncoderLeft.setDouble(globalComponent.getDriveTrain().getLeft().getDistance());
        driveEncoderRight.setDouble(globalComponent.getDriveTrain().getRight().getDistance());
        hoodEncoder.setDouble(globalComponent.getHoodController().getAngle());
        hoodOut.setDouble(globalComponent.getHoodController().getDegrees());
        armEncoder.setDouble(globalComponent.getArmController().getAngle());
        armOut.setDouble(globalComponent.getArmController().getDegrees());
    }
}
