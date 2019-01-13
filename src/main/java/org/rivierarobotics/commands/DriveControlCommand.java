package org.rivierarobotics.commands;

import dagger.Provides;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.InstantCommand;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.subsystems.DriveTrain;

import javax.inject.Inject;


public class DriveControlCommand extends InstantCommand {
    private DriveTrain driveTrain;
    private Joystick throttle;
    private Joystick turning;

    @Inject
    public DriveControlCommand(@Provided DriveTrain dt) {
        driveTrain = dt;
        throttle = DriverInterface.JS_THROTTLE;
        turning = DriverInterface.JS_TURNING;
    }

    public void controlArcade() {
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
