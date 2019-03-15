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
    //TODO [IMPORTANT] [PracticeBot] [Software] work on rotation-based positions. deal with overflow from multiple cycles.
    RESTING_ARM_ZERO(0, 0),
    NINETY_ARM_ZERO(200, -2850),

    ROCKET_LEVEL_ONE(-3, 291),
    ROCKET_LEVEL_TWO(1786, -2047),
    CARGO_SHIP(2895, -2530),
    HUMAN_PLAYER_STATION(467, -520),
    COLLECT(-50, 50);

    public final int ticksFront;
    public final int ticksBack;
    private final int restingArmZero = 3730;

    HoodPosition(int ticksFront, int ticksBack) {
        this.ticksFront = restingArmZero + ticksFront;
        this.ticksBack = restingArmZero + ticksBack;

    }
}
