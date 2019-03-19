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

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PistonController extends Subsystem {
    private final Solenoid clampPiston;
    private final Solenoid pushPiston;
    private final Solenoid deployPiston;

    private final Solenoid helperClimbPiston;
    private final Solenoid lockClimbPiston;

    @Inject
    public PistonController() {
        clampPiston = new Solenoid(1);
        pushPiston = new Solenoid(2);
        deployPiston = new Solenoid(3);
        helperClimbPiston = new Solenoid(4);
        lockClimbPiston = new Solenoid(5);
    }

    private Solenoid pistonFor(Piston piston) {
        if (piston == Piston.CLAMP) {
            return clampPiston;
        } else if (piston == Piston.PUSH) {
            return pushPiston;
        } else if (piston == Piston.DEPLOY) {
            return deployPiston;
        } else if (piston == Piston.HELPER_CLIMB) {
            return helperClimbPiston;
        } else if (piston == Piston.LOCK_CLIMB) {
            return lockClimbPiston;
        } else {
            throw new IllegalArgumentException("Invalid piston value " + piston);
        }
    }

    public void extendPiston(Piston piston) {
        pistonFor(piston).set(piston.extend);
    }

    public void retractPiston(Piston piston) {
        pistonFor(piston).set(!piston.extend);
    }

    public boolean getPistonState(Piston piston) {
        return pistonFor(piston).get();
    }

    public void swap(Piston piston) {
        pistonFor(piston).set(!pistonFor(piston).get());
    }

    @Override
    protected void initDefaultCommand() {

    }

}