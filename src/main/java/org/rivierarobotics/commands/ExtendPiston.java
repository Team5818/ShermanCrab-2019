package org.rivierarobotics.commands;

import javax.inject.Inject;

import org.rivierarobotics.subsystems.HatchController;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ExtendPiston extends InstantCommand {
    private final HatchController hc;

    @Inject
    public ExtendPiston(HatchController hc) {
        this.hc = hc;
    }

    @Override
    protected void execute() {
        hc.extendPiston();
    }
}
