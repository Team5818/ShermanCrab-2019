package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;
import org.rivierarobotics.subsystems.Piston;

import javax.inject.Inject;

import static org.rivierarobotics.commands.CommandGroups.inParallel;

public class HatchPushExtend extends CommandGroup {

    @Inject
    public HatchPushExtend(PistonCommands piston) {
        addSequential(inParallel(piston.extend(Piston.CLAMP_LEFT), piston.extend(Piston.CLAMP_RIGHT)));
        addSequential(new TimedCommand(0.25));
        addSequential(inParallel(piston.extend(Piston.PUSH_LOWER), piston.extend(Piston.PUSH_UPPER)));
    }
}
