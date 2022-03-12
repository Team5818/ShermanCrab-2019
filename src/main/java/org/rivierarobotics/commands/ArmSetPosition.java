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

import edu.wpi.first.wpilibj2.command.InstantCommand;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.subsystems.ArmController;
import org.rivierarobotics.subsystems.ArmPosition;

@GenerateCreator
public class ArmSetPosition extends InstantCommand {
    private final ArmController arm;
    private final double pos;

    public ArmSetPosition(@Provided ArmController arm, ArmPosition pos) {
        this(arm, arm.front ? pos.ticksFront : pos.ticksBack);
        addRequirements(arm);
    }

    public ArmSetPosition(@Provided ArmController arm, double pos) {
        this.arm = arm;
        this.pos = pos;
        addRequirements(arm);
    }

    @Override
    public void execute() {
        arm.setAngle(pos);
    }
}
