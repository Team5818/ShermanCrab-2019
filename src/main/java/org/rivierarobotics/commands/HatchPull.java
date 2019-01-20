package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;
import org.rivierarobotics.subsystems.Piston;

import javax.inject.Inject;

import static org.rivierarobotics.commands.CommandGroups.inParallel;

public class HatchPull extends CommandGroup {

    @Inject
    public HatchPull(ExtendPistonCreator extendPiston, RetractPistonCreator retractPiston) {
        addSequential(inParallel(extendPiston.create(Piston.GRAB_LOWER), extendPiston.create(Piston.GRAB_UPPER)));
        addSequential(new TimedCommand(0.25));
        addSequential(retractPiston.create(Piston.GRAB_UPPER));
        addSequential(new TimedCommand(0.25));
        addSequential(retractPiston.create(Piston.GRAB_LOWER));
    }
}
