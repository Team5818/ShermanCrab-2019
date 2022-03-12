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
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.rivierarobotics.inject.Input;
import org.rivierarobotics.subsystems.DriveTrain;
import org.rivierarobotics.util.MathUtil;

import javax.inject.Inject;

public class DriveControl extends CommandBase {
    private final DriveTrain driveTrain;
    private final Joystick throttle;
    private final Joystick turning;

    @Inject
    public DriveControl(DriveTrain dt, @Input(Input.Position.DRIVER_LEFT) Joystick left,
                        @Input(Input.Position.DRIVER_RIGHT) Joystick right) {
        this.driveTrain = dt;
        this.throttle = left;
        this.turning = right;
        addRequirements(driveTrain);
    }

    @Override
    public void execute() {
        setArcade(MathUtil.fitDeadband(turning.getX()), MathUtil.fitDeadband(-throttle.getY()));
    }

    public void setArcade(double rotate, double power) {
        double max = Math.max(Math.abs(rotate), Math.abs(power));
        double diff = power - rotate;
        double sum = power + rotate;

        double left;
        double right;

        if (power > 0) {
            if (rotate > 0) {
                left = max;
                right = diff;
            } else {
                left = sum;
                right = max;
            }
        } else {
            if (rotate > 0) {
                left = sum;
                right = -max;
            } else {
                right = diff;
                left = -max;
            }
        }
        driveTrain.setPower(left, right);
    }
}