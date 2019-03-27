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

package org.rivierarobotics.robot;

import com.flowpowered.math.vector.Vector2i;

import java.util.ArrayList;
import java.util.List;

public class JevoisMessage {
    private final String id;
    private final List<Vector2i> points;
    private final String extra;

    public JevoisMessage(String id, List<Vector2i> points, String extra) {
        this.id = id;
        this.points = new ArrayList<>(points);
        this.extra = extra;
    }

    public String getId() {
        return id;
    }

    public List<Vector2i> getPoints() {
        return points;
    }

    public String getExtra() {
        return extra;
    }

    @Override
    public String toString() {
        return "JevoisMessage{" +
                "id='" + id + '\'' +
                ", points=" + points +
                ", extra='" + extra + '\'' +
                '}';
    }
}
