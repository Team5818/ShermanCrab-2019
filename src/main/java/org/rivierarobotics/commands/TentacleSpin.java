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

import edu.wpi.first.wpilibj2.command.CommandBase;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.subsystems.HoodController;
import org.rivierarobotics.subsystems.TentacleController;

@GenerateCreator
public class TentacleSpin extends CommandBase {
    private final double power;
    private final TentacleController tentacle;

    public TentacleSpin(@Provided TentacleController tentacle, double power) {
        this.tentacle = tentacle;
        this.power = power;
        addRequirements(tentacle);
    }

    @Override
    public void initialize() {
        double power = this.power;
        if (HoodController.CURRENT_HOOD_POSITION != null) {
            if ((HoodController.HOOD_FRONT && HoodController.CURRENT_HOOD_POSITION.tentacleInvert.front)
                    || (!HoodController.HOOD_FRONT && HoodController.CURRENT_HOOD_POSITION.tentacleInvert.back)) {
                power *= -1;
            }
        }
        tentacle.setPower(power);
    }

    @Override
    public void end(boolean interrupted) {
        tentacle.setPower(0.0);
    }
}
