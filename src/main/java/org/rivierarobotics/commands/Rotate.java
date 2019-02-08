package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.Command;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.subsystems.DriveTrain;
import org.rivierarobotics.subsystems.PigeonGyro;

@GenerateCreator
public class Rotate extends Command {
    private DriveTrain dt;
    private PigeonGyro gyro;
    private double degreesToRotate;
    private double changeDegrees;
    private double startDegrees;

    public Rotate(@Provided DriveTrain dt, @Provided PigeonGyro gyro, double degrees) {
        degreesToRotate = degrees;
        this.gyro = gyro;
        this.dt = dt;
        requires(dt);
        requires(gyro);
    }

    @Override
    protected void initialize() {
        startDegrees = changeDegrees = gyro.getYaw();
    }

    @Override
    protected void execute() {
        changeDegrees = gyro.getYaw() - startDegrees;
        dt.setPower(0.5,-0.5);
    }

    @Override
    protected boolean isFinished() {
        return changeDegrees >= degreesToRotate;
    }
}
