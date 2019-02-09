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

package org.rivierarobotics.robot;

import javax.inject.Singleton;

import org.rivierarobotics.inject.Input;

import dagger.Module;
import dagger.Provides;
import edu.wpi.first.wpilibj.Joystick;

@Module
public class ControlsModule {
    private static final int JS_DRIVER_LEFT = 0;
    private static final int JS_DRIVER_RIGHT = 1;
    private static final int JS_CODRIVER_LEFT = 2;
    private static final int JS_CODRIVER_RIGHT = 3;

    @Provides
    @Singleton
    @Input(Input.Position.DRIVER_LEFT)
    public static Joystick provideDriverJoystickLeft() {
        return new Joystick(JS_DRIVER_LEFT);
    }

    @Provides
    @Singleton
    @Input(Input.Position.DRIVER_RIGHT)
    public static Joystick provideDriverJoystickRight() {
        return new Joystick(JS_DRIVER_RIGHT);
    }

    @Provides
    @Singleton
    @Input(Input.Position.CODRIVER_LEFT)
    public static Joystick provideCoDriverJoystickLeft() {
        return new Joystick(JS_CODRIVER_LEFT);
    }

    @Provides
    @Singleton
    @Input(Input.Position.CODRIVER_RIGHT)
    public static Joystick provideCoDriverJoystickRight() {
        return new Joystick(JS_CODRIVER_RIGHT);
    }
}