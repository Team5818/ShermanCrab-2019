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