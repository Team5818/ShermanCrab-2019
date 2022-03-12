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

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.rivierarobotics.util.Logging;
import org.rivierarobotics.util.MechLogger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PistonController extends SubsystemBase {
    private final Solenoid clampPiston;
    private final Solenoid pushPiston;
    private final Solenoid deployPiston;

    private final Solenoid helperClimbPiston;
    private final Solenoid lockClimbPiston;

    private final MechLogger logger;

    @Inject
    public PistonController() {
        clampPiston = new Solenoid(PneumaticsModuleType.CTREPCM, 1);
        pushPiston = new Solenoid(PneumaticsModuleType.CTREPCM, 2);
        deployPiston = new Solenoid(PneumaticsModuleType.CTREPCM, 3);
        helperClimbPiston = new Solenoid(PneumaticsModuleType.CTREPCM, 4);
        lockClimbPiston = new Solenoid(PneumaticsModuleType.CTREPCM, 5);
        this.logger = Logging.getLogger(getClass());
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
        logState(piston, piston.extend);
    }

    public void retractPiston(Piston piston) {
        pistonFor(piston).set(!piston.extend);
        logState(piston, !piston.extend);
    }

    public boolean getPistonState(Piston piston) {
        return pistonFor(piston).get();
    }

    private void logState(Piston piston, boolean state) {
        logger.conditionChange(piston.name() + "_swState", state == piston.extend ? "extended" : "retracted");
        logger.conditionChange(piston.name() + "_hwState", state ? "extended" : "retracted");
    }

    public void swap(Piston piston) {
        boolean state = !pistonFor(piston).get();
        pistonFor(piston).set(state);
        logState(piston, state);
    }
}