package org.rivierarobotics.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

public class ArmMotorGroup {
    private WPI_TalonSRX talonMaster;
    private CANSparkMax sparkSlaveOne;
    private CANSparkMax sparkSlaveTwo;

    public ArmMotorGroup(int master, int slaveOne, int slaveTwo) {
        talonMaster = new WPI_TalonSRX(master);
        sparkSlaveOne = new CANSparkMax(slaveOne, CANSparkMaxLowLevel.MotorType.kBrushless);
        sparkSlaveOne = new CANSparkMax(slaveTwo, CANSparkMaxLowLevel.MotorType.kBrushless);

        sparkSlaveOne.follow(CANSparkMax.ExternalFollower.kFollowerPhoenix, master, true);
        sparkSlaveTwo.follow(CANSparkMax.ExternalFollower.kFollowerPhoenix, master, true);
    }

    public void setAngle(int angle) {
        talonMaster.set(ControlMode.MotionMagic, angle);
    }

    public int getAngle() {
        return (talonMaster.getSensorCollection().getQuadraturePosition());
    }

    public void setPower(double pwr) {
        talonMaster.set(pwr);
    }
}
