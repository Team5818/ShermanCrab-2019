package org.rivierarobotics.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.command.Subsystem;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PigeonGyro extends Subsystem {
    private PigeonIMU gyro;

    @Inject
    public PigeonGyro() {
        this.gyro = new PigeonIMU(15);
    }

    private double[] getYPR() {
        double[] ypr = {0,0,0};
        gyro.getYawPitchRoll(ypr);
        return ypr;
    }

    public double getYaw() {
        return getYPR()[0];
    }

    @Override
    protected void initDefaultCommand() {

    }
}
