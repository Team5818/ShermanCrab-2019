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

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.rivierarobotics.inject.Input;
import org.rivierarobotics.subsystems.HoodController;
import org.rivierarobotics.util.MathUtil;

import javax.inject.Inject;

public class HoodControl extends Command {
    private final HoodController hoodController;
    private final Joystick hoodJoystick;

    @Inject
    public HoodControl(HoodController hoodController, @Input(Input.Position.CODRIVER_RIGHT) Joystick hoodJoystick) {
        this.hoodController = hoodController;
        this.hoodJoystick = hoodJoystick;
        requires(hoodController);
    }

    @Override
    protected void execute() {
        hoodController.setPower(MathUtil.fitDeadband(hoodJoystick.getY()));
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
