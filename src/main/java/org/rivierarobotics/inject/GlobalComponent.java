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

package org.rivierarobotics.inject;

import dagger.Component;
import org.rivierarobotics.inject.CommandComponent.CCModule;
import org.rivierarobotics.robot.ButtonConfiguration;
import org.rivierarobotics.robot.ControlsModule;
import org.rivierarobotics.subsystems.*;

import javax.inject.Singleton;

@Component(modules = {SubsystemModule.class, ControlsModule.class, CCModule.class})
@Singleton
public abstract class GlobalComponent {
    public void robotInit() {
        getDriveTrain();
        getPistonController();
        getArmController();
        getHoodController();
        getTentacleController();
        getWinchController();
        getButtonConfiguration();
        getShifter();
    }

    public abstract DriveTrain getDriveTrain();

    public abstract PistonController getPistonController();

    public abstract ArmController getArmController();

    public abstract HoodController getHoodController();

    public abstract TentacleController getTentacleController();

    public abstract WinchController getWinchController();

    public abstract ButtonConfiguration getButtonConfiguration();

    public abstract Shifter getShifter();

}
