package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.subsystems.ArmController;

@GenerateCreator
public class ArmDisablePID extends InstantCommand {
    private ArmController arm;

    public ArmDisablePID(@Provided ArmController arm) {
        this.arm = arm;
        requires(arm);
    }

    @Override
    protected void execute() {
        arm.getPidLoop().disable();
    }
}
