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
    RESTING_ARM_ZERO(0, 0, false, false),
    RESTING_ARM_ONE_HUNDRED_EIGHTY(180, 180, false, false),

    ROCKET_LEVEL_ONE(42, -32, true, false),
    ROCKET_LEVEL_TWO(97, -87, false, true),
    CARGO_SHIP(138, -105, false, true),
    COLLECT(42, -32, false, true);

    public final double degreesFront;
    public final double degreesBack;
    public final double ticksFront;
    public final double ticksBack;
    public final boolean tentacleInvertFront;
    public final boolean tentacleInvertBack;

    HoodPosition(double degreesFront, double degreesBack, boolean tentacleInvertFront, boolean tentacleInvertBack) {
        this.tentacleInvertFront = tentacleInvertFront;
        this.tentacleInvertBack = tentacleInvertBack;
        this.degreesFront = degreesFront;
        this.degreesBack = degreesBack;
        this.ticksFront = degreesFront * HoodController.ANGLE_SCALE;
        this.ticksBack = degreesBack * HoodController.ANGLE_SCALE;
    }
}
