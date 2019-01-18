package org.rivierarobotics.commands;

import javax.inject.Inject;

import org.rivierarobotics.inject.Input;
import org.rivierarobotics.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class DriveControl extends Command {
    private DriveTrain driveTrain;
    private Joystick throttle;
    private Joystick turning;

    @Inject
    public DriveControl(DriveTrain dt, @Input(Input.Position.DRIVER_LEFT) Joystick left,
            @Input(Input.Position.DRIVER_RIGHT) Joystick right) {
        this.driveTrain = dt;
        this.throttle = left;
        this.turning = right;
    }

    @Override
    protected void execute() {
        double y = throttle.getY();
        double x = turning.getX();
        setArcade(x, y);
    }

    public void setArcade(double rotate, double power) {
        double max = Math.max(Math.abs(rotate), Math.abs(power));
        double diff = power - rotate;
        double sum = power + rotate;

        double left;
        double right;

        if (power > 0) {
            if (rotate > 0) {
                left = max;
                right = diff;
            } else {
                left = sum;
                right = max;
            }
        } else {
            if (rotate > 0) {
                left = sum;
                right = -max;
            } else {
                right = diff;
                left = -max;
            }
        }
        driveTrain.setPower(left, right);
    }

    public void setZero() {
        driveTrain.stop();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
