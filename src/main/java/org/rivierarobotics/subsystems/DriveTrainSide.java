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
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.PIDController;
import org.rivierarobotics.util.AbstractPIDSource;
import org.rivierarobotics.util.Logging;
import org.rivierarobotics.util.MechLogger;

public class DriveTrainSide {
    private final MechLogger logger;
    private final double INCHES_TO_TICKS = 0;
    private final double P = 0, I = 0, D = 0, F = 0;
    private WPI_TalonSRX talonMaster;
    private CANSparkMax sparkSlaveOne;
    private CANSparkMax sparkSlaveTwo;
    private PIDController pidLoop;

    public DriveTrainSide(int master, int slaveOne, int slaveTwo, boolean invert) {
        this.logger = Logging.getLogger(getClass(), invert ? "left" : "right");
        talonMaster = new WPI_TalonSRX(master);
        sparkSlaveOne = new CANSparkMax(slaveOne, CANSparkMaxLowLevel.MotorType.kBrushless);
        sparkSlaveTwo = new CANSparkMax(slaveTwo, CANSparkMaxLowLevel.MotorType.kBrushless);

        talonMaster.setInverted(invert);

        sparkSlaveOne.follow(CANSparkMax.ExternalFollower.kFollowerPhoenix, master, true);
        sparkSlaveTwo.follow(CANSparkMax.ExternalFollower.kFollowerPhoenix, master, true);

        talonMaster.setNeutralMode(NeutralMode.Coast);
        sparkSlaveOne.setIdleMode(CANSparkMax.IdleMode.kCoast);
        sparkSlaveTwo.setIdleMode(CANSparkMax.IdleMode.kCoast);

        pidLoop = new PIDController(P, I, D, F, new AbstractPIDSource(this::getTicks), this::rawSetPower);

        logger.conditionChange("neutral_mode", "brake");
        NeutralIdleMode.BRAKE.applyTo(talonMaster, sparkSlaveOne, sparkSlaveTwo);
    }

    public double getDistance() {
        return getTicks() / INCHES_TO_TICKS;
    }

    public void setDistance(double inches) {
        double ticks = inches * INCHES_TO_TICKS;
        double newSetpoint = getTicks() + ticks;
        logger.setpointChange(newSetpoint);
        pidLoop.setSetpoint(newSetpoint);
        logger.conditionChange("pid_loop", "enabled");
        pidLoop.enable();
    }

    public int getTicks() {
        return talonMaster.getSensorCollection().getQuadraturePosition();
    }

    public void setPower(double pwr) {
        if (pwr != 0 && pidLoop.isEnabled()) {
            logger.conditionChange("pid_loop", "disabled");
            pidLoop.disable();
            logger.clearSetpoint();
        }

        if (!pidLoop.isEnabled()) {
            rawSetPower(pwr);
        }
    }

    private void rawSetPower(double pwr) {
        logger.powerChange(pwr);
        talonMaster.set(pwr);
    }

    public void setMaxCurrent(int maxCurrent) {
        logger.conditionChange("max_current", maxCurrent);
        talonMaster.configContinuousCurrentLimit(maxCurrent);
        sparkSlaveOne.setSmartCurrentLimit(maxCurrent);
        sparkSlaveTwo.setSmartCurrentLimit(maxCurrent);
        logger.conditionChange("current_limit", "enabled");
        talonMaster.enableCurrentLimit(true);
    }

    public WPI_TalonSRX getTalon() {
        return talonMaster;
    }
}
