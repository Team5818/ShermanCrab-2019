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

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WinchController extends Subsystem {
    private WPI_TalonSRX winch;

    @Inject
    public WinchController(int ch) {
        winch = new WPI_TalonSRX(ch);
        winch.setInverted(true);
        winch.setNeutralMode(NeutralMode.Brake);
    }

    public int getAngle() {
        return winch.getSensorCollection().getPulseWidthPosition();
    }

    public void setPosition(double position) {
        /* No PID loop needed - manual control by itself is sufficient */
    }

    public void setPower(double pwr) {
        winch.set(pwr);
    }

    public WPI_TalonSRX getWinch() {
        return winch;
    }

    @Override
    protected void initDefaultCommand() {

    }
}