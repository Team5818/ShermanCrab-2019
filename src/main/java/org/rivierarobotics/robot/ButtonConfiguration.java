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

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Scheduler;
import org.rivierarobotics.inject.CommandComponent;
import org.rivierarobotics.inject.Input;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.Vector;
import java.util.function.DoubleConsumer;

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
        /*
        //shift
       JoystickButton shiftHigh = new JoystickButton(driverLeft,1);
       shiftHigh.whenPressed(cmds.gear().shift(Gear.HIGH));

       JoystickButton shiftLow = new JoystickButton(driverLeft, 2);
       shiftLow.whenPressed(cmds.gear().shift(Gear.LOW));

       JoystickButton swapGear = new JoystickButton(driverRight, 1);
       swapGear.whenPressed(cmds.gear().swap());

       //hatch
       JoystickButton hatchDeploy = new JoystickButton(codriverButtons, 12);
       hatchDeploy.whenPressed(inOrder(cmds.piston().extend(Piston.DEPLOY_LEFT),
               cmds.piston().extend(Piston.DEPLOY_RIGHT)));
       hatchDeploy.whenReleased(inOrder(cmds.piston().retract(Piston.DEPLOY_LEFT),
               cmds.piston().retract(Piston.DEPLOY_RIGHT)));

       //clamp
       JoystickButton clampActionToggle = new JoystickButton(codriverButtons, 11);
       clampActionToggle.whenPressed(inOrder(cmds.piston().extend(Piston.CLAMP_LEFT),
               cmds.piston().extend(Piston.CLAMP_RIGHT)));
       clampActionToggle.whenReleased(inOrder(cmds.piston().retract(Piston.CLAMP_LEFT),
               cmds.piston().retract(Piston.CLAMP_RIGHT)));

       JoystickButton clampActionPress = new JoystickButton(codriverButtons, 5);
       clampActionPress.whenPressed(inOrder(cmds.piston().extend(Piston.CLAMP_LEFT),
                cmds.piston().extend(Piston.CLAMP_RIGHT)));
       clampActionPress.whenReleased(inOrder(cmds.piston().retract(Piston.CLAMP_LEFT),
                cmds.piston().retract(Piston.CLAMP_RIGHT)));

       JoystickButton tentaclesFwd = new JoystickButton(codriverButtons, 4);
       tentaclesFwd.whenPressed(cmds.tentacle().spin(1.0));
       tentaclesFwd.whenReleased(cmds.tentacle().spin(0.0));

        JoystickButton tentaclesBack = new JoystickButton(codriverButtons, 3);
        tentaclesBack.whenPressed(cmds.tentacle().spin(-1.0));
        tentaclesBack.whenReleased(cmds.tentacle().spin(0.0));
        */
        JoystickButton collectFront = new JoystickButton(codriverButtons, 1);
        collectFront.whenPressed(cmds.hood().set(2515));

        JoystickButton collectBack = new JoystickButton(codriverButtons, 3);
        collectBack.whenPressed(cmds.hood().set(1445));

        JoystickButton stow = new JoystickButton(codriverButtons, 2);
        stow.whenPressed(cmds.hood().set(1980));
    }

    public void initTest() {
        clearButtons();
        for (int i = 1; i <= 6; i++) {
            DoubleConsumer out;
            if (i % 3 == 1) {
                out = new WPI_TalonSRX(i)::set;
            } else {
                CANSparkMax sm = new CANSparkMax(i, CANSparkMaxLowLevel.MotorType.kBrushless);
                sm.follow(CANSparkMax.ExternalFollower.kFollowerDisabled, 0);
                out = sm::set;
            }
            new JoystickButton(driverButtons, i).whenPressed(cmds.test().motor(
                    () -> -driverLeft.getY(),
                    out));
        }
        for (int i = 7; i <= 9; i++) {
            DoubleConsumer out;
            if (i % 3 == 1) {
                out = new WPI_TalonSRX(i)::set;
            } else {
                CANSparkMax sm = new CANSparkMax(i, CANSparkMaxLowLevel.MotorType.kBrushless);
                sm.follow(CANSparkMax.ExternalFollower.kFollowerDisabled, 0);
                out = sm::set;
            }
            new JoystickButton(codriverButtons, i).whenPressed(cmds.test().motor(
                    () -> -codriverLeft.getY(),
                    out));
        }
        new JoystickButton(codriverButtons, 10).whenPressed(cmds.test().motor(
                () -> -codriverLeft.getY(),
                new WPI_TalonSRX(10)::set));
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