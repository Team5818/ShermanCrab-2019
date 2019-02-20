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

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.subsystems.HoodController;
import org.rivierarobotics.subsystems.HoodPosition;

@GenerateCreator
public class HoodSet extends InstantCommand {
    private final HoodController hood;
    private final int pos;

    public HoodSet(@Provided HoodController hood, int pos) {
        this.hood = hood;
        this.pos = pos;
        requires(hood);
    }

    @Override
    protected void execute() {
        hood.setAngle(pos);
    }

    private int enumToAngle(HoodPosition pos) {
        if(pos == HoodPosition.NINETY_DEGREES) {
            return 90;
        } else if(pos == HoodPosition.ONEHUNDREDEIGHTY_DEGREES) {
            return 180;
        } else if(pos == HoodPosition.ZERO_DEGREES) {
            return 0;
        } else {
            throw new IllegalArgumentException("Invalid hood position" + pos);
        }
    }

    //@Override
    //protected boolean isFinished() {
    //    return hood.getAngle() >= enumToAngle(pos);
    //}
}
