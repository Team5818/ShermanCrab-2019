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
import org.rivierarobotics.subsystems.ArmController;

import javax.inject.Inject;

public class ArmControl extends Command {
    /* NEED TO CHANGE VALUES LATER FOR SAFETY DEADBAND, MANUALLY TEST*/
    private double ARM_DEADBAND = 0.1;
    private double ARM_MAX_EXT = 90;
    private double ARM_MIN_EXT = -90;
    private ArmController arm;
    private Joystick armJoy;

    @Inject
    public ArmControl(ArmController arm, @Input(Input.Position.CODRIVER_LEFT) Joystick armJoy) {
        this.arm = arm;
        this.armJoy = armJoy;
        requires(arm);
    }

    @Override
    protected void execute() {
        double armJoyY = armJoy.getY();
        if(armJoyY < -ARM_DEADBAND && arm.getDistance() > ARM_MIN_EXT) {
            arm.setPower(armJoyY);
        } else if(armJoyY > ARM_DEADBAND && arm.getDistance() < ARM_MAX_EXT){
            arm.setPower(armJoyY);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
