package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.Command;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.subsystems.HoodController;

@GenerateCreator
public class HoodSpin extends Command {
    private double power;
    private HoodController hood;

    public HoodSpin(@Provided HoodController hood, double power) {
        this.hood = hood;
        this.power = power;
    }

    @Override
    protected void execute() {
        hood.setSpinPower(power);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
