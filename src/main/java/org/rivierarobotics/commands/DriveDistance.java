package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    private double calcDistance;
    private double calcCurrentDistance;

    public DriveDistance(@Provided DriveTrain dt, double distance) {
        this.dt = dt;
        requires(dt);
        this.distance = distance;
    }

    @Override
    protected void initialize() {
        startDistance = currentDistance = dt.getDistance();
        calcDistance = startDistance - distance;
        dt.setDistance(calcDistance, calcDistance);
    }

    @Override
    protected void execute() {
        currentDistance = dt.getDistance();
        calcCurrentDistance = Math.abs(currentDistance - startDistance);
    }


    @Override
    protected boolean isFinished() {
        return Math.abs(calcCurrentDistance) >= Math.abs(distance);
    }
}
