package org.rivierarobotics.commands;

import org.rivierarobotics.subsystems.Piston;

import javax.inject.Inject;

public class PistonCommands {
    private final ExtendPistonCreator extendPistonCreator;
    private final RetractPistonCreator retractPistonCreator;

    @Inject
    public PistonCommands(ExtendPistonCreator extendPistonCreator, RetractPistonCreator retractPistonCreator) {
        this.extendPistonCreator = extendPistonCreator;
        this.retractPistonCreator = retractPistonCreator;
    }

    public final ExtendPiston extend(Piston piston) {
        return extendPistonCreator.create(piston);
    }

    public final RetractPiston retract(Piston piston) {
        return retractPistonCreator.create(piston);
    }
 }
