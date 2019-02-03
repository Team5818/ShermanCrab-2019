package org.rivierarobotics.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Shifter extends Subsystem {
     /*Working code, just commented out until shifting gears are introduced */
    /*private final Solenoid shifter;

    @Inject
    public Shifter() {
        shifter = new Solenoid(4);
        shifter.set(false);
    }

    public void setGear(Gear gear) {
        if(gear == Gear.HIGH) {
            shifter.set(true);
        } else if(gear == Gear.LOW) {
            shifter.set(false);
        } else {
            throw new IllegalArgumentException("Invalid gear value " + gear);
        }
    }

    public void swapGear() {
        shifter.set(!shifter.get());
    }*/

    @Inject
    public Shifter() {

    }

    @Override
    protected void initDefaultCommand() {

    }
}
