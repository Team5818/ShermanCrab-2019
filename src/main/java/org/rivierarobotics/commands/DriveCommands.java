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

package org.rivierarobotics.commands;

import javax.inject.Inject;

public class DriveCommands {
    private final DriveForwardCreator driveForwardCreator;
    private final DriveDistanceCreator driveDistanceCreator;
    private final RotateCreator rotateCreator;
    private final DriveAtPowerCreator driveAtPowerCreator;

    @Inject
    public DriveCommands(DriveForwardCreator driveForwardCreator,
                         DriveDistanceCreator driveDistanceCreator,
                         RotateCreator rotateCreator,
                         DriveAtPowerCreator driveAtPowerCreator) {
        this.driveForwardCreator = driveForwardCreator;
        this.driveDistanceCreator = driveDistanceCreator;
        this.rotateCreator = rotateCreator;
        this.driveAtPowerCreator = driveAtPowerCreator;
    }

    public DriveForward forward(double power, double distance) {
        return driveForwardCreator.create(power, distance);
    }

    public DriveDistance distance(double distance) {
        return driveDistanceCreator.create(distance);
    }

    public Rotate rotate(double degrees) {
        return rotateCreator.create(degrees);
    }

    public DriveAtPower atPower(double power, boolean stop) {
        return driveAtPowerCreator.create(power, stop);
    }
}
