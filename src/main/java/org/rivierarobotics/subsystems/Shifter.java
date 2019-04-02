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

package org.rivierarobotics.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.rivierarobotics.util.Logging;
import org.rivierarobotics.util.MechLogger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Shifter extends Subsystem {
    private final Solenoid shift;
    private final MechLogger logger;

    @Inject
    public Shifter(int ch) {
        shift = new Solenoid(ch);
        this.logger = Logging.getLogger(getClass());
    }

    public void setGear(Gear gear) {
        shift.set(gear.state);
        logger.conditionChange("gear", gear.state ? "high" : "low");
    }

    public void swapGear() {
        logger.conditionChange("gear", !shift.get() ? "high" : "low");
        shift.set(!shift.get());
    }

    public boolean getGearState() {
        return shift.get();
    }

    @Override
    protected void initDefaultCommand() {

    }
}
