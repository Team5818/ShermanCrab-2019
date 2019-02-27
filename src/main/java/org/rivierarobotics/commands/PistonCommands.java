/*
 * This file is part of Placeholder-2019, licensed under the GNU General Public License (GPLv3).
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

import org.rivierarobotics.subsystems.Piston;

import javax.inject.Inject;

public class PistonCommands {
    private final ExtendPistonCreator extendPistonCreator;
    private final RetractPistonCreator retractPistonCreator;

    @Inject
    public PistonCommands(ExtendPistonCreator extendPistonCreator, RetractPistonCreator retractPistonCreator) {
        this.extendPistonCreator = extendPistonCreator;
        this.retractPistonCreator = retractPistonCreator;
    }

    public final ExtendPiston extend(Piston piston) {
        return extendPistonCreator.create(piston);
    }

    public final RetractPiston retract(Piston piston) {
        return retractPistonCreator.create(piston);
    }
}
