package org.rivierarobotics.commands;

import org.rivierarobotics.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;

@GenerateCreator
public class DriveDistance extends Command {

    private DriveTrain dt;
    private final double distance;
    private double startDistance;
    private double currentDistance;

    public DriveDistance(@Provided DriveTrain dt, double distance) {
        this.dt = dt;
        requires(dt);
        this.distance = distance;
    }

    @Override
    protected void initialize() {
        startDistance = currentDistance = dt.getDistance();

        double absDistance = distance + startDistance;
        dt.setDistance(absDistance, absDistance);
    }

    @Override
    protected void execute() {
        currentDistance = dt.getDistance();
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(currentDistance - startDistance) >= Math.abs(distance);
    }
}
