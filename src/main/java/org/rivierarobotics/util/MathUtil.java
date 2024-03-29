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
    private static final double DEADBAND = 0.1;

    public static double fitDeadband(double val) {
        if (!(Math.abs(val) < DEADBAND)) {
            if (val > 0) {
                if (val >= 1) {
                    return 1;
                } else {
                    return val - DEADBAND;
                }
            } else if (val < 0) {
                if (val <= -1) {
                    return -1;
                } else {
                    return val + DEADBAND;
                }
            }
        }
        return 0;
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

    public static int moduloPositive(int value, int modulo) {
        return (value % modulo) + (value < 0 ? modulo : 0);
    }
}