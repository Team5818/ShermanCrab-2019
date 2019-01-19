package org.rivierarobotics.commands;

import org.rivierarobotics.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;

@GenerateCreator
public class DriveForward extends Command {
	private final double power;
	private final double distance;
	private final DriveTrain dt;
	private double startDistance;
	private double currentDistance;

	public DriveForward(@Provided DriveTrain dt, double power, double distance) {
		this.power = power;
		this.distance = distance;
		this.dt = dt;
		requires(dt);
	}

	@Override
	protected void initialize() {
		startDistance = currentDistance = dt.getDistance();
	}

	@Override
	protected void execute() {
		dt.setPower(power, power);
		currentDistance = dt.getDistance();
	}

	@Override
	protected boolean isFinished() {
		return currentDistance >= distance + startDistance;
	}
}
