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

import javax.inject.Inject;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.rivierarobotics.inject.Input;
import org.rivierarobotics.subsystems.DriveTrain;
import org.rivierarobotics.util.MathUtil;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class DriveControl extends Command {
    private DriveTrain driveTrain;
    private Joystick throttle;
    private Joystick turning;

    @Inject
    public DriveControl(DriveTrain dt, @Input(Input.Position.DRIVER_LEFT) Joystick left,
            @Input(Input.Position.DRIVER_RIGHT) Joystick right) {
        this.driveTrain = dt;
        this.throttle = left;
        this.turning = right;
        requires(driveTrain);
    }

    @Override
    protected void execute() {
        double y = -throttle.getY();
        double x = turning.getX();
        setArcade(MathUtil.fitDeadband(x), MathUtil.fitDeadband(y));
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
        if(Math.abs(left) <= 0.05) { left = 0; }
        if(Math.abs(right) <= 0.05) { right = 0; }
        SmartDashboard.putNumber("left", left);
        SmartDashboard.putNumber("right", right);
        driveTrain.setPower(left, right);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}