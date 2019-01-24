package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;
import org.rivierarobotics.subsystems.Piston;

import javax.inject.Inject;

import static org.rivierarobotics.commands.CommandGroups.inOrder;

public class HatchPullRetract extends CommandGroup {

    @Inject
    public HatchPullRetract(PistonCommands piston) {
        addSequential(piston.retract(Piston.GRAB_UPPER));
        addSequential(new TimedCommand(0.25));
        addSequential(piston.retract(Piston.GRAB_LOWER));
    }
}
