package org.rivierarobotics.robot;

import javax.inject.Inject;

import org.rivierarobotics.commands.DriveVelocity;
import org.rivierarobotics.inject.CommandComponent;
import org.rivierarobotics.inject.Input;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.rivierarobotics.subsystems.Piston;

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
        JoystickButton hatchPush = new JoystickButton(driverLeft, 1);
        hatchPush.toggleWhenPressed(cmds.hatch().push());

        JoystickButton driveDistance = new JoystickButton(driverLeft, 2);
        driveDistance.toggleWhenPressed(cmds.drive().rotate(90));

        JoystickButton clampAction = new JoystickButton(driverRight, 1);
        clampAction.whenPressed(inOrder(cmds.piston().extend(Piston.CLAMP_RIGHT),
                cmds.piston().extend(Piston.CLAMP_LEFT)));
        clampAction.whenReleased(inOrder(cmds.piston().retract(Piston.CLAMP_RIGHT),
                cmds.piston().retract(Piston.CLAMP_LEFT)));

        JoystickButton pushAction = new JoystickButton(driverRight, 2);
        pushAction.whenPressed(inOrder(cmds.piston().extend(Piston.PUSH_LOWER),
                cmds.piston().extend(Piston.PUSH_UPPER)));
        pushAction.whenReleased(inOrder(cmds.piston().retract(Piston.PUSH_LOWER),
                cmds.piston().retract(Piston.PUSH_UPPER)));
    }
}