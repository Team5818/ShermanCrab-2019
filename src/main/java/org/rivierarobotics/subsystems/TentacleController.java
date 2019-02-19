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

package org.rivierarobotics.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TentacleController extends Subsystem {
    private final WPI_TalonSRX tentacles;

    @Inject
    public TentacleController(int tent) {
        tentacles = new WPI_TalonSRX(tent);
    }
    
    public void setPower(double pwr) {
        tentacles.set(pwr);
    }

    public void stop() {
        setPower(0.0);
    }

    @Override
    protected void initDefaultCommand() {
    }
}
