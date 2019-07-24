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
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.rivierarobotics.util.AbstractPIDSource;
import org.rivierarobotics.util.Logging;
import org.rivierarobotics.util.MathUtil;
import org.rivierarobotics.util.MechLogger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WinchController extends Subsystem {
    private final CANSparkMax winch;
    private final MechLogger logger;
    private final PIDController pidLoop;

    private static final double P = 0.00025;
    private static final double I = 0;
    private static final double D = 0;
    private static final double F = 0;
    private static final double MAX_PID = 0.3;

    @Inject
    public WinchController(int ch) {
        logger = Logging.getLogger(getClass());
        winch = new CANSparkMax(ch, CANSparkMaxLowLevel.MotorType.kBrushless);
        pidLoop = new PIDController(P, I, D, F, new AbstractPIDSource(this::getDistance), this::rawSetPower, 0.01);

        pidLoop.setOutputRange(-MAX_PID, MAX_PID);
        winch.setInverted(true);
        winch.setIdleMode(CANSparkMax.IdleMode.kBrake);
        logger.conditionChange("neutral_mode", "brake");
    }

    public int getDistance() {
        return (int)(winch.getEncoder().getPosition());
    }

    public void setPosition(double position) {
        pidLoop.setSetpoint(position);
        pidLoop.enable();
    }

    public void rawSetPower(double pwr) {
        logger.powerChange(pwr);
        winch.set(pwr);
    }

    public void setPower(double pwr) {
        if(pwr != 0) {
            pidLoop.disable();
            rawSetPower(pwr);
        }
    }

    @Override
    protected void initDefaultCommand() {

    }
}