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
import org.rivierarobotics.subsystems.SuctionController;
import org.rivierarobotics.subsystems.SuctionMotorPosition;

@GenerateCreator
public class SuctionMotorSet extends InstantCommand {
    private final SuctionController suction;
    private final SuctionMotorPosition pos;

    public SuctionMotorSet(@Provided SuctionController suction, SuctionMotorPosition pos) {
        this.suction = suction;
        this.pos = pos;
        requires(suction);
    }

    @Override
    protected void execute() {
        suction.setAngle(pos.ticks);
    }
}
