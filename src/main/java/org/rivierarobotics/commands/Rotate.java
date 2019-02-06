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
    private final double TURN_DIAMETER = 30;

    public Rotate(@Provided DriveTrain dt, double degrees) {
        degreesToRotate = degrees;
        this.dt = dt;
        requires(dt);
    }

    @Override
    protected void initialize() {
        startDegrees = currentDegrees = dt.getDegrees();
        distanceToRotate = ((Math.PI * TURN_DIAMETER) / (360 / degreesToRotate));
        SmartDashboard.putNumber("distance to rotate", distanceToRotate);
        dt.setDistance(-Math.abs(distanceToRotate), Math.abs(distanceToRotate));
        dt.resetGyroYaw();
    }

    @Override
    protected void execute() {
        currentDegrees = dt.getDegrees();
        SmartDashboard.putNumber("current degrees exec", currentDegrees);
    }

    @Override
    protected boolean isFinished() {
        SmartDashboard.putNumber("current degrees final", currentDegrees);
        return currentDegrees - startDegrees >= degreesToRotate;
    }
}
