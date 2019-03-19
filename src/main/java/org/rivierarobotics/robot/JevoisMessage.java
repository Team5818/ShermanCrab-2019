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

import java.util.Arrays;

public class JevoisMessage {
    private final String id;
    private final int[] x;
    private final int[] y;
    private final String extra;

    public JevoisMessage(String id, int[] x, int[] y, String extra) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.extra = extra;
    }

    public String getId() {
        return id;
    }

    public int getX(int i) {
        return x[i];
    }

    public int getY(int i) {
        return y[i];
    }

    public String getExtra() {
        return extra;
    }

    @Override
    public String toString() {
        return "JevoisMessage{" +
                "id='" + id + '\'' +
                ", x=" + Arrays.toString(x) +
                ", y=" + Arrays.toString(y) +
                ", extra='" + extra + '\'' +
                '}';
    }
}
