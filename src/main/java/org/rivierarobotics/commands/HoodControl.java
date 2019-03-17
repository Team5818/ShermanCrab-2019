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

    private HoodController hood;
    private Joystick hoodRotateJoy;

    @Inject
    public HoodControl(HoodController hood, @Input(Input.Position.CODRIVER_RIGHT) Joystick hoodRotateJoy) {
        this.hood = hood;
        this.hoodRotateJoy = hoodRotateJoy;
        requires(hood);
    }

    @Override
    protected void execute() {
        double armJoyY = hoodRotateJoy.getY();
        hood.setPower(MathUtil.fitDeadband(armJoyY));
    }

    @Override
    protected void end() {
        //TODO [Regional] [Software] test that this solves hood moving randomly problem
        hood.setPower(0.0);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
