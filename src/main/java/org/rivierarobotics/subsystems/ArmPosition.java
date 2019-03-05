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

package org.rivierarobotics.subsystems;

public enum ArmPosition {
    ZERO_DEGREES(0),
    NINETY_DEGREES(90),
    NEGATIVE_NINETY_DEGREES(-90),
    FORTY_FIVE_DEGREES(45),
    NEGATIVE_FORTY_FIVE_DEGREES(-45),

    FRONT_ROCKET_LEVEL_ONE(60.49),
    FRONT_ROCKET_LEVEL_TWO(5.2),
    FRONT_CARGO_SHIP(27.87),
    FRONT_HUMAN_PLAYER_STATION(22.66),
    FRONT_COLLECT(88.53),
    FRONT_HATCH_LIMIT(0),

    BACK_ROCKET_LEVEL_ONE(-58.02),
    BACK_ROCKET_LEVEL_TWO(-13.58),
    BACK_CARGO_SHIP(-49.47),
    BACK_HUMAN_PLAYER_STATION(-26.37),
    BACK_COLLECT(-83.86),
    BACK_HATCH_LIMIT(0);

    public final double degrees;
    public final double ticks;
    public final static double TICKS_AT_ZERO = 1079;
    private final static double TICKS_AT_NINETY = 2100;
    public static final double TICKS_TO_DEGREES = (TICKS_AT_NINETY - TICKS_AT_ZERO) / 90;

    ArmPosition(double degrees) {
        this.degrees = degrees;
        this.ticks = (degrees * TICKS_TO_DEGREES) + TICKS_AT_ZERO;
    }
}
