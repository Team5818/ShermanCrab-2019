package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.rivierarobotics.subsystems.Piston;

import javax.inject.Inject;

public class ExtendBoth extends CommandGroup {

    @Inject
    public ExtendBoth(ExtendPistonCreator extendPistons) {
        addParallel(extendPistons.create(Piston.LOWER));
        addParallel(extendPistons.create(Piston.UPPER));
    }
}
