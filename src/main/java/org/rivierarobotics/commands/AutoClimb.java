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
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.subsystems.ArmPosition;
import org.rivierarobotics.subsystems.HoodPosition;
import org.rivierarobotics.subsystems.Piston;
import org.rivierarobotics.subsystems.WinchController;

import javax.inject.Inject;

public class AutoClimb extends CommandGroup {
    private final WinchController winchController;

    @Inject
    public AutoClimb(PistonCommands piston, HoodCommands hood, ArmCommands arm, WinchCommands winch,
                     @Provided WinchController winchController) {
        this.winchController = winchController;
        requires(winchController);

        addSequential(piston.extend(Piston.LOCK_CLIMB));
        addSequential(hood.setFrontPosition(HoodPosition.CLIMB));
        addSequential(arm.setFrontPosition(ArmPosition.CLIMB_INITIAL));
        addSequential(new TimedCommand(0.2));
        addSequential(arm.setFrontPosition(ArmPosition.CLIMB_PUSH));
        addSequential(piston.extend(Piston.HELPER_CLIMB));
        addSequential(new TimedCommand(0.4));
        addSequential(winch.atPower(1.0));
        addSequential(new TimedCommand(1.5));
        addSequential(piston.retract(Piston.HELPER_CLIMB));
        addSequential(new TimedCommand(2.0));
        addSequential(hood.setFrontPosition(HoodPosition.RESTING_ARM_ZERO));
        addSequential(arm.setFrontPosition(ArmPosition.ZERO_DEGREES));
    }

    @Override
    protected void end() {
        new AutoClimbEnd(winchController).start();
    }

    @Override
    protected boolean isFinished() {
        return winchController.getClimbLimitSwitch();
    }
}
