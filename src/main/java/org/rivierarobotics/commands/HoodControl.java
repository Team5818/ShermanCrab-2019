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

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.rivierarobotics.inject.Input;
import org.rivierarobotics.subsystems.HoodController;

import javax.inject.Inject;

public class HoodControl extends Command {
    // TODO change values for safety hood deadband, manually test
    private double HOOD_DEADBAND = 0.1;
    private double HOOD_MAX_EXT = 90;
    private double HOOD_MIN_EXT = -90;
    private HoodController hood;
    private Joystick hoodRotateJoy;

    @Inject
    public HoodControl(HoodController hood, @Input(Input.Position.CODRIVER_LEFT) Joystick hoodRotateJoy) {
        this.hood = hood;
        this.hoodRotateJoy = hoodRotateJoy;
        requires(hood);
    }

    @Override
    protected void execute() {
        double armJoyY = hoodRotateJoy.getY();
        if(armJoyY < -HOOD_DEADBAND && hood.getAngle() > HOOD_MIN_EXT) {
            hood.setRotatePower(armJoyY);
        } else if(armJoyY > HOOD_DEADBAND && hood.getAngle() < HOOD_MAX_EXT){
            hood.setRotatePower(armJoyY);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
