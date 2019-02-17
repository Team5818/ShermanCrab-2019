package org.rivierarobotics.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class HoodMotorGroup {
    private WPI_TalonSRX rotate;
    private WPI_TalonSRX spin;

    public HoodMotorGroup(int rotate, int spin) {
        this.rotate = new WPI_TalonSRX(rotate);
        this.spin = new WPI_TalonSRX(spin);
    }

    public void setRotateAngle(int angle) {
        rotate.set(ControlMode.MotionMagic, angle);
    }

    public int getRotateAngle() {
        return rotate.getSensorCollection().getQuadraturePosition();
    }

    public void setRotatePower(double power) {
        rotate.set(power);
    }

    public void setSpinPower(double power) {
        spin.set(power);
    }
}
