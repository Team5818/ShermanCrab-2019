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

import org.rivierarobotics.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;

@GenerateCreator
public class DriveDistance extends Command {

    private DriveTrain dt;
    private final double distance;
    private double startDistance;
    private double currentDistance;
    private double calcDistance;
    private double calcCurrentDistance;

    public DriveDistance(@Provided DriveTrain dt, double distance) {
        this.distance = distance;
        this.dt = dt;
        requires(dt);
    }

    @Override
    protected void initialize() {
        startDistance = currentDistance = dt.getDistance();
        calcDistance = startDistance + distance;
        dt.addDistance(distance, distance);
    }

    @Override
    protected void execute() {
        currentDistance = dt.getDistance();
    }


    @Override
    protected boolean isFinished() {
        return currentDistance >= calcDistance;
    }
}
