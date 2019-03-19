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

package org.rivierarobotics.util;

public class MathUtil {
    private static final double DEADBAND = 0.08;

    public static double fitDeadband(double val) {
        double abs = Math.abs(val);
        if (abs < DEADBAND) {
            return 0;
        }
        if (abs > 1) {
            return 1;
        }
        return (val - DEADBAND) / (1 - DEADBAND);
    }

    public static double limit(double value, double max) {
        if (value > max) {
            return max;
        } else if (value < -max) {
            return -max;
        } else {
            return value;
        }
    }

    public static double fitHoodRotation(double angle, double min, double max) {
        if (angle > max) {
            return max;
        } else if (angle < min) {
            return min;
        } else {
            return angle;
        }
    }
}
