package org.rivierarobotics.commands;

import javax.inject.Inject;

public class HoodCommands {
    private final HoodSpinCreator hoodSpinCreator;

    @Inject
    public HoodCommands(HoodSpinCreator hoodSpinCreator) {
        this.hoodSpinCreator = hoodSpinCreator;
    }

    public final HoodSpin spin(double power) {
        return hoodSpinCreator.create(power);
    }
}
