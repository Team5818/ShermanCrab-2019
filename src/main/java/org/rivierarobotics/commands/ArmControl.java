package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.rivierarobotics.inject.Input;
import org.rivierarobotics.subsystems.ArmController;

import javax.inject.Inject;

public class ArmControl extends Command {
    /* NEED TO CHANGE VALUES LATER FOR SAFETY DEADBAND, MANUALLY TEST*/
    private double ARM_DEADBAND = 0.1;
    private double ARM_MAX_EXT = 90;
    private double ARM_MIN_EXT = -90;
    private ArmController arm;
    private Joystick armJoy;

    @Inject
    public ArmControl(ArmController arm, @Input(Input.Position.CODRIVER_LEFT) Joystick armJoy) {
        this.arm = arm;
        this.armJoy = armJoy;
        requires(arm);
    }

    @Override
    protected void execute() {
        double armJoyY = armJoy.getY();
        if(armJoyY < -ARM_DEADBAND && arm.getDistance() > ARM_MIN_EXT) {
            arm.setPower(armJoyY);
        } else if(armJoyY > ARM_DEADBAND && arm.getDistance() < ARM_MAX_EXT){
            arm.setPower(armJoyY);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
