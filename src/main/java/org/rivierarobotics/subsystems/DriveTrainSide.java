package org.rivierarobotics.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;

public class DriveTrainSide {
    private static final double TICKS_TO_INCHES;
    private static final double P;
    private static final double I;
    private static final double D;
    private static final double F;
    private static final int SLOT_IDX = 0;
    private static final int PID_LOOP_IDX = 0;
    private static final int TIMEOUT = 30;

    private static SimpleWidget ezWidget(String name, Object def) {
        return Shuffleboard.getTab("Drive Train").addPersistent(name, def);
    }

    static {
        TICKS_TO_INCHES = ezWidget("Ticks to Inches", 1).getEntry().getDouble(1);
        System.err.println("Ticks to Inches: " + TICKS_TO_INCHES);

        P = ezWidget("P", 0.2).getEntry().getDouble(0.2);
        System.err.println("P: " + P);

        I = ezWidget("I", 0.0).getEntry().getDouble(0);
        System.err.println("I: " + I);

        D = ezWidget("D", 0.0).getEntry().getDouble(0);
        System.err.println("D: " + D);

        F = ezWidget("F", 0.2).getEntry().getDouble(0.2);
        System.err.println("F: " + F);
    }

    private WPI_TalonSRX motorEnc;
    private WPI_TalonSRX motorZed;

    public DriveTrainSide(int enc, int zed, boolean invert) {
        motorEnc = new WPI_TalonSRX(enc);
        motorZed = new WPI_TalonSRX(zed);
        motorZed.follow(motorEnc);
        motorEnc.setInverted(invert);
        motorZed.setInverted(InvertType.FollowMaster);

        /* Factory default hardware to prevent unexpected behavior */
        motorEnc.configFactoryDefault();

        /* Configure Sensor Source for Primary PID */
        motorEnc.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PID_LOOP_IDX, TIMEOUT);
        /*
         * Configure Talon SRX Output and Sensor direction accordingly Invert Motor to
         * have green LEDs when driving Talon Forward / Requesting Positive Output Phase
         * sensor to have positive increment when driving Talon Forward (Green LED)
         */
        motorEnc.setSensorPhase(true);

        /* Set relevant frame periods to be at least as fast as periodic rate */
        motorEnc.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, TIMEOUT);

        /* Set the peak and nominal outputs */
        motorEnc.configNominalOutputForward(0, TIMEOUT);
        motorEnc.configNominalOutputReverse(0, TIMEOUT);
        motorEnc.configPeakOutputForward(1, TIMEOUT);
        motorEnc.configPeakOutputReverse(-1, TIMEOUT);

        /* Set Motion Magic gains in slot0 - see documentation */
        motorEnc.selectProfileSlot(SLOT_IDX, PID_LOOP_IDX);
        motorEnc.config_kF(SLOT_IDX, F, TIMEOUT);
        motorEnc.config_kP(SLOT_IDX, P, TIMEOUT);
        motorEnc.config_kI(SLOT_IDX, I, TIMEOUT);
        motorEnc.config_kD(SLOT_IDX, D, TIMEOUT);
    }

    public double getDistance() {
        int ticks = motorEnc.getSensorCollection().getQuadraturePosition();
        return ticks * TICKS_TO_INCHES;
    }

    public void setVelocity(double vel) {
        motorEnc.set(ControlMode.Velocity, vel);
    }

    public void setPower(double pwr) {
        motorEnc.set(pwr);
    }
}
