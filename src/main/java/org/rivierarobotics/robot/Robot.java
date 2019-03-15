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

import edu.wpi.cscore.VideoMode;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import org.rivierarobotics.inject.DaggerGlobalComponent;
import org.rivierarobotics.inject.GlobalComponent;
import org.rivierarobotics.subsystems.*;

public class Robot extends TimedRobot {
    private GlobalComponent globalComponent;
    private final NetworkTableEntry driveEncoderLeft = Shuffleboard.getTab("Drive Train")
            .add("Distance Left", 0).getEntry();
    private final NetworkTableEntry driveEncoderRight = Shuffleboard.getTab("Drive Train")
            .add("Distance Right", 0).getEntry();
    private final NetworkTableEntry armEncoder = Shuffleboard.getTab("Arm Controller")
            .add("Angle", 0).getEntry();
    private final NetworkTableEntry hoodEncoder = Shuffleboard.getTab("Hood Controller")
            .add("Angle", 0).getEntry();
    private final NetworkTableEntry hoodOut = Shuffleboard.getTab("Hood Controller")
            .add("Degrees", 0).getEntry();
    private final NetworkTableEntry armOut = Shuffleboard.getTab("Arm Controller")
            .add("Degrees", 0).getEntry();
    private final NetworkTableEntry winchEncoder = Shuffleboard.getTab("Winch Controller")
            .add("Distance", 0).getEntry();
    private final NetworkTableEntry suctionEncoder = Shuffleboard.getTab("Suction Controller")
            .add("Angle", 0).getEntry();

    @Override
    public void robotInit() {
        globalComponent = DaggerGlobalComponent.create();
        globalComponent.robotInit();
        CameraServer.getInstance().startAutomaticCapture().setVideoMode(VideoMode.PixelFormat.kMJPEG, 160, 120, 60);
    }

    @Override
    public void teleopInit() {
        globalComponent.getButtonConfiguration().initTeleop();
    }

    @Override
    public void teleopPeriodic() {
        displayShuffleboard();
        currentLimit();
        armSafety();
        hatchLED();
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        globalComponent.getButtonConfiguration().initTeleop();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void testInit() {
        globalComponent.getButtonConfiguration().initTest();
    }

    @Override
    public void testPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void disabledPeriodic() {
        globalComponent.getArmController().getPIDLoop().disable();
        globalComponent.getHoodController().getPIDLoop().disable();
        globalComponent.getArmController().setBrake();
        globalComponent.getDriveTrain().setBrake();
    }

    private void armSafety() {
        if (globalComponent.getArmController().getDegrees() > ArmPosition.ZERO_DEGREES.degreesFront &&
                globalComponent.getPistonController().getPistonState(Piston.DEPLOY_LEFT) &&
                globalComponent.getPistonController().getPistonState(Piston.DEPLOY_RIGHT)) {
            if (ArmController.PWR_MANUAL > 0) {
                globalComponent.getArmController().stop();
            } else {
                if (ArmController.PWR_MANUAL < 0) {
                    globalComponent.getArmController().setCoast();
                }
                ArmController.SAFE = true;
            }
        } else {
            ArmController.SAFE = true;
        }
        ArmController.DEPLOY_PISTONS_OUT = globalComponent.getPistonController().getPistonState(Piston.DEPLOY_LEFT) ||
                globalComponent.getPistonController().getPistonState(Piston.DEPLOY_RIGHT);
    }

    private void hatchLED() {
        globalComponent.getPistonController().setTriangleLED(globalComponent.getPistonController().getTriangleEngaged());
    }

    private void displayShuffleboard() {
        driveEncoderLeft.setDouble(globalComponent.getDriveTrain().getLeft().getDistance());
        driveEncoderRight.setDouble(globalComponent.getDriveTrain().getRight().getDistance());
        hoodEncoder.setDouble(globalComponent.getHoodController().getAngle());
        armEncoder.setDouble(globalComponent.getArmController().getAngle());
        hoodOut.setDouble(globalComponent.getHoodController().getDegrees());
        winchEncoder.setDouble(globalComponent.getArmController().getDegrees());
    }

    private void currentLimit() {
        if(globalComponent.getShifter().getGearState() == Gear.HIGH.state) {
            globalComponent.getDriveTrain().getLeft().setMaxCurrent(50);
            globalComponent.getDriveTrain().getRight().setMaxCurrent(50);
        } else {
            globalComponent.getDriveTrain().getLeft().setMaxCurrent(100);
            globalComponent.getDriveTrain().getRight().setMaxCurrent(100);
        }
    }
}
