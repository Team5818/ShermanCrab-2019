package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.rivierarobotics.inject.Input;
import org.rivierarobotics.subsystems.HoodController;

import javax.inject.Inject;

public class HoodControl extends Command {
    /* NEED TO CHANGE VALUES LATER FOR SAFETY DEADBAND, MANUALLY TEST*/
    private double HOOD_DEADBAND = 0.1;
    private double HOOD_MAX_EXT = 90;
    private double HOOD_MIN_EXT = -90;
    private HoodController hood;
    private Joystick hoodRotateJoy;

    @Inject
    public HoodControl(HoodController hood, @Input(Input.Position.CODRIVER_LEFT) Joystick hoodRotateJoy) {
        this.hood = hood;
        this.hoodRotateJoy = hoodRotateJoy;
        requires(hood);
    }

    @Override
    protected void execute() {
        double armJoyY = hoodRotateJoy.getY();
        if(armJoyY < -HOOD_DEADBAND && hood.getDistance() > HOOD_MIN_EXT) {
            hood.setRotatePower(armJoyY);
        } else if(armJoyY > HOOD_DEADBAND && hood.getDistance() < HOOD_MAX_EXT){
            hood.setRotatePower(armJoyY);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
