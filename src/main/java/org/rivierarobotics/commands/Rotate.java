package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.subsystems.DriveTrain;

@GenerateCreator
public class Rotate extends Command {
    private DriveTrain dt;
    private double degreesToRotate;
    private double currentDegrees;
    private double startDegrees;
    private double distanceToRotate;
    private final double WHEEL_DIAMETER = 4.2;

    public Rotate(@Provided DriveTrain dt, double degrees) {
        degreesToRotate = degrees;
        this.dt = dt;
        requires(dt);
    }

    @Override
    protected void initialize() {
        startDegrees = currentDegrees = dt.getDegrees();
        distanceToRotate = ((Math.PI * WHEEL_DIAMETER) / (360 / degreesToRotate));
        SmartDashboard.putNumber("distance to rotate", distanceToRotate);
        dt.setDistance(Math.abs(distanceToRotate), -Math.abs(distanceToRotate));
    }

    @Override
    protected void execute() {
        currentDegrees = dt.getDegrees();
        SmartDashboard.putNumber("current degrees", currentDegrees);
    }

    @Override
    protected boolean isFinished() {
        return currentDegrees >= startDegrees + degreesToRotate;
    }
}
