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

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import org.rivierarobotics.commands.HoodControl;
import org.rivierarobotics.util.Logging;
import org.rivierarobotics.util.MathUtil;
import org.rivierarobotics.util.MechLogger;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class HoodController extends DefaultSubsystem<HoodControl> {
    public static final double ANGLE_SCALE = 4096 / 360;
    private static final NetworkTableEntry SETPOINT_ANGLE;
    private static final NetworkTableEntry PWR;
    private static final NetworkTableEntry GRAV_OFFSET;
    private static final NetworkTableEntry REAL_ANGLE;
    public static HoodPosition CURRENT_HOOD_POSITION;
    public static boolean HOOD_FRONT = true;

    static {
        SETPOINT_ANGLE = ezWidget("Setpoint Angle", 0).getEntry();
        PWR = ezWidget("Power", 0).getEntry();
        GRAV_OFFSET = ezWidget("Gravity Offset", 0).getEntry();
        REAL_ANGLE = ezWidget("Real Angle", 0).getEntry();
    }

    private final CANSparkMax driveSpark;
    private final WPI_TalonSRX encoderTalon;
    private final ArmController armController;
    private final PIDController pidLoop;
    private final MechLogger logger;
    private static final double P = 0.00025;
    private static final double I = 0.0;
    private static final double D = 0.0;
    private static final double F = 0.0;
    private static final double GRAVITY_CONSTANT = 0.02;
    private static final double MAX_PID = 0.2;
    private static final double MAX_JS = 0.2;
    private boolean isPidEnabled = false;

    @Inject
    public HoodController(ArmController armController, Provider<HoodControl> command, int drive, int encoder) {
        super(command);
        this.driveSpark = new CANSparkMax(drive, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.encoderTalon = new WPI_TalonSRX(encoder);
        this.armController = armController;
        this.logger = Logging.getLogger(getClass());

        logger.conditionChange("neutral_mode", "brake");
        driveSpark.setIdleMode(CANSparkMax.IdleMode.kBrake);
        encoderTalon.setSensorPhase(true);
        pidLoop = new PIDController(P, I, D);

        pidLoop.enableContinuousInput(0, 4096);
    }

    private static SimpleWidget ezWidget(String name, Object def) {
        return Shuffleboard.getTab("Hood Controller").add(name, def);
    }

    public int getAngle() {
        return encoderTalon.getSensorCollection().getQuadraturePosition();
    }

    public void setAngle(double angle) {
        pidLoop.setSetpoint(angle);
        logger.setpointChange(angle);
        SETPOINT_ANGLE.setDouble(angle);
        isPidEnabled = true;
        logger.conditionChange("pid_loop", "enabled");
    }

    public double getDegrees() {
        return getAngle() / ANGLE_SCALE % 360;
    }

    public void setPower(double pwr) {
        if (pwr != 0 && isPidEnabled) {
            isPidEnabled = false;
            logger.clearSetpoint();
            logger.conditionChange("pid_loop", "disabled");
            logger.conditionChange("neutral_mode", "coast");
            driveSpark.setIdleMode(CANSparkMax.IdleMode.kCoast);
        } else if (pwr == 0) {
            logger.conditionChange("neutral_mode", "brake");
            driveSpark.setIdleMode(CANSparkMax.IdleMode.kBrake);
        }

        if (!isPidEnabled) {
            pwr += getGravOffset();
            rawSetPower(MathUtil.limit(pwr, MAX_JS));
        }
    }

    private void rawSetPower(double pwr) {
        PWR.setDouble(getDegrees());
        logger.powerChange(pwr);
        driveSpark.set(pwr);
    }

    private double getGravOffset() {
        double realAngle = this.getDegrees() + armController.getDegrees();
        realAngle = (realAngle % 360) + (realAngle < 0 ? 360 : 0);
        double gravOffset = Math.sin(Math.toRadians(realAngle)) * GRAVITY_CONSTANT;
        GRAV_OFFSET.setDouble(gravOffset);
        REAL_ANGLE.setDouble(realAngle);
        return gravOffset;
    }

    public void resetQuadratureEncoder() {
        encoderTalon.getSensorCollection().setQuadraturePosition(0, 0);
    }

    public void disablePID() {
        isPidEnabled = false;
    }

    public boolean isPidEnabled() {
        return isPidEnabled;
    }

    public PIDController getPIDLoop() {
        return pidLoop;
    }

    public CANSparkMax getDriveSpark() {
        return driveSpark;
    }

    public WPI_TalonSRX getEncoderTalon() {
        return encoderTalon;
    }
}
