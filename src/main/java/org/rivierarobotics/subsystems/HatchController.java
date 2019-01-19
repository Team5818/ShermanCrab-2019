package org.rivierarobotics.subsystems;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

@Singleton
public class HatchController extends Subsystem {
    private final Solenoid piston;

    @Inject
    public HatchController() {
        piston = new Solenoid(0);
    }

    public void extendPiston() {
        piston.set(true);
    }

    public void retractPiston() {
        piston.set(false);
    }

    @Override
    protected void initDefaultCommand() {

    }

}
