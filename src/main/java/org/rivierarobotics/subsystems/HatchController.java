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

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

@Singleton
public class HatchController extends Subsystem {
    private final Solenoid clampPiston;
    private final Solenoid pushPiston;
    private final Solenoid deployPistonLeft;
    private final Solenoid deployPistonRight;
    private final Solenoid climbPiston;

    @Inject
    public HatchController() {
        clampPiston = new Solenoid(1);
        pushPiston = new Solenoid(2);
        deployPistonLeft = new Solenoid(3);
        deployPistonRight = new Solenoid(4);
        climbPiston = new Solenoid(6);
    }

    private Solenoid pistonFor(Piston piston) {
        if(piston == Piston.CLAMP) {
            return clampPiston;
        } else if(piston == Piston.PUSH) {
            return pushPiston;
        } else if(piston == Piston.DEPLOY_LEFT) {
            return deployPistonLeft;
        } else if(piston == Piston.DEPLOY_RIGHT) {
            return deployPistonRight;
        } else if(piston == Piston.CLIMB) {
            return climbPiston;
        } else {
            throw new IllegalArgumentException("Invalid piston value " + piston);
        }
    }

    public void extendPiston(Piston piston) {
        pistonFor(piston).set(pstateFor(piston));
    }

    public void retractPiston(Piston piston) {
        pistonFor(piston).set(!pstateFor(piston));
    }

    private boolean pstateFor(Piston piston) {
        if(piston == Piston.CLAMP) {
            return false;
        } else {
            return true;
        }
    }

    public boolean getPistonState(Piston piston) {
        return pistonFor(piston).get();
    }

    @Override
    protected void initDefaultCommand() {

    }

}