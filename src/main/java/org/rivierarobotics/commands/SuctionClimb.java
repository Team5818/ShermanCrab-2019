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
import org.rivierarobotics.subsystems.SuctionMotorPosition;

import javax.inject.Inject;

public class SuctionClimb extends CommandGroup {

    @Inject
    public SuctionClimb(SuctionCommands suction, PistonCommands piston, ArmCommands arm) {
        addSequential(suction.set(SuctionMotorPosition.CLIMB_INIT));
        long start = System.currentTimeMillis();
        while (start <= System.currentTimeMillis() + 5000) {
            addSequential(piston.extend(Piston.CLIMB));
            addSequential(new TimedCommand(0.5));
            addSequential(piston.retract(Piston.CLIMB));
            addSequential(new TimedCommand(0.5));
        }
        addSequential(suction.set(SuctionMotorPosition.CLIMB_HOLD));
        addSequential(arm.setBackPosition(ArmPosition.SUCTION_CLIMB));
        while (!isFinished()) {
            addSequential(piston.extend(Piston.CLIMB));
            addSequential(new TimedCommand(0.5));
            addSequential(piston.retract(Piston.CLIMB));
            addSequential(new TimedCommand(0.5));
        }
    }

    @Override
    protected boolean isFinished() {
        return ClimbCommands.SUCTION_DONE;
    }
}
