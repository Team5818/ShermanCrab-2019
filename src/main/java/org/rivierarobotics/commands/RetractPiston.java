package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.subsystems.HatchController;
import org.rivierarobotics.subsystems.Piston;

@GenerateCreator
public class RetractPiston extends InstantCommand {
    private final HatchController hc;
    private final Piston piston;

    public RetractPiston(@Provided HatchController hc, Piston piston) {
        this.hc = hc;
        this.piston = piston;
        requires(hc);
    }

    @Override
    protected void execute() {
        hc.retractPiston(piston);
    }
}
