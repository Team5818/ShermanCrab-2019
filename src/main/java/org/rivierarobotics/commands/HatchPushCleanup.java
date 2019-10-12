package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;
import org.rivierarobotics.subsystems.Piston;

import javax.inject.Inject;

public class HatchPushCleanup extends CommandGroup {
    @Inject
    public HatchPushCleanup(PistonCommands piston) {
        addSequential(new TimedCommand(1.0));
        addSequential(piston.retract(Piston.PUSH));
    }
}
