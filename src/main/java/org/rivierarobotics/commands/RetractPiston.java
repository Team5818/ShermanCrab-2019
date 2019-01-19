package org.rivierarobotics.commands;

import javax.inject.Inject;

import org.rivierarobotics.subsystems.HatchController;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class RetractPiston extends InstantCommand {
    private final HatchController hc;

    @Inject
    public RetractPiston(HatchController hc) {
        this.hc = hc;
        requires(hc);
    }

    @Override
    protected void execute() {
        hc.retractPiston();
    }
}
