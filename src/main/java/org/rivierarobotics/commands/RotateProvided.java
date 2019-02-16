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

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.subsystems.DriveTrain;
import org.rivierarobotics.subsystems.PigeonGyro;

@GenerateCreator
public class RotateProvided extends Command {
    private DriveTrain dt;
    private PigeonGyro gyro;
    private double degreesToRotate;
    private double currentDegrees;
    private double startDegrees;
    private double degreeBuffer;
    
    public RotateProvided(@Provided DriveTrain dt, @Provided PigeonGyro gyro, double degrees) {
        degreesToRotate = degrees;
        this.gyro = gyro;
        this.dt = dt;
        requires(dt);
        requires(gyro);
        degreeBuffer = 7;
    }

    private void rotateDirection(double degrees) {
        if (degrees > 0) {
            dt.setPower(-.4, .4);
        } else {
            dt.setPower(.4, -.4);
        }
    }

    @Override
    protected void initialize() {
        startDegrees = currentDegrees = gyro.getYaw();
    }

    @Override
    protected void execute() {
        // changeDegrees = gyro.getYaw() - startDegrees;
        currentDegrees = gyro.getYaw();
        SmartDashboard.putNumber("deg", currentDegrees - startDegrees);
        rotateDirection(degreesToRotate);
        //dt.setPower(-0.5, 0.5);
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(currentDegrees - startDegrees) >= degreesToRotate - (degreeBuffer * (degreesToRotate / 90));
    }
}
