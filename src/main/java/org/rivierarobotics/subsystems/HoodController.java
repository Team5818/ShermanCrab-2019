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

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import org.rivierarobotics.commands.HoodControl;
import org.rivierarobotics.util.MathUtil;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class HoodController extends Subsystem {
    private Provider<HoodControl> command;
    private final CANSparkMax hood;
    private final ArmController armController;
    private CANPIDController pidLoop;

    public static HoodPosition CURRENT_HOOD_POSITION = HoodPosition.RESTING_ARM_ZERO;
    public static boolean HOOD_FRONT = true;
    public static final double ANGLE_SCALE = 4096 / 360.0;
    private final double gravityConstant = 0.2;
    private final double maxPID = 0.75;

    private static final NetworkTableEntry setpointAngle;
    private static final NetworkTableEntry pwrEntry;
    private static final NetworkTableEntry gravOffset;
    private static final NetworkTableEntry realAngle;


    private final double kP = 0.001;
    private final double kI = 0;
    private final double kD = 0.025;
    private final double kF = 0.005;

    private static SimpleWidget ezWidget(String name, Object def) {
        return Shuffleboard.getTab("Hood Controller").add(name, def);
    }

    static {
        setpointAngle = ezWidget("Setpoint Angle", 0).getEntry();
        pwrEntry = ezWidget("Power", 0).getEntry();
        gravOffset = ezWidget("Gravity Offset", 0).getEntry();
        realAngle = ezWidget("Real Angle", 0).getEntry();
    }

    @Inject
    public HoodController(ArmController armController, Provider<HoodControl> command, int id) {
        this.armController = armController;
        this.command = command;

        hood = new CANSparkMax(id, CANSparkMaxLowLevel.MotorType.kBrushless);
        pidLoop = hood.getPIDController();

        pidLoop.setP(kP);
        pidLoop.setI(kI);
        pidLoop.setD(kD);
        pidLoop.setFF(kF);

        pidLoop.setOutputRange(-maxPID, maxPID);
    }

    public void setAngle(double angle) {
        setpointAngle.setDouble(angle);
        pidLoop.setReference(angle, ControlType.kPosition);
    }

    public double getAngle() {
        return hood.getEncoder().getPosition();
    }

    public double getDegrees() {
        return getAngle() / ANGLE_SCALE % 360;
    }

    public void setPower(double pwr) {
        pwr = MathUtil.limit(pwr, maxPID);
        if (pwr != 0) {
            hood.setIdleMode(CANSparkMax.IdleMode.kCoast);
        } else {
            hood.setIdleMode(CANSparkMax.IdleMode.kBrake);
        }

        pwr += getGravOffset();
        hood.set(pwr);
        pwrEntry.setDouble(hood.get());
    }

    private double getGravOffset() {
        double realAngle = this.getDegrees() + armController.getDegrees();
        realAngle = (realAngle % 360) + (realAngle < 0 ? 360 : 0);
        double gravOffset = Math.sin(Math.toRadians(realAngle)) * gravityConstant;
        HoodController.gravOffset.setDouble(gravOffset);
        HoodController.realAngle.setDouble(realAngle);
        return gravOffset;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(command.get());
    }
}