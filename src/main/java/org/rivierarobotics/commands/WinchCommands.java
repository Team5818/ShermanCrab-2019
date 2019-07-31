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

package org.rivierarobotics.commands;

import org.rivierarobotics.subsystems.WinchPosition;

import javax.inject.Inject;

public class WinchCommands {
    private final WinchSetPositionCreator winchSetPositionCreator;
    private final WinchAtPowerCreator winchAtPowerCreator;
    private final WinchSafetyOverrideCreator winchSafetyOverrideCreator;

    @Inject
    public WinchCommands(WinchSetPositionCreator winchSetPositionCreator,
                         WinchAtPowerCreator winchAtPowerCreator,
                         WinchSafetyOverrideCreator winchSafetyOverrideCreator) {
        this.winchSetPositionCreator = winchSetPositionCreator;
        this.winchAtPowerCreator = winchAtPowerCreator;
        this.winchSafetyOverrideCreator = winchSafetyOverrideCreator;
    }

    public final WinchSetPosition set(WinchPosition pos) {
        return winchSetPositionCreator.create(pos.ticks);
    }

    public final WinchSetPosition set(double pos) {
        return winchSetPositionCreator.create(pos);
    }

    public final WinchAtPower atPower(double pwr) {
        return winchAtPowerCreator.create(pwr);
    }

    public final WinchSafetyOverride overrideSafety() {
        return winchSafetyOverrideCreator.create();
    }
}
