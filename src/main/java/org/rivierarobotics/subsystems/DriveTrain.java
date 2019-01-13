package org.rivierarobotics.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.rivierarobotics.commands.DriveControlCommand;
import org.rivierarobotics.inject.Sided;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class DriveTrain extends Subsystem {
	private Provider<DriveControlCommand> command;
	private DriveTrainSide left;
	private DriveTrainSide right;

	@Inject
	public DriveTrain(@Sided(Sided.Side.LEFT) DriveTrainSide left,
					  @Sided(Sided.Side.RIGHT) DriveTrainSide right,
					  Provider<DriveControlCommand> command) {
		this.left = left;
		this.right = right;
		this.command = command;
	}


	public void stop() {
        this.setPower(0, 0);
    }

    public void setPower(double l, double r) {
	    left.setPower(l);
	    right.setPower(r);
    }

    /*public void driveDistance() {
        left.driveDistance();
        right.driveDistance();
    }*/

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(command.get());
	}
}
