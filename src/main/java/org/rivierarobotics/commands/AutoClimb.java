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
import org.rivierarobotics.subsystems.*;

import javax.inject.Inject;

public class AutoClimb extends CommandGroup {
    private final WinchController winchController;
    private boolean isFinished = false;

    @Inject
    public AutoClimb(PistonCommands piston, HoodCommands hood, ArmCommands arm, WinchCommands winch, @Provided WinchController winchController) {
        requires(winchController);
        this.winchController = winchController;
        addSequential(piston.extend(Piston.LOCK_CLIMB));
        addSequential(hood.setFrontPosition(HoodPosition.CLIMB));
        addSequential(new TimedCommand(0.1));
        addSequential(arm.setFrontPosition(ArmPosition.CLIMB_INITIAL));
        addSequential(piston.extend(Piston.HELPER_CLIMB));
        addSequential(new TimedCommand(0.1));
        addSequential(winch.set(WinchPosition.HELPER_RETRACT));
        addSequential(arm.setFrontPosition(ArmPosition.CLIMB_PUSH));
        addSequential(new TimedCommand(0.5));
        addSequential(piston.retract(Piston.HELPER_CLIMB));
        addSequential(winch.atPower(1.0));
        addSequential(new TimedCommand(2.0));
        addSequential(hood.setFrontPosition(HoodPosition.RESTING_ARM_ZERO));
        addSequential(arm.setFrontPosition(ArmPosition.ZERO_DEGREES));
    }

    @Override
    protected void end() {
        //TODO find another way to wait - this is very resource consuming
        long waitTime = System.currentTimeMillis() + 500;
        while(System.currentTimeMillis() <= waitTime) {
            System.out.println("waiting...");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {

            }
        }
        cancel();
        winchController.atPower(0.0);
    }

    @Override
    protected boolean isFinished() {
        return isFinished;
    }
}
