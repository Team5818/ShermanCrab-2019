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
    STARTING_ANGLE(0, 0),
    RESTING_ARM_ZERO(1650, 1650),
    NINETY_ARM_ZERO(1850, -1200),

    ROCKET_LEVEL_ONE(1647, 1941),
    ROCKET_LEVEL_TWO(3436, -397),
    CARGO_SHIP(4545, -880),
    HUMAN_PLAYER_STATION(2117, 3181),
    COLLECT(1600, 1700);

    public final int ticksFront;
    public final int ticksBack;

    HoodPosition(int ticksFront, int ticksBack) {
        this.ticksFront = ticksFront;
        this.ticksBack = ticksBack;
    }
    /*
    //degrees based hood preset system, still WIP
    RESTING_ARM_ZERO(1650, 1650),
    NINETY_ARM_ZERO(1850, -1200),

    ROCKET_LEVEL_ONE(1647, 1941),
    ROCKET_LEVEL_TWO(3970, -397),
    CARGO_SHIP(4545, -880),
    HUMAN_PLAYER_STATION(2117, 3181),
    COLLECT(1600, 1700);

    public final double degreesFront;
    public final double degreesBack;
    public final double ticksFront;
    public final double ticksBack;
    public final static double TICKS_AT_RESTING_ZERO = 1650;
    private final static double TICKS_AT_NINETY = 1975;
    public static final double TICKS_TO_DEGREES = (TICKS_AT_NINETY - TICKS_AT_RESTING_ZERO) / 90;

    HoodPosition(double degrees) {
        this.degreesFront = degrees;
        this.degreesBack = -degrees;
        this.ticksFront = (degreesFront * TICKS_TO_DEGREES) + TICKS_AT_RESTING_ZERO;
        this.ticksBack = (degreesBack * TICKS_TO_DEGREES) + TICKS_AT_RESTING_ZERO;
    }
    */
}
