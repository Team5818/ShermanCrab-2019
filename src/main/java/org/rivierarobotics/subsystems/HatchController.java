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
    private final Solenoid clampPistonRight;
    private final Solenoid clampPistonLeft;
    private final Solenoid pushPistonLower;
    private final Solenoid pushPistonUpper;
    private final Solenoid deployPistonLeft;
    private final Solenoid deployPistonRight;

    @Inject
    public HatchController() {
        // TODO change solenoid IDs
        pushPistonLower = new Solenoid(0);
        pushPistonUpper = new Solenoid(1);
        clampPistonLeft = new Solenoid(2);
        clampPistonRight = new Solenoid(3);
        deployPistonLeft = new Solenoid(4);
        deployPistonRight = new Solenoid(5);

        var tab = Shuffleboard.getTab("Solenoid");
        tab.add(clampPistonRight);
        tab.add(clampPistonLeft);
    }

    private Solenoid pistonFor(Piston piston) {
        if(piston == Piston.CLAMP_RIGHT) {
            return clampPistonRight;
        } else if(piston == Piston.CLAMP_LEFT) {
            return clampPistonLeft;
        } else if(piston == Piston.PUSH_LOWER) {
            return pushPistonLower;
        } else if(piston == Piston.PUSH_UPPER) {
            return pushPistonUpper;
        } else if(piston == Piston.DEPLOY_LEFT) {
            return deployPistonLeft;
        } else if(piston == Piston.DEPLOY_RIGHT) {
            return deployPistonRight;
        } else {
            throw new IllegalArgumentException("Invalid piston value " + piston);
        }
    }

    public void extendPiston(Piston piston) {
        pistonFor(piston).set(true);
    }

    public void retractPiston(Piston piston) {
        pistonFor(piston).set(false);
    }

    public boolean getPistonState(Piston piston) {
        return pistonFor(piston).get();
    }

    @Override
    protected void initDefaultCommand() {

    }

}