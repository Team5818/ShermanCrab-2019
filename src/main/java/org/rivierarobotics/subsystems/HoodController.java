package org.rivierarobotics.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.rivierarobotics.commands.HoodControl;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import static org.rivierarobotics.subsystems.DriveTrainSide.INCHES_TO_TICKS;

@Singleton
public class HoodController extends Subsystem {
    private Provider<HoodControl> command;
    private WPI_TalonSRX rotate;
    private WPI_TalonSRX spin;

    @Inject
    public HoodController(Provider<HoodControl> command) {
        //this.rotate = new WPI_TalonSRX(20);
        //this.spin = new WPI_TalonSRX(22);
        this.command = command;
    }

    public void setAngle(double angle) {
        rotate.set(ControlMode.MotionMagic, angle);
    }

    public double getDistance() {
        return (rotate.getSensorCollection().getQuadraturePosition()) / INCHES_TO_TICKS;
    }

    public void setRotatePower(double pwr) {
        rotate.set(pwr);
    }

    public void setSpinPower(double pwr) {
        spin.set(pwr);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(command.get());
    }
}
