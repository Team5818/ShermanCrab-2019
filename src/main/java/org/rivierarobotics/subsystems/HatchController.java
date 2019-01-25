package org.rivierarobotics.subsystems;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

@Singleton
public class HatchController extends Subsystem {
    private final Solenoid grabPistonLower;
    private final Solenoid grabPistonUpper;
    private final Solenoid pushPistonLower;
    private final Solenoid pushPistonUpper;

    @Inject
    public HatchController() {
        grabPistonLower = new Solenoid(1);
        grabPistonUpper = new Solenoid(0);
        pushPistonLower = new Solenoid(3);
        pushPistonUpper = new Solenoid(2);
        var tab = Shuffleboard.getTab("Solenoid");
        tab.add(grabPistonLower);
        tab.add(grabPistonUpper);
    }

    private Solenoid pistonFor(Piston piston) {
        if(piston == Piston.GRAB_LOWER) {
            return grabPistonLower;
        } else if(piston == Piston.GRAB_UPPER) {
            return grabPistonUpper;
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
