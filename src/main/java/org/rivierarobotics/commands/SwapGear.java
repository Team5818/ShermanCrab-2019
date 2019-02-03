package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.subsystems.Gear;
import org.rivierarobotics.subsystems.Shifter;

@GenerateCreator
public class SwapGear extends InstantCommand {
    private final Shifter shifter;

    public SwapGear(@Provided Shifter shifter) {
        this.shifter = shifter;
        requires(shifter);
    }

    @Override
    protected void execute() {
        /*commented out until geared motors are introduced*/
        //shifter.swapGear();
    }
}
