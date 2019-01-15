package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.rivierarobotics.inject.Input;
import org.rivierarobotics.subsystems.DriveTrain;
import javax.inject.Inject;


public class DriveControl extends Command {
    private DriveTrain driveTrain;
    private Joystick throttle;
    private Joystick turning;

    @Inject
    public DriveControl(DriveTrain dt,
                        @Input(Input.Position.DRIVER_LEFT) Joystick left,
                        @Input(Input.Position.DRIVER_RIGHT) Joystick right) {
        this.driveTrain = dt;
        this.throttle = left;
        this.turning = right;
    }

    public void setTank() {
        double y = throttle.getY();
        double x = turning.getX();

        double left;
        double right;
        if (y >= 0) {
            left = y+x;
            right = y-x;
        } else {
            left = y-x;
            right = y+x;
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
