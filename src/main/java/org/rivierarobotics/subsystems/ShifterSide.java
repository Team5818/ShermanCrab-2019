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
import org.rivierarobotics.commands.DriveControl;
import org.rivierarobotics.inject.Sided;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class ShifterSide {
    private final Solenoid shifter;

    @Inject
    public ShifterSide(int ch) {
        shifter = new Solenoid(ch);
        shifter.set(false);
    }

    public void setGear(Gear gear) {
        if(gear == Gear.HIGH) {
            shifter.set(true);
        } else if(gear == Gear.LOW) {
            shifter.set(false);
        } else {
            throw new IllegalArgumentException("Invalid gear value " + gear);
        }
    }

    public void swapGear() {
        shifter.set(!shifter.get());
    }
}
