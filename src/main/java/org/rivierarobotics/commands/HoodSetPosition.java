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

import edu.wpi.first.wpilibj.command.InstantCommand;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.subsystems.HoodController;
import org.rivierarobotics.subsystems.HoodPosition;

@GenerateCreator
public class HoodSetPosition extends InstantCommand {
    private final HoodController hood;
    private final HoodPosition position;
    private final double ticks;
    private final boolean front;

    public HoodSetPosition(@Provided HoodController hood, HoodPosition pos, boolean front) {
        this(hood, front ? pos.ticksFront : pos.ticksBack, front, pos);
    }

    public HoodSetPosition(@Provided HoodController hood, double ticks, boolean front, HoodPosition position) {
        this.ticks = ticks;
        this.hood = hood;
        this.front = front;
        this.position = position;
        requires(hood);
    }

    @Override
    protected void execute() {
        hood.setAngle(ticks);
        HoodController.CURRENT_HOOD_POSITION = position;
        HoodController.HOOD_FRONT = front;
    }
}
