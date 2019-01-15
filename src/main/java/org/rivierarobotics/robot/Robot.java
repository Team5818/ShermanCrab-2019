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

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

import org.rivierarobotics.commands.RequireAllSubsystems;
import org.rivierarobotics.inject.CommandComponent;
import org.rivierarobotics.inject.DaggerCommandComponent;

public class Robot extends TimedRobot {
	private CommandComponent commandComponent;
	private RequireAllSubsystems requireAllSubsystems = commandComponent.getRequireAllSubsystems();

	Command autonomousCommand;
	SendableChooser<Command> chooser;

	@Override
	public void robotInit() {
		commandComponent = DaggerCommandComponent.create();
		chooser = new SendableChooser<>();

		chooser.addOption("Do Nothing", new TimedCommand(15));
		chooser.addOption("Drive Forward", commandComponent.newDriveForward(0.6,15));

		SmartDashboard.putData("Auto Mode", chooser);
	}

	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		commandComponent.getPrintSmartDash();
	}

	@Override
	public void teleopInit() {
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}
	
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		commandComponent.getPrintSmartDash();
	}

	@Override
	public void disabledInit() {
		if (requireAllSubsystems.isRunning()) {
			requireAllSubsystems.cancel();
		}
	}
	
	@Override
	public void disabledPeriodic() {
		commandComponent.getPrintSmartDash();
		Scheduler.getInstance().run();
	}

	@Override
	public void testInit() {

	}

	@Override
	public void testPeriodic() {
		Scheduler.getInstance().run();
		commandComponent.getPrintSmartDash();
	}
}
