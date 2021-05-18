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

import org.rivierarobotics.util.MathUtil;

public enum HoodPosition {
    RESTING_ARM_ZERO(0, HoodTentacleInvert.NEITHER),
    ROCKET_LEVEL_ONE(22, HoodTentacleInvert.FRONT_ONLY),
    ROCKET_LEVEL_TWO(86, HoodTentacleInvert.BACK_ONLY),
    CARGO_SHIP(120, HoodTentacleInvert.BACK_ONLY),
    COLLECT(6, HoodTentacleInvert.BACK_ONLY),
    CLIMB(67, HoodTentacleInvert.NEITHER);

    public final HoodTentacleInvert tentacleInvert;
    public final double degreesFront;
    public final double degreesBack;
    public final double ticksFront;
    public final double ticksBack;

    HoodPosition(double degrees, HoodTentacleInvert tentacleInvert) {
        this.tentacleInvert = tentacleInvert;
        this.degreesFront = degrees;
        this.degreesBack = -degrees;
        this.ticksFront = toTicks(degreesFront);
        this.ticksBack = toTicks(degreesBack);
    }

    private static int toTicks(double deg) {
        return MathUtil.moduloPositive((int) (deg * HoodController.ANGLE_SCALE), 4096);
    }
}
