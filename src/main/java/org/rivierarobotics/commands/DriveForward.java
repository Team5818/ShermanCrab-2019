package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.rivierarobotics.subsystems.DriveTrain;

import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;

@GenerateCreator
public class DriveForward extends InstantCommand {
	public DriveForward(@Provided DriveTrain dt, double power, double time) {

	}
}
