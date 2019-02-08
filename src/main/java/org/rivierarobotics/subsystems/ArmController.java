package org.rivierarobotics.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.rivierarobotics.commands.ArmControl;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import static org.rivierarobotics.subsystems.DriveTrainSide.INCHES_TO_TICKS;

@Singleton
public class ArmController extends Subsystem {
    private Provider<ArmControl> command;
    private WPI_TalonSRX arm;

    @Inject
    public ArmController(Provider<ArmControl> command) {
        //this.arm = new WPI_TalonSRX(20);
        this.command = command;
    }

    public void setAngle(double angle) {
        arm.set(ControlMode.MotionMagic, angle);
    }

    public double getDistance() {
        return (arm.getSensorCollection().getQuadraturePosition()) / INCHES_TO_TICKS;
    }

    public void setPower(double pwr) {
        arm.set(pwr);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(command.get());
    }
}
