package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.rivierarobotics.subsystems.Piston;

import javax.inject.Inject;

public class RetractBoth extends CommandGroup {

    @Inject
    public RetractBoth(RetractPistonCreator retractPistons) {
        addParallel(retractPistons.create(Piston.LOWER));
        addParallel(retractPistons.create(Piston.UPPER));
    }
}
