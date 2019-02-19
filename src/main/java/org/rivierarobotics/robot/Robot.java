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

package org.rivierarobotics.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import org.rivierarobotics.inject.DaggerGlobalComponent;
import org.rivierarobotics.inject.GlobalComponent;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends TimedRobot {
	private GlobalComponent globalComponent;
	private final NetworkTableEntry driveEncoderLeft = Shuffleboard.getTab( "Drive Train")
	            .add("Distance Left", 0).getEntry();
	private final NetworkTableEntry driveEncoderRight = Shuffleboard.getTab( "Drive Train")
	            .add("Distance Right", 0).getEntry();
	private final NetworkTableEntry armEncoder = Shuffleboard.getTab( "Arm Controller")
			.add("Angle", 0).getEntry();
	private final NetworkTableEntry hoodEncoder = Shuffleboard.getTab( "Hood Controller")
			.add("Angle", 0).getEntry();
	
	@Override
	public void robotInit() {
		globalComponent = DaggerGlobalComponent.create();
		globalComponent.getDriveTrain();
		globalComponent.getArmController();
		globalComponent.getHoodController();
		globalComponent.getPigeonGyro();
		globalComponent.getTentacleController();
		globalComponent.getButtonConfiguration().initialize();
	}
	
	@Override
	public void teleopPeriodic() {
		double distance = globalComponent.getDriveTrain().getLeft().getDistance();
		driveEncoderLeft.setDouble(distance);
		hoodEncoder.setDouble(globalComponent.getHoodController().getAngle());
		armEncoder.setDouble(globalComponent.getArmController().getAngle());
	    distance = globalComponent.getDriveTrain().getRight().getDistance();
	    driveEncoderRight.setDouble(distance);
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {

	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}
	
	@Override
	public void disabledPeriodic() {
		
	}
}
