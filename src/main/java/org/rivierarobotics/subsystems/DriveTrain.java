package org.rivierarobotics.subsystems;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.rivierarobotics.commands.DriveControl;
import org.rivierarobotics.inject.Sided;

import edu.wpi.first.wpilibj.command.Subsystem;

@Singleton
public class DriveTrain extends Subsystem {
    private Provider<DriveControl> command;
    private PigeonIMU gyro = new PigeonIMU(0);
    private DriveTrainSide left;
    private DriveTrainSide right;

    @Inject
    public DriveTrain(@Sided(Sided.Side.LEFT) DriveTrainSide left,
                      @Sided(Sided.Side.RIGHT) DriveTrainSide right,
                      Provider<DriveControl> command) {
        this.left = left;
        this.right = right;
        this.command = command;
        resetGyroYaw();
    }

    public void stop() {
        this.setPower(0, 0);
    }

    public void setPower(double l, double r) {
        left.setPower(l);
        right.setPower(r);
    }

    public void setVelocity(double l, double r) {
        left.setVelocity(l);
        right.setVelocity(r);
    }

    public DriveTrainSide getLeft() {
        return left;
    }

    public DriveTrainSide getRight() {
        return right;
    }

    public void setDistance(double l, double r) {
        left.setDistance(l);
        right.setDistance(r);
    }

    public double getDistance() {
        return (left.getDistance() + right.getDistance()) / 2;
    }

    private void resetGyroYaw() {
        gyro.setYaw(0, 10);
    }

    private double[] getYPR() {
        double[] ypr = {0,0,0};
        gyro.getYawPitchRoll(ypr);
        return ypr;
    }

    public double getDegrees() {
        return getYPR()[0];
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(command.get());
    }
}
