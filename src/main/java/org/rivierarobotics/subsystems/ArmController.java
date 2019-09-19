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
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.rivierarobotics.commands.ArmControl;
import org.rivierarobotics.util.AbstractPIDSource;
import org.rivierarobotics.util.Logging;
import org.rivierarobotics.util.MathUtil;
import org.rivierarobotics.util.MechLogger;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class ArmController extends Subsystem {
    private final WPI_TalonSRX arm;
    private final CANSparkMax sparkSlaveOne;
    private final CANSparkMax sparkSlaveTwo;
    private final MechLogger logger = Logging.getLogger(getClass());
    private final PistonController pistonController;
    private final double P = 0.005, I = 0.0, D = 0.0, F = 0.0;
    private final double GRAVITY_CONSTANT = -0.045;
    private final double ANGLE_SCALE = (90) / (ArmPosition.NINETY_DEGREES.ticksFront - ArmPosition.ZERO_DEGREES.ticksFront);
    private final PIDController pidLoop;
    public boolean FRONT = true;
    private Provider<ArmControl> command;

    @Inject
    public ArmController(PistonController pistonController, Provider<ArmControl> command, int master, int slaveOne, int slaveTwo) {
        arm = new WPI_TalonSRX(master);
        sparkSlaveOne = new CANSparkMax(slaveOne, CANSparkMaxLowLevel.MotorType.kBrushless);
        sparkSlaveTwo = new CANSparkMax(slaveTwo, CANSparkMaxLowLevel.MotorType.kBrushless);

        arm.setInverted(false);

        sparkSlaveOne.follow(CANSparkMax.ExternalFollower.kFollowerPhoenix, master, false);
        sparkSlaveTwo.follow(CANSparkMax.ExternalFollower.kFollowerPhoenix, master, false);

        setMode(NeutralIdleMode.COAST);

        pidLoop = new PIDController(P, I, D, F, new AbstractPIDSource(this::getAngle), this::rawSetPower, 0.01);

        this.pistonController = pistonController;
        this.command = command;
    }

    public int getAngle() {
        int angle = arm.getSensorCollection().getPulseWidthPosition();
        return (angle > 4096) ? (angle % 4096) : ((angle < -4096) ? (-(Math.abs(angle)) % 4096) : angle);
    }

    public void setAngle(double angle) {
        if (pistonController.getPistonState(Piston.DEPLOY)) {
            angle = MathUtil.limit(angle, ArmPosition.ZERO_DEGREES.ticksFront);
            //TODO is this logger setTo (and the one below it) redundant?
            logger.conditionChange("deploy_pistons", "out");
        } else {
            logger.conditionChange("deploy_pistons", "in");
        }

        setMode(NeutralIdleMode.BRAKE);
        pidLoop.setSetpoint(angle);
        logger.setpointChange(angle);
        pidLoop.enable();
        logger.conditionChange("pid_loop", "enabled");
    }

    public double getDegrees() {
        return (getAngle() - ArmPosition.ZERO_DEGREES.ticksFront) * ANGLE_SCALE;
    }

    //TODO honestly everything from here to the end of safety() is a mess, but I'm hesitant to change it as it might break something
    public void setPower(double pwr) {
        if (safety(pwr)) {
            if (pwr != 0 && pidLoop.isEnabled()) {
                disablePID();
            } else if (pwr == 0 && !pidLoop.isEnabled()) {
                setMode(NeutralIdleMode.BRAKE);
            }
        }
        if (!pidLoop.isEnabled()) {
            rawSetPower(pwr);
        }
    }

    private void rawSetPower(double pwr) {
        pwr += Math.sin(Math.toRadians(getDegrees())) * GRAVITY_CONSTANT;
        pwr = MathUtil.limit(pwr, 0.85);
        logger.powerChange(pwr);
        arm.set(pwr);
    }

    private void stop() {
        if (pidLoop.isEnabled()) {
            rawSetPower(0.0);
            disablePID();
        }
        setAngle(getAngle());
    }

    private boolean safety(double pwr) {
        if (pistonController.getPistonState(Piston.DEPLOY)
                && getAngle() >= ArmPosition.ZERO_DEGREES.ticksFront) {
            if (pwr < 0) {
                setMode(NeutralIdleMode.COAST);
                return true;
            } else {
                stop();
                return false;
            }
        } else {
            return true;
        }
    }

    private void disablePID() {
        pidLoop.disable();
        logger.clearSetpoint();
        logger.conditionChange("pid_loop", "disabled");
    }

    public void setMode(NeutralIdleMode mode) {
        mode.setTo(arm, sparkSlaveOne, sparkSlaveTwo);
        logger.conditionChange("neutral_mode", mode.name);
    }

    public PIDController getPIDLoop() {
        return pidLoop;
    }

    public WPI_TalonSRX getArm() {
        return arm;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(command.get());
    }
}