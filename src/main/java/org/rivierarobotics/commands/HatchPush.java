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

import static org.rivierarobotics.commands.CommandGroups.inOrder;

public class HatchPush extends CommandGroup {

    @Inject
    public HatchPush(PistonCommands piston) {
        addSequential(inOrder(piston.extend(Piston.DEPLOY_RIGHT),
                piston.extend(Piston.DEPLOY_LEFT)));
        addSequential(piston.extend(Piston.PUSH));
        addSequential(new TimedCommand(0.05));
        addSequential(piston.extend(Piston.CLAMP));
        addSequential(new TimedCommand(0.15));
        addSequential(inOrder(piston.retract(Piston.DEPLOY_RIGHT),
                piston.retract(Piston.DEPLOY_LEFT)));
        addSequential(piston.retract(Piston.PUSH));
        addSequential(new TimedCommand(0.05));
        addSequential(piston.retract(Piston.CLAMP));

    }
}
