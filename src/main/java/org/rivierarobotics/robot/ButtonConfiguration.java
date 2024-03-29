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
        final double tentacleSpeed = 0.75;
        JoystickButton tentaclesAction = new JoystickButton(driverRight, 1);
        tentaclesAction.whileHeld(cmds.tentacle().spin(tentacleSpeed));

        JoystickButton tentaclesReverse = new JoystickButton(driverRight, 2);
        tentaclesReverse.whileHeld(cmds.tentacle().spin(-tentacleSpeed));

        //hatch
        JoystickButton deployOpen = new JoystickButton(codriverLeft, 2);
        deployOpen.whenPressed(cmds.piston().extend(Piston.DEPLOY));

        JoystickButton deployClose = new JoystickButton(codriverLeft, 1);
        deployClose.toggleWhenPressed(cmds.piston().retract(Piston.DEPLOY));

        JoystickButton hatchPush = new JoystickButton(codriverButtons, 8);
        hatchPush.whenPressed(cmds.hatch().push());
        hatchPush.whenReleased(cmds.piston().retract(Piston.PUSH));

        //clamp
        JoystickButton clampOpen = new JoystickButton(codriverRight, 1);
        clampOpen.whenPressed(cmds.piston().retract(Piston.CLAMP));

        JoystickButton clampClosed = new JoystickButton(codriverRight, 2);
        clampClosed.whenPressed(cmds.piston().extend(Piston.CLAMP));

        //hood & arm
        JoystickButton frontRocketTwo = new JoystickButton(codriverButtons, 4);
        frontRocketTwo.whenPressed(inOrder(cmds.hood().setFrontPosition(HoodPosition.ROCKET_LEVEL_TWO),
                cmds.arm().setFrontPosition(ArmPosition.ROCKET_LEVEL_TWO)));

        JoystickButton backRocketTwo = new JoystickButton(codriverButtons, 1);
        backRocketTwo.whenPressed(inOrder(cmds.hood().setBackPosition(HoodPosition.ROCKET_LEVEL_TWO),
                cmds.arm().setBackPosition(ArmPosition.ROCKET_LEVEL_TWO)));

        JoystickButton frontRocketOne = new JoystickButton(codriverButtons, 5);
        frontRocketOne.whenPressed(inOrder(cmds.hood().setFrontPosition(HoodPosition.ROCKET_LEVEL_ONE),
                cmds.arm().setFrontPosition(ArmPosition.ROCKET_LEVEL_ONE)));

        JoystickButton backRocketOne = new JoystickButton(codriverButtons, 2);
        backRocketOne.whenPressed(inOrder(cmds.hood().setBackPosition(HoodPosition.ROCKET_LEVEL_ONE),
                cmds.arm().setBackPosition(ArmPosition.ROCKET_LEVEL_ONE)));

        JoystickButton frontCargo = new JoystickButton(codriverButtons, 6);
        frontCargo.whenPressed(inOrder(cmds.hood().setFrontPosition(HoodPosition.CARGO_SHIP),
                cmds.arm().setFrontPosition(ArmPosition.CARGO_SHIP)));

        JoystickButton backCargo = new JoystickButton(codriverButtons, 3);
        backCargo.whenPressed(inOrder(cmds.hood().setBackPosition(HoodPosition.CARGO_SHIP),
                cmds.arm().setBackPosition(ArmPosition.CARGO_SHIP)));

        JoystickButton frontCollect = new JoystickButton(codriverButtons, 11);
        frontCollect.whenPressed(inOrder(cmds.hood().setFrontPosition(HoodPosition.COLLECT),
                cmds.arm().setFrontPosition(ArmPosition.COLLECT)));

        JoystickButton backCollect = new JoystickButton(codriverButtons, 9);
        backCollect.whenPressed(inOrder(cmds.hood().setBackPosition(HoodPosition.COLLECT),
                cmds.arm().setBackPosition(ArmPosition.COLLECT)));

        JoystickButton zeroArm = new JoystickButton(codriverButtons, 7);
        zeroArm.whenPressed(inOrder(cmds.hood().setFrontPosition(HoodPosition.RESTING_ARM_ZERO),
                cmds.arm().setFrontPosition(ArmPosition.ZERO_DEGREES)));

        //resets
        JoystickButton coDriverRetractAll = new JoystickButton(codriverButtons, 12);
        coDriverRetractAll.whenPressed(inOrder(cmds.piston().retract(Piston.DEPLOY),
                cmds.arm().setFrontPosition(ArmPosition.ZERO_DEGREES),
                cmds.hood().setFrontPosition(HoodPosition.RESTING_ARM_ZERO)));

        JoystickButton driverRetractAll = new JoystickButton(driverButtons, 6);
        driverRetractAll.whenPressed(inOrder(cmds.piston().retract(Piston.DEPLOY),
                cmds.arm().setFrontPosition(ArmPosition.ZERO_DEGREES),
                cmds.hood().setFrontPosition(HoodPosition.RESTING_ARM_ZERO)));

        JoystickButton overrideWinchSafety = new JoystickButton(driverButtons, 3);
        overrideWinchSafety.whenPressed(cmds.winch().overrideSafety());

        JoystickButton resetHoodEncoder = new JoystickButton(codriverButtons, 10);
        resetHoodEncoder.whenPressed(cmds.hood().resetEncoder());

        JoystickButton resetWinchEncoder = new JoystickButton(driverButtons, 5);
        resetWinchEncoder.whenPressed(cmds.winch().resetEncoder());

        //l3 climb
        JoystickButton autoClimb = new JoystickButton(driverButtons, 8);
        autoClimb.whenPressed(cmds.climb().auto());

        JoystickButton winchFwd = new JoystickButton(driverButtons, 12);
        winchFwd.whenPressed(cmds.winch().atPower(1.0));
        winchFwd.whenReleased(cmds.winch().atPower(0.0));

        JoystickButton winchBack = new JoystickButton(driverButtons, 11);
        winchBack.whenPressed(cmds.winch().atPower(-0.5));
        winchBack.whenReleased(cmds.winch().atPower(0.0));

        JoystickButton helperPistonRetract = new JoystickButton(driverButtons, 9);
        helperPistonRetract.whenPressed(cmds.piston().retract(Piston.HELPER_CLIMB));

        JoystickButton helperPistonExtend = new JoystickButton(driverButtons, 10);
        helperPistonExtend.whenPressed(cmds.piston().extend(Piston.HELPER_CLIMB));

        JoystickButton lockPistonSwap = new JoystickButton(driverButtons, 7);
        lockPistonSwap.whenPressed(cmds.piston().swap(Piston.LOCK_CLIMB));
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