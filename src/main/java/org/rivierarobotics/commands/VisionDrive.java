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

import edu.wpi.first.wpilibj.command.Command;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.subsystems.DriveTrain;
import org.rivierarobotics.robot.VisionState;

@GenerateCreator
public class VisionDrive extends Command {
    private final DriveTrain dt;
    private VisionState jevois;
    private final int CAMERA_PIXEL_WIDTH = 100;
    private final int CENTERED_IMAGE_PIXEL = 40;
    private final double TARGET_LENGTH = 15;
    private final String TARGET_ID = "";

    public VisionDrive(@Provided DriveTrain dt, VisionState vision) {
        this.dt = dt;
        jevois = vision;
        requires(dt);
    }

    @Override
    protected void execute() {
        double percentScreen = getCenter()[0] / CAMERA_PIXEL_WIDTH;
        double power = .4;
        if (percentScreen < CENTERED_IMAGE_PIXEL) {
            dt.setPower(power, (percentScreen + 1) * power);
        } else {
            dt.setPower((percentScreen + 1) * power, power);
        }

    }

    @Override
    protected boolean isFinished() {
        if (getTargetLength(TARGET_ID) > TARGET_LENGTH) {
            return true;
        } else {
            return false;
        }
    }

    public double[] getCenter() {
        double[] centerCoords = new double[2];
        for (int i = 0; i < 2; i++) {
            centerCoords[i] = calculateDistance(getXCoord(i), getYCoord(i), getXCoord(i + 1), getYCoord(i + 1));
        }
        return centerCoords;
    }

    public double getTargetLength(String object) {
        while (!getObjectId().equals(object)) {
            continue;
        }
        double[] lengths = new double[4];
        for (int i = 0; i < 3; i++) {
            lengths[i] = calculateDistance(getXCoord(i), getYCoord(i), getXCoord(i + 1), getYCoord(i + 1));
        }
        lengths[3] = calculateDistance(getXCoord(3), getYCoord(3), getXCoord(0), getYCoord(0));
        double sideOne = average(lengths[0], lengths[2]);
        double sideTwo = average(lengths[1], lengths[3]);
        if (sideOne > sideTwo) {
            return sideOne;
        } else {
            return sideTwo;
        }
    }

    public double average(double x, double y) {
        return (x + y) / 2;
    }

    public double calculateDistance(int x1, int y1, int x2, int y2) {
        int xDistance = x2 - x1;
        int yDistance = y2 - y1;
        return Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
    }

    public String getObjectId() {
        return jevois.getCurrentMessage().getId();
    }

    public int getXCoord(int i) {
        return jevois.getCurrentMessage().getX(i);
    }

    public int getYCoord(int i) {
        return jevois.getCurrentMessage().getY(i);
    }

}
