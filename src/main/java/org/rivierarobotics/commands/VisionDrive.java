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

import com.flowpowered.math.vector.Vector2i;
import edu.wpi.first.wpilibj.command.Command;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.robot.JevoisMessage;
import org.rivierarobotics.subsystems.DriveTrain;
import org.rivierarobotics.robot.VisionState;

import java.util.List;

import static java.util.stream.Collectors.toList;

@GenerateCreator
public class VisionDrive extends Command {
    private final DriveTrain dt;
    private VisionState jevois;
    private final int CAMERA_PIXEL_WIDTH = 320;
    private final int CENTERED_IMAGE_PIXEL = 160;
    private final int IMAGE_CENTER_BUFFER = 60;
    private double basePower;
    private double turnPower;

    public VisionDrive(@Provided DriveTrain dt, VisionState vision, double basePower, double turnPower) {
        this.dt = dt;
        jevois = vision;
        this.basePower = basePower;
        this.turnPower = turnPower;
        requires(dt);
    }

    @Override
    protected void execute() {
        var blobs = jevois.getCurrentMessage();
        var filterBlobs = blobs.stream()
                .filter(this::isGoodBlob)
                .collect(toList());
        var bestBlob = findBestBlob(filterBlobs);

        int centerX = getCenterX(bestBlob);
        int imageOffset = centerX - CENTERED_IMAGE_PIXEL;
        double turnEffort = Math.signum(imageOffset) * turnPower * Math.abs(imageOffset / IMAGE_CENTER_BUFFER);

        dt.setPower(basePower - turnEffort, turnEffort + basePower);
    }

    private int getCenterX(JevoisMessage x) {
        Vector2i corner1 = x.getPoints().get(0);
        Vector2i corner2 = x.getPoints().get(2);
        return (corner1.getX() + corner2.getX()) / 2;
    }

    private JevoisMessage findBestBlob(List<JevoisMessage> blobs) {
        JevoisMessage best = null;
        int distToCenter = Integer.MAX_VALUE;
        for (JevoisMessage blob : blobs) {
            int center = getCenterX(blob);

            if (Math.abs(center - CENTERED_IMAGE_PIXEL) < distToCenter) {
                best = blob;
                distToCenter = center;
            }
        }
        return best;
    }


    private boolean isGoodBlob(JevoisMessage x) {
        int dist = Integer.MAX_VALUE;
        Vector2i a = null;
        Vector2i b = null;
        var points = x.getPoints();
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                Vector2i tempA = points.get(i);
                Vector2i tempB = points.get(j);
                int tempDist = tempA.distanceSquared(tempB);
                if (tempDist < dist) {
                    a = tempA;
                    b = tempB;
                    dist = tempDist;
                }
            }
        }
        assert a != null;
        if (a.getX() > b.getX()) {
            var temp = b;
            b = a;
            a = temp;
        }
        return (b.getY() - a.getY()) > 0;
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
