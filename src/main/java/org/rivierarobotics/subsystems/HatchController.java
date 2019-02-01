package org.rivierarobotics.subsystems;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

@Singleton
public class HatchController extends Subsystem {
    private final Solenoid clampPistonRight;
    private final Solenoid clampPistonLeft;
    private final Solenoid pushPistonLower;
    private final Solenoid pushPistonUpper;

    @Inject
    public HatchController() {
        pushPistonLower = new Solenoid(0);
        pushPistonUpper = new Solenoid(1);
        clampPistonLeft = new Solenoid(2);
        clampPistonRight = new Solenoid(3);

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

    @Override
    protected void initDefaultCommand() {

    }

}
