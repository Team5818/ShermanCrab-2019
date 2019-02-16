package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.Command;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.subsystems.DriveTrain;

@GenerateCreator
public class RotateEncoder extends Command {
    private DriveTrain driveTrain;
    private double currentDistance;

    public RotateEncoder(@Provided DriveTrain dt) {
        this.driveTrain = dt;
    }

    @Override
    protected void execute() {

    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
