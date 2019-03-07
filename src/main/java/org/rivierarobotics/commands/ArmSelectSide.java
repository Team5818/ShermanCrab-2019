package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.subsystems.ArmController;

@GenerateCreator
public class ArmSelectSide extends InstantCommand {
    private ArmController arm;
    private boolean front;

    public ArmSelectSide(@Provided ArmController arm, boolean front) {
        this.arm = arm;
        this.front = front;
        requires(arm);
    }

    @Override
    protected void execute() {
        ArmController.FRONT = front;
    }
}
