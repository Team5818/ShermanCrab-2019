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

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PistonController extends Subsystem {
    private final Solenoid clampPiston;
    private final Solenoid pushPiston;
    private final Solenoid deployPistonLeft;
    private final Solenoid deployPistonRight;

    //TODO [PracticeBot] [Software] make climb solenoids final if implemented
    /*private Solenoid climbPiston;
    private Solenoid lockClimbPiston;
    private Solenoid helperClimbPiston;

    //TODO [PracticeBot] [Software] uncomment limit switch code in PistonController, make limit switches final
    //TODO [PracticeBot] [CompBot] [Testing] ensure that limit switch logic works
    private DigitalInput leftSwitch;
    private DigitalInput rightSwitch;
    private Solenoid triangleLED;
*/


    @Inject
    public PistonController() {
        //TODO [CompBot] [Testing] ensure that all solenoids are assigned correctly
        clampPiston = new Solenoid(1);
        pushPiston = new Solenoid(2);
        deployPistonLeft = new Solenoid(3);
        deployPistonRight = new Solenoid(4);
        //climbPiston for use with piston climb
        //climbPiston = new Solenoid(6);

        //lockClimbPiston & helperClimbPiston for use with scissor climb
        //lockClimbPiston = new Solenoid(6);
        //helperClimbPiston = new Solenoid(6);

        //leftSwitch = new DigitalInput(5);
        //rightSwitch = new DigitalInput(7);
        //triangleLED = new Solenoid(0);
    }

    private Solenoid pistonFor(Piston piston) {
        if (piston == Piston.CLAMP) {
            return clampPiston;
        } else if (piston == Piston.PUSH) {
            return pushPiston;
        } else if (piston == Piston.DEPLOY_LEFT) {
            return deployPistonLeft;
        } else if (piston == Piston.DEPLOY_RIGHT) {
            return deployPistonRight;
        } /*else if (piston == Piston.CLIMB) {
            return climbPiston;
        } else if (piston == Piston.HELPER_CLIMB) {
            return helperClimbPiston;
        } else if (piston == Piston.LOCK_CLIMB) {
            return lockClimbPiston;
        } */else {
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

    public boolean getTriangleEngaged() {
        return false;
        //return leftSwitch.get() && rightSwitch.get();
    }

    public void setTriangleLED(boolean state) {
        //  triangleLED.set(state);
    }

    @Override
    protected void initDefaultCommand() {

    }

}