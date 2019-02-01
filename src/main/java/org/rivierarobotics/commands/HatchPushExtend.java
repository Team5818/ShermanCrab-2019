package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;
import org.rivierarobotics.subsystems.Piston;

import javax.inject.Inject;

import static org.rivierarobotics.commands.CommandGroups.inOrder;

public class HatchPushExtend extends CommandGroup {

    @Inject
    public HatchPushExtend(PistonCommands piston) {
        addSequential(inOrder(piston.extend(Piston.PUSH_LOWER), piston.extend(Piston.PUSH_UPPER)));
        addSequential(new TimedCommand(0.25));
        addSequential(inOrder(piston.extend(Piston.CLAMP_RIGHT), piston.extend(Piston.CLAMP_LEFT)));
        addSequential(new TimedCommand(0.25));
        addSequential(inOrder(piston.retract(Piston.CLAMP_RIGHT),
                piston.retract(Piston.CLAMP_LEFT),
                piston.retract(Piston.PUSH_UPPER),
                piston.retract(Piston.PUSH_LOWER)));
    }
}
