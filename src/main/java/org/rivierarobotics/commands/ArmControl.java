package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.rivierarobotics.inject.Input;
import org.rivierarobotics.subsystems.ArmController;

import javax.inject.Inject;

public class ArmControl extends Command {
    private ArmController arm;
    private Joystick armJoy;

    @Inject
    public ArmControl(ArmController arm, @Input(Input.Position.CODRIVER_LEFT)Joystick armJoy) {
        this.arm = arm;
        this.armJoy = armJoy;
        requires(arm);
    }

    @Override
    protected void initialize() {
        super.initialize();
    }

    @Override
    protected void execute() {
        super.execute();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
