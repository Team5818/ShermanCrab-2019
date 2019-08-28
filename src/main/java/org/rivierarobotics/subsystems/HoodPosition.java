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
    RESTING_ARM_ZERO(0, TentacleInvert.NEITHER),
    ROCKET_LEVEL_ONE(22, TentacleInvert.FRONT_ONLY),
    ROCKET_LEVEL_TWO(86, TentacleInvert.BACK_ONLY),
    CARGO_SHIP(120, TentacleInvert.BACK_ONLY),
    COLLECT(6, TentacleInvert.BACK_ONLY),
    CLIMB(67, TentacleInvert.NEITHER);

    public final double degreesFront, degreesBack, ticksFront, ticksBack;
    public final boolean tentacleInvertFront, tentacleInvertBack;

    HoodPosition(double degrees, TentacleInvert tentacleInvert) {
        this.tentacleInvertFront = tentacleInvert.front;
        this.tentacleInvertBack = tentacleInvert.back;
        this.degreesFront = degrees;
        this.degreesBack = -degrees;
        this.ticksFront = MathUtil.toTicks(degreesFront);
        this.ticksBack = MathUtil.toTicks(degreesBack);
    }

    private enum TentacleInvert {
        FRONT_AND_BACK(true, true),
        FRONT_ONLY(true, false),
        BACK_ONLY(false, true),
        NEITHER(false, false);

        public final boolean front, back;

        TentacleInvert(boolean front, boolean back) {
            this.front = front;
            this.back = back;
        }
    }
}
