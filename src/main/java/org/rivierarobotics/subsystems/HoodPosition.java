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
    //TODO convert to degrees or make system more intuitive to enter data
    //TODO make everything relative to resting position, accounting for hood slop
    STARTING_ANGLE(0, 0),
    RESTING_ARM_ZERO(1650, 1650),
    NINETY_ARM_ZERO(RESTING_ARM_ZERO.ticksFront + 200, RESTING_ARM_ZERO.ticksBack - 2850),

    ROCKET_LEVEL_ONE(RESTING_ARM_ZERO.ticksFront - 3, RESTING_ARM_ZERO.ticksBack + 291),
    ROCKET_LEVEL_TWO(RESTING_ARM_ZERO.ticksFront + 1786, RESTING_ARM_ZERO.ticksBack - 2047),
    CARGO_SHIP(RESTING_ARM_ZERO.ticksFront + 2895, RESTING_ARM_ZERO.ticksBack - 2530),
    HUMAN_PLAYER_STATION(RESTING_ARM_ZERO.ticksFront + 467, RESTING_ARM_ZERO.ticksBack + 1531),
    COLLECT(RESTING_ARM_ZERO.ticksFront - 50, RESTING_ARM_ZERO.ticksBack + 50);

    public final int ticksFront;
    public final int ticksBack;

    HoodPosition(int ticksFront, int ticksBack) {
        this.ticksFront = ticksFront;
        this.ticksBack = ticksBack;
    }
}
