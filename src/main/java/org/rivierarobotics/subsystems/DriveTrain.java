package org.rivierarobotics.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.rivierarobotics.commands.DriveControl;
import org.rivierarobotics.inject.Sided;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class DriveTrain extends Subsystem {
	private Provider<DriveControl> command;
	private DriveTrainSide left;
	private DriveTrainSide right;

	@Inject
	public DriveTrain(@Sided(Sided.Side.LEFT) DriveTrainSide left,
					  @Sided(Sided.Side.RIGHT) DriveTrainSide right,
					  Provider<DriveControl> command) {
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

	public void resetDistance() {
		this.left.setDistance(0);
		this.right.setDistance(0);
	}

	public double getAverageDistance() {
		double leftDistance = this.left.getDistance();
		double rightDistance = this.right.getDistance();
		return (leftDistance + rightDistance)/2;
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(command.get());
	}
}
