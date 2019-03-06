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
    ZERO_DEGREES(1079),
    NINETY_DEGREES(2100),
    NEGATIVE_NINETY_DEGREES(80),

    FRONT_ROCKET_LEVEL_ONE(1765),
    FRONT_ROCKET_LEVEL_TWO(1138),
    FRONT_CARGO_SHIP(1395),
    FRONT_HUMAN_PLAYER_STATION(1336),
    FRONT_COLLECT(2083),
    FRONT_HATCH_LIMIT(0),

    BACK_ROCKET_LEVEL_ONE(-58.02),
    BACK_ROCKET_LEVEL_TWO(-13.58),
    BACK_CARGO_SHIP(-49.47),
    BACK_HUMAN_PLAYER_STATION(-26.37),
    BACK_COLLECT(-83.86),
    BACK_HATCH_LIMIT(-95.15);

    public final double ticks;

    ArmPosition(double ticks) {
        this.ticks = ticks;
    }
}
