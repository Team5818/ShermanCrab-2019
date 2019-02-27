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

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;
import org.rivierarobotics.subsystems.Piston;

import javax.inject.Inject;

public class HatchPush extends CommandGroup {
    private final PistonCommands pistonCmds;

    @Inject
    public HatchPush(PistonCommands piston) {
        pistonCmds = piston;
    }

    @Override
    protected void execute() {
        addSequential(pistonCmds.extend(Piston.PUSH));
        addSequential(new TimedCommand(0.05));
        addSequential(pistonCmds.retract(Piston.CLAMP));
    }

    @Override
    protected void end() {
        addSequential(pistonCmds.retract(Piston.PUSH));
        addSequential(new TimedCommand(0.05));
    }
}
