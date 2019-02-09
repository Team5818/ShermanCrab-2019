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

package org.rivierarobotics.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.subsystems.DriveTrain;

@GenerateCreator
public class DriveVelocity extends Command {
	private final NetworkTableEntry velEntry = Shuffleboard.getTab("Drive Train")
			.addPersistent("Vel", 0).getEntry();
	private final NetworkTableEntry distEntry = Shuffleboard.getTab("Drive Train")
			.addPersistent("Dist", 0).getEntry();
	private DriveTrain dt;
	private double velocity;
	private double startDistance;
	private double currentDistance;

	public DriveVelocity(@Provided DriveTrain dt) {
		this.dt = dt;
		requires(dt);
	}

	@Override
	protected void initialize() {
		velocity = velEntry.getDouble(0.0);
		startDistance = currentDistance = dt.getDistance();
	}

	@Override
	protected void execute() {
		dt.setVelocity(velocity, velocity);
		currentDistance = dt.getDistance();
	}

	@Override
	protected boolean isFinished() {
		double distance = distEntry.getDouble(0.0);
		return Math.abs(currentDistance - startDistance) >= distance;
	}
}
