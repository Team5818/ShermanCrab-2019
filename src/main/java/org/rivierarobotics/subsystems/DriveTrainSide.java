package org.rivierarobotics.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import org.rivierarobotics.robot.UtilMap;

public class DriveTrainSide {
	private WPI_TalonSRX motorEnc;
	private WPI_TalonSRX motorZed;

	public DriveTrainSide(int enc, int zed, boolean invert) {
		motorEnc = new WPI_TalonSRX(enc);
		motorZed = new WPI_TalonSRX(zed);
		motorEnc.setInverted(!invert);
		motorZed.setInverted(!invert);
	}

	public void setPower(double pwr) {
		motorEnc.set(-pwr);
		motorZed.set(-pwr);
	}

	public void setDistance(double inches) {
		motorEnc.getSensorCollection().setQuadraturePosition(
				(int) (inches / UtilMap.COUNTS_TO_INCHES_FACTOR), 10);
	}

	public double getDistance() {
		return motorEnc.getSensorCollection().getQuadraturePosition() * UtilMap.COUNTS_TO_INCHES_FACTOR;
	}
}
