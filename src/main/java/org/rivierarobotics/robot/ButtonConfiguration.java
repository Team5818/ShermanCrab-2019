package org.rivierarobotics.robot;

import javax.inject.Inject;

import org.rivierarobotics.inject.CommandComponent;
import org.rivierarobotics.inject.Input;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class ButtonConfiguration {
    private final Joystick driverLeft;
    private final Joystick driverRight;
    private final Joystick codriverLeft;
    private final Joystick codriverRight;
    private final CommandComponent commands;

    @Inject
    public ButtonConfiguration(@Input(Input.Position.DRIVER_LEFT) Joystick driverLeft,
            @Input(Input.Position.DRIVER_RIGHT) Joystick driverRight,
            @Input(Input.Position.CODRIVER_LEFT) Joystick codriverLeft,
            @Input(Input.Position.CODRIVER_RIGHT) Joystick codriverRight, CommandComponent.Builder component) {
        this.driverLeft = driverLeft;
        this.driverRight = driverRight;
        this.codriverLeft = codriverLeft;
        this.codriverRight = codriverRight;
        this.commands = component.build();
    }

    public void initialize() {
        JoystickButton hatchPull = new JoystickButton(driverRight, 1);
        hatchPull.whenPressed(commands.newHatchPull());

        JoystickButton bothPistonMove = new JoystickButton(driverLeft, 1);
        bothPistonMove.whenPressed(commands.newExtendBoth());
        bothPistonMove.whenReleased(commands.newRetractBoth());
    }
}
