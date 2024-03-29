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

    ROCKET_LEVEL_ONE(47),
    ROCKET_LEVEL_TWO(22),
    CARGO_SHIP(40),
    COLLECT(90),

    CLIMB_INITIAL(82),
    CLIMB_PUSH(106);

    public final static double TICKS_AT_ZERO = 2835;
    private final static double TICKS_AT_NINETY = 3835;
    public static final double TICKS_TO_DEGREES = (TICKS_AT_NINETY - TICKS_AT_ZERO) / 90;
    public final double degreesFront, degreesBack, ticksFront, ticksBack;

    ArmPosition(double degrees) {
        this.degreesFront = degrees;
        this.degreesBack = -degrees;
        this.ticksFront = (degreesFront * TICKS_TO_DEGREES) + TICKS_AT_ZERO;
        this.ticksBack = (degreesBack * TICKS_TO_DEGREES) + TICKS_AT_ZERO;
    }
}
