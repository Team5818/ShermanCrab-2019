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
    private final Joystick driverButtons;
    private final Joystick codriverButtons;
    private final CommandComponent cmds;

    @Inject
    public ButtonConfiguration(@Input(Input.Position.DRIVER_LEFT) Joystick driverLeft,
                               @Input(Input.Position.DRIVER_RIGHT) Joystick driverRight,
                               @Input(Input.Position.CODRIVER_LEFT) Joystick codriverLeft,
                               @Input(Input.Position.CODRIVER_RIGHT) Joystick codriverRight,
                               @Input(Input.Position.DRIVER_BUTTONS) Joystick driverButtons,
                               @Input(Input.Position.CODRIVER_BUTTONS) Joystick codriverButtons,
                               CommandComponent.Builder component) {
        this.driverLeft = driverLeft;
        this.driverRight = driverRight;
        this.codriverLeft = codriverLeft;
        this.codriverRight = codriverRight;
        this.driverButtons = driverButtons;
        this.codriverButtons = codriverButtons;
        this.cmds = component.build();
    }

    public void initialize() {
        JoystickButton hatchPush = new JoystickButton(driverLeft, 1);
        hatchPush.toggleWhenPressed(cmds.hatch().push());

        JoystickButton hatchDeploy = new JoystickButton(driverLeft, 2);
        hatchDeploy.toggleWhenPressed(cmds.drive().rotateProvided(90));

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