package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;
import org.rivierarobotics.subsystems.Piston;

import javax.inject.Inject;

import static org.rivierarobotics.commands.CommandGroups.inParallel;

public class HatchPull extends CommandGroup {

    @Inject
    public HatchPull(PistonCommands piston) {
        addSequential(inParallel(piston.extend(Piston.GRAB_LOWER), piston.extend(Piston.GRAB_UPPER)));
        addSequential(new TimedCommand(0.25));
        addSequential(piston.retract(Piston.GRAB_UPPER));
        addSequential(new TimedCommand(0.25));
        addSequential(piston.retract(Piston.GRAB_LOWER));
    }
}
