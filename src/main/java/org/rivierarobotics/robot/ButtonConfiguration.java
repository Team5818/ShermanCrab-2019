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

package org.rivierarobotics.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Scheduler;
import org.rivierarobotics.inject.CommandComponent;
import org.rivierarobotics.inject.Input;
import org.rivierarobotics.subsystems.*;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.Vector;
import java.util.function.DoubleConsumer;

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

    public void initTeleop() {
        clearButtons();

        //shift
        JoystickButton shiftHigh = new JoystickButton(driverLeft, 1);
        shiftHigh.whenPressed(cmds.gear().shift(Gear.HIGH));

        JoystickButton shiftLow = new JoystickButton(driverLeft, 2);
        shiftLow.whenPressed(cmds.gear().shift(Gear.LOW));

        //tentacles
        double tentacleSpeed = 0.5;
        JoystickButton tentaclesFwd = new JoystickButton(codriverRight, 1);
        tentaclesFwd.whileHeld(cmds.tentacle().spin(tentacleSpeed));

        JoystickButton tentaclesBack = new JoystickButton(codriverRight, 2);
        tentaclesBack.whileHeld(cmds.tentacle().spin(-tentacleSpeed));

        //hatch
        JoystickButton deployOpen = new JoystickButton(codriverButtons, 2);
        deployOpen.whenPressed(inOrder(cmds.piston().extend(Piston.DEPLOY_LEFT),
                cmds.piston().extend(Piston.DEPLOY_RIGHT)));

        JoystickButton deployClose = new JoystickButton(codriverButtons, 3);
        deployClose.toggleWhenPressed(inOrder(cmds.piston().retract(Piston.DEPLOY_LEFT),
                cmds.piston().retract(Piston.DEPLOY_RIGHT)));

        JoystickButton hatchPush = new JoystickButton(codriverButtons, 6);
        hatchPush.whenPressed(cmds.hatch().push());
        hatchPush.whenReleased(cmds.hatch().pushCleanup());

        //clamp
        JoystickButton clampOpen = new JoystickButton(codriverButtons, 4);
        clampOpen.whenPressed(cmds.piston().retract(Piston.CLAMP));

        JoystickButton clampClosed = new JoystickButton(codriverButtons, 5);
        clampClosed.whenPressed(cmds.piston().extend(Piston.CLAMP));

        //arm
        JoystickButton armRocketOne = new JoystickButton(codriverButtons, 12);
        armRocketOne.whenPressed(cmds.arm().setPosition(ArmPosition.FRONT_ROCKET_LEVEL_ONE));

        JoystickButton armRocketTwo = new JoystickButton(codriverButtons, 10);
        armRocketTwo.whenPressed(cmds.arm().setPosition(ArmPosition.FRONT_ROCKET_LEVEL_TWO));

        JoystickButton armHatch = new JoystickButton(codriverButtons, 8);
        armHatch.whenPressed(cmds.arm().setPosition(ArmPosition.FRONT_CARGO_SHIP));

        //hood testing
        JoystickButton fwd = new JoystickButton(codriverButtons, 11);
        fwd.whenPressed(cmds.hood().setPosition(HoodPosition.NINETY_ARM_ZERO));

        JoystickButton back = new JoystickButton(codriverButtons, 7);
        back.whenPressed(cmds.hood().setPosition(HoodPosition.NEGATIVE_NINETY_ARM_ZERO));

        JoystickButton zed = new JoystickButton(codriverButtons, 9);
        zed.whenPressed(cmds.hood().setPosition(HoodPosition.RESTING_ARM_ZERO));

        JoystickButton disablePID = new JoystickButton(codriverLeft, 1);
        disablePID.whenPressed(cmds.hood().disablePID());

    }

    public void initTest() {
        clearButtons();
        for (int i = 1; i <= 6; i++) {
            DoubleConsumer out = TestControllers.get(i)::set;
            new JoystickButton(driverButtons, i).whenPressed(cmds.test().motor(
                    () -> -driverLeft.getY(),
                    out));
        }
        for (int i = 7; i <= 9; i++) {
            DoubleConsumer out = TestControllers.get(i)::set;
            new JoystickButton(codriverButtons, i).whenPressed(cmds.test().motor(
                    () -> -codriverLeft.getY(),
                    out));
        }
        new JoystickButton(codriverButtons, 10).whenPressed(cmds.test().motor(
                () -> -codriverLeft.getY(),
                TestControllers.get(10)::set));
        for (int i = 0; i < 6; i++) {
            new JoystickButton(codriverButtons, i + 1).whenPressed(cmds.test().solenoid(new Solenoid(i)::set));
        }
        new JoystickButton(codriverButtons, 11).whenPressed(cmds.tentacle().spin(0.2));
        new JoystickButton(codriverButtons, 12).whenPressed(cmds.tentacle().spin(-0.2));
    }

    @SuppressWarnings("unchecked")
    private void clearButtons() {
        try {
            Field m_buttons = Scheduler.class.getDeclaredField("m_buttons");
            m_buttons.setAccessible(true);
            Vector<Trigger.ButtonScheduler> buttons = (Vector<Trigger.ButtonScheduler>) m_buttons.get(Scheduler.getInstance());
            if (buttons != null) {
                buttons.clear();
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}