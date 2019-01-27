package org.rivierarobotics.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.subsystems.DriveTrain;

@GenerateCreator
public class DriveVelocity extends Command {
	private final NetworkTableEntry velEntry = Shuffleboard.getTab("Drive Train")
			.addPersistent("Vel", 0).getEntry();
	private final NetworkTableEntry distEntry = Shuffleboard.getTab("Drive Train")
			.addPersistent("Dist", 0).getEntry();
	private DriveTrain dt;
	private double startDistance;
	private double currentDistance;

	public DriveVelocity(@Provided DriveTrain dt) {
		this.dt = dt;
		requires(dt);
	}

	@Override
	protected void initialize() {
		startDistance = currentDistance = dt.getDistance();
	}

	@Override
	protected void execute() {
		double velocity = velEntry.getDouble(0.0);
		dt.setVelocity(velocity, velocity);
		currentDistance = dt.getDistance();
	}

	@Override
	protected boolean isFinished() {
		double distance = distEntry.getDouble(0.0);
		return currentDistance >= distance + startDistance;
	}
}
