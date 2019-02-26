package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.Command;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.subsystems.ArmController;

@GenerateCreator
public class ArmSetPower extends Command {
    private double pwr;
    private ArmController arm;

    public ArmSetPower(@Provided ArmController arm, double pwr) {
        this.pwr = pwr;
        this.arm = arm;
        requires(arm);
    }

    @Override
    protected void execute() {
        arm.setPower(pwr);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
