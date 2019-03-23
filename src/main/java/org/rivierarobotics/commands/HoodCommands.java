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

import org.rivierarobotics.subsystems.HoodController;
import org.rivierarobotics.subsystems.HoodPosition;

import javax.inject.Inject;

public class HoodCommands {
    private final HoodSetPositionCreator hoodSetPositionCreator;
    private final HoodOffsetEncCreator hoodOffsetEncCreator;

    @Inject
    public HoodCommands(HoodSetPositionCreator hoodSetPositionCreator, HoodOffsetEncCreator hoodOffsetEncCreator) {
        this.hoodSetPositionCreator = hoodSetPositionCreator;
        this.hoodOffsetEncCreator = hoodOffsetEncCreator;
    }

    public final HoodSetPosition setFrontPosition(HoodPosition pos) {
        HoodController.CURRENT_HOOD_POSITION = pos;
        HoodController.HOOD_FRONT = true;
        return hoodSetPositionCreator.create(pos.ticksFront);
    }

    public final HoodSetPosition setBackPosition(HoodPosition pos) {
        HoodController.CURRENT_HOOD_POSITION = pos;
        HoodController.HOOD_FRONT = false;
        return hoodSetPositionCreator.create(pos.ticksBack);
    }

    public final HoodSetPosition setRawPosition(double pos) {
        return hoodSetPositionCreator.create(pos);
    }

    public final HoodOffsetEnc offsetEnc() {
        return hoodOffsetEncCreator.create();
    }
}
