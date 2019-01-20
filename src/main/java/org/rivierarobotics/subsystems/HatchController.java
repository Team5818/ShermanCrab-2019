package org.rivierarobotics.subsystems;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

@Singleton
public class HatchController extends Subsystem {
    private final Solenoid piston1;
    private final Solenoid piston2;

    @Inject
    public HatchController() {
        piston1 = new Solenoid(0);
        piston2 = new Solenoid(1);
    }

    private Solenoid pistonFor(Piston piston) {
        if(piston == Piston.LOWER) {
            return piston1;
        } else if(piston == Piston.UPPER) {
            return piston2;
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
