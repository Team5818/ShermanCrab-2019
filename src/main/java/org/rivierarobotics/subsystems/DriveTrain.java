package org.rivierarobotics.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
	private DriveTrainSide left;
	private DriveTrainSide right;
	
	public DriveTrain() {
		left = new DriveTrainSide(true);
		right = new DriveTrainSide(false);
	}
	
	public void setTank(double l_pwr, double r_pwr) {
		left.setPower(l_pwr);
		right.setPower(r_pwr);
	}

	@Override
	protected void initDefaultCommand() {
		
	}
}
