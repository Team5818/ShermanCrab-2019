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
import edu.wpi.first.wpilibj.command.TimedCommand;
import org.rivierarobotics.subsystems.ArmPosition;
import org.rivierarobotics.subsystems.Piston;
import org.rivierarobotics.subsystems.WinchPosition;

import javax.inject.Inject;

public class ScissorClimb extends CommandGroup {

    @Inject
    public ScissorClimb(PistonCommands piston, ArmCommands arm, WinchCommands winch, DriveCommands drive) {
        addSequential(piston.retract(Piston.LOCK_CLIMB));
        addSequential(new TimedCommand(0.5));
        addSequential(piston.extend(Piston.HELPER_CLIMB));
        addSequential(arm.setFrontPosition(ArmPosition.FORTY_FIVE_DEGREES));
        addSequential(winch.set(WinchPosition.IN));
        addSequential(new TimedCommand(1.0));
        addSequential(arm.setFrontPosition(ArmPosition.SCISSOR_CLIMB));
        addSequential(new TimedCommand(1.0));
        addSequential(piston.retract(Piston.HELPER_CLIMB));
        addSequential(new TimedCommand(1.0));
        addSequential(drive.atPower(0.25, false));
    }
}
