package org.rivierarobotics.subsystems;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

@Singleton
public class HatchController extends Subsystem {
    private Solenoid clampPistonRight;
    private Solenoid clampPistonLeft;
    private Solenoid pushPistonLower;
    private Solenoid pushPistonUpper;
    private Solenoid deployPistonLeft;
    private Solenoid deployPistonRight;

    @Inject
    public HatchController() {
        pushPistonLower = new Solenoid(0);
        pushPistonUpper = new Solenoid(1);
        clampPistonLeft = new Solenoid(2);
        clampPistonRight = new Solenoid(3);
        deployPistonLeft = new Solenoid(4);
        deployPistonRight = new Solenoid(5);

        var tab = Shuffleboard.getTab("Solenoid");
        tab.add(clampPistonRight);
        tab.add(clampPistonLeft);
    }

    private Solenoid pistonFor(Piston piston) {
        if(piston == Piston.CLAMP_RIGHT) {
            return clampPistonRight;
        } else if(piston == Piston.CLAMP_LEFT) {
            return clampPistonLeft;
        } else if(piston == Piston.PUSH_LOWER) {
            return pushPistonLower;
        } else if(piston == Piston.PUSH_UPPER) {
            return pushPistonUpper;
        } else if(piston == Piston.DEPLOY_LEFT) {
            return deployPistonLeft;
        } else if(piston == Piston.DEPLOY_RIGHT) {
            return deployPistonRight;
        } else {
            throw new IllegalArgumentException("Invalid piston value " + piston);
        }
    }

    public void extendPiston(Piston piston) {
        pistonFor(piston).set(true);
    }

    public void retractPiston(Piston piston) {
        pistonFor(piston).set(false);
    }

    public boolean getPistonState(Piston piston) {
        return pistonFor(piston).get();
    }

    @Override
    protected void initDefaultCommand() {

    }

}