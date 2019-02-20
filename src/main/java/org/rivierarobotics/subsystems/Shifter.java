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

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Shifter extends Subsystem {
    private Solenoid shift;

    @Inject
    public Shifter(int ch) {
        shift = new Solenoid(ch);
    }

    public void setGear(Gear gear) {
        if(gear.equals(Gear.HIGH) || gear.equals(Gear.LOW)) {
            shift.set(gear.gearToBool());
        } else {
            throw new IllegalArgumentException("Invalid gear value " + gear);
        }
    }

    public void swapGear() {
        shift.set(!shift.get());
    }

    @Override
    protected void initDefaultCommand() {

    }
}
