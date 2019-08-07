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

import org.rivierarobotics.subsystems.HoodPosition;

import javax.inject.Inject;

public class HoodCommands {
    private final HoodSetPositionCreator hoodSetPositionCreator;
    private final HoodOffsetEncCreator hoodEncoderResetCreator;

    @Inject
    public HoodCommands(HoodSetPositionCreator hoodSetPositionCreator, HoodOffsetEncCreator hoodEncoderResetCreator) {
        this.hoodSetPositionCreator = hoodSetPositionCreator;
        this.hoodEncoderResetCreator = hoodEncoderResetCreator;
    }

    public final HoodSetPosition setFrontPosition(HoodPosition pos) {
        return hoodSetPositionCreator.create(pos, true);
    }

    public final HoodSetPosition setBackPosition(HoodPosition pos) {
        return hoodSetPositionCreator.create(pos, false);
    }

    public final HoodSetPosition setRawPosition(double pos) {
        return hoodSetPositionCreator.create(pos, false, null);
    }

    public final HoodEncoderReset resetEncoder() {
        return hoodEncoderResetCreator.create();
    }
}
