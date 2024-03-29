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

import org.rivierarobotics.subsystems.Gear;

import javax.inject.Inject;

public class GearCommands {
    private final ShiftGearCreator shiftGearCreator;
    private final SwapGearCreator swapGearCreator;
    private final FullShiftCreator fullShiftCreator;

    @Inject
    public GearCommands(ShiftGearCreator shiftGearCreator,
                        SwapGearCreator swapGearCreator,
                        FullShiftCreator fullShiftCreator) {
        this.shiftGearCreator = shiftGearCreator;
        this.swapGearCreator = swapGearCreator;
        this.fullShiftCreator = fullShiftCreator;
    }

    public ShiftGear shift(Gear gear) {
        return shiftGearCreator.create(gear);
    }

    public SwapGear swap() {
        return swapGearCreator.create();
    }

    public FullShift fullShift(Gear gear) {
        return fullShiftCreator.create(gear);
    }
}
