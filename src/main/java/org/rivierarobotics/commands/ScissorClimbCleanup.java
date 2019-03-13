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

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.rivierarobotics.subsystems.ArmPosition;
import org.rivierarobotics.subsystems.Piston;
import org.rivierarobotics.subsystems.WinchPosition;

import javax.inject.Inject;

public class ScissorClimbCleanup extends CommandGroup {

    @Inject
    public ScissorClimbCleanup(PistonCommands piston, ArmCommands arm, WinchCommands winch, DriveCommands drive) {
        addSequential(winch.set(WinchPosition.IN));
        addSequential(drive.atPower(0.0, true));
        addSequential(arm.setFrontPosition(ArmPosition.ZERO_DEGREES));
    }
}
