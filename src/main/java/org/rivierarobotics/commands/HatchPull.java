package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;
import org.rivierarobotics.subsystems.Piston;

import javax.inject.Inject;

public class HatchPull extends CommandGroup {

    @Inject
    public HatchPull(ExtendBoth extendBoth, RetractPistonCreator retractPistons) {
        addSequential(extendBoth);
        addSequential(new TimedCommand(0.25));
        addSequential(retractPistons.create(Piston.UPPER));
        addSequential(new TimedCommand(0.25));
        addSequential(retractPistons.create(Piston.LOWER));
    }
}
