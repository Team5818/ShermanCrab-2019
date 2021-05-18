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

package org.rivierarobotics.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.rivierarobotics.util.AbstractPIDSource;
import org.rivierarobotics.util.Logging;
import org.rivierarobotics.util.MechLogger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WinchController extends Subsystem {
    private final MechLogger logger;
    private final PIDController pidLoop;
    private final PistonController pistonController;
    private static final double P = 0.0025;
    private static final double I = 0.0;
    private static final double D = 0.0;
    private static final double F = 0.0;
    public boolean lockOverride = false;
    private CANSparkMax winch;
    private DigitalInput climbLimitSwitch;

    @Inject
    public WinchController(PistonController pistonController, int spark, int limit) {
        this.pistonController = pistonController;
        logger = Logging.getLogger(getClass());
        winch = new CANSparkMax(spark, CANSparkMaxLowLevel.MotorType.kBrushless);
        climbLimitSwitch = new DigitalInput(limit);
        pidLoop = new PIDController(P, I, D, F, new AbstractPIDSource(this::getDistance), this::rawSetPower, 0.01);

        winch.setInverted(true);
        winch.setIdleMode(CANSparkMax.IdleMode.kBrake);
        logger.conditionChange("neutral_mode", "brake");
        resetEncoder();
    }

    public int getDistance() {
        return (int) (winch.getEncoder().getPosition());
    }

    public void setPosition(double position) {
        logger.setpointChange(position);
        pidLoop.setSetpoint(position);
        pidLoop.enable();
        logger.conditionChange("pid_loop", "enabled");
    }

    public void rawSetPower(double pwr) {
        logger.powerChange(pwr);
        winch.set(pwr);
    }

    public void atPower(double pwr) {
        if (pistonController.getPistonState(Piston.LOCK_CLIMB) || lockOverride) {
            pidLoop.disable();
            logger.clearSetpoint();
            logger.conditionChange("pid_loop", "disabled");
            rawSetPower(pwr);
        }
    }

    public void resetEncoder() {
        winch.getEncoder().setPosition(0.0);
    }

    public PIDController getPIDLoop() {
        return pidLoop;
    }

    public CANSparkMax getWinch() {
        return winch;
    }

    public boolean getClimbLimitSwitch() {
        //negates returned value as limit switch is wired backwards
        return !climbLimitSwitch.get();
    }

    @Override
    protected void initDefaultCommand() {

    }
}