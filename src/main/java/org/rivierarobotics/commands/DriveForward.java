package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.rivierarobotics.subsystems.DriveTrain;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import javax.inject.Inject;

@GenerateCreator
public class DriveForward extends InstantCommand {
	private DriveTrain driveTrain;
	private double distance;
	private final double power;
	private double startPosition;
	private double currentPosition;

	@Inject
	public DriveForward(@Provided DriveTrain dt, double distance, double power) {
		this.driveTrain = dt;
		this.distance = distance;
		this.power = power;
		requires(dt);
	}

	@Override
	protected void initialize() {
		startPosition = driveTrain.getAverageDistance();

	}

	@Override
	protected void execute() {
		driveTrain.setPower(power, power);
		currentPosition = driveTrain.getAverageDistance();
	}
	@Override
	protected void end() {
		driveTrain.setPower(0, 0);
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(currentPosition - startPosition) >= distance;
	}
}
