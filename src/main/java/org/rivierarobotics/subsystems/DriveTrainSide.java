package org.rivierarobotics.subsystems;

import org.rivierarobotics.constants.RobotMap;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class DriveTrainSide {
	private WPI_TalonSRX motor1;
	private WPI_TalonSRX motor2;
	
	public DriveTrainSide(boolean isLeft) {
		if(isLeft) {
			motor1 = new WPI_TalonSRX(RobotMap.LEFT_DRIVETRAIN_TALON_1);
			motor2 = new WPI_TalonSRX(RobotMap.LEFT_DRIVETRAIN_TALON_2);
		} else {
			motor1 = new WPI_TalonSRX(RobotMap.RIGHT_DRIVETRAIN_TALON_1);
			motor2 = new WPI_TalonSRX(RobotMap.RIGHT_DRIVETRAIN_TALON_2);
		}
		motor1.setInverted(!isLeft);
		motor2.setInverted(!isLeft);
	}
	

	public void setPower(double pwr) {
		motor1.set(-pwr);
		motor2.set(-pwr);
	}
}
