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
        piston2 = new Solenoid(0);
    }

    public void extendPiston() {
        piston1.set(true);
        piston2.set(true);
    }

    public void retractPiston() {
        piston1.set(false);
        piston2.set(false);
    }

    @Override
    protected void initDefaultCommand() {

    }

}
