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
    RESTING_ARM_ZERO(0, 0),

    ROCKET_LEVEL_ONE(-3, 100),
    ROCKET_LEVEL_TWO(1786, -2047),
    CARGO_SHIP(2895, -2727),
    HUMAN_PLAYER_STATION(467, -520),
    COLLECT(330, -330);

    public final int ticksFront;
    public final int ticksBack;

    HoodPosition(int ticksFront, int ticksBack) {
        this.ticksFront = HoodController.RESTING_ZERO + ticksFront;
        this.ticksBack = HoodController.RESTING_ZERO + ticksBack;
    }
}
