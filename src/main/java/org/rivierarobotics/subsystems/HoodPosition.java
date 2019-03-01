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

public enum HoodPosition {
    STARTING_ANGLE(0),

    FRONT_ROCKET_LEVEL_ONE(1647),
    FRONT_ROCKET_LEVEL_TWO(4657),
    FRONT_CARGO_SHIP(-1904),
    FRONT_HUMAN_PLAYER_STATION(2117),
    FRONT_COLLECT(2296),

    BACK_ROCKET_LEVEL_ONE(3179),
    BACK_ROCKET_LEVEL_TWO(600),
    BACK_CARGO_SHIP(146),
    BACK_HUMAN_PLAYER_STATION(3181),
    BACK_COLLECT(2866);

    public final int ticks;

    HoodPosition(int ticks) {
        this.ticks = ticks;
    }
}
