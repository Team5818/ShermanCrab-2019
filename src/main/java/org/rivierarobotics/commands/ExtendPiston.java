package org.rivierarobotics.commands;

import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.subsystems.HatchController;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.rivierarobotics.subsystems.Piston;

@GenerateCreator
public class ExtendPiston extends InstantCommand {
    private final HatchController hc;
    private final Piston piston;

    public ExtendPiston(@Provided HatchController hc, Piston piston) {
        this.hc = hc;
        this.piston = piston;
        requires(hc);
    }

    @Override
    protected void execute() {
        hc.extendPiston(piston);
    }
}
