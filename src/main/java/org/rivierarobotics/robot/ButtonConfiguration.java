package org.rivierarobotics.robot;

import javax.inject.Inject;

import org.rivierarobotics.inject.CommandComponent;
import org.rivierarobotics.inject.Input;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.rivierarobotics.subsystems.Piston;

import static org.rivierarobotics.commands.CommandGroups.inParallel;
import static org.rivierarobotics.commands.CommandGroups.inOrder;

public class ButtonConfiguration {
    private final Joystick driverLeft;
    private final Joystick driverRight;
    private final Joystick codriverLeft;
    private final Joystick codriverRight;
    private final CommandComponent cmds;

    @Inject
    public ButtonConfiguration(@Input(Input.Position.DRIVER_LEFT) Joystick driverLeft,
            @Input(Input.Position.DRIVER_RIGHT) Joystick driverRight,
            @Input(Input.Position.CODRIVER_LEFT) Joystick codriverLeft,
            @Input(Input.Position.CODRIVER_RIGHT) Joystick codriverRight, CommandComponent.Builder component) {
        this.driverLeft = driverLeft;
        this.driverRight = driverRight;
        this.codriverLeft = codriverLeft;
        this.codriverRight = codriverRight;
        this.cmds = component.build();
    }

    public void initialize() {
        JoystickButton hatchPull = new JoystickButton(driverLeft, 1);
        hatchPull.whenPressed(inOrder(cmds.piston().extend(Piston.GRAB_LOWER), cmds.piston().extend(Piston.GRAB_UPPER)));
        hatchPull.whenReleased(cmds.hatch().pull());

        JoystickButton grabAction = new JoystickButton(driverRight, 1);
        grabAction.whenPressed(inOrder(cmds.piston().extend(Piston.GRAB_LOWER), cmds.piston().extend(Piston.GRAB_UPPER)));
        grabAction.whenReleased(inOrder(cmds.piston().retract(Piston.GRAB_LOWER), cmds.piston().retract(Piston.GRAB_UPPER)));

        JoystickButton pushAction = new JoystickButton(driverRight, 2);
        pushAction.whenPressed(inOrder(cmds.piston().extend(Piston.PUSH_LOWER), cmds.piston().extend(Piston.PUSH_UPPER)));
        pushAction.whenReleased(inOrder(cmds.piston().retract(Piston.PUSH_LOWER), cmds.piston().retract(Piston.PUSH_UPPER)));
    }
}
