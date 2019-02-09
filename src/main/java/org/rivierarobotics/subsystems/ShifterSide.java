package org.rivierarobotics.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import org.rivierarobotics.commands.DriveControl;
import org.rivierarobotics.inject.Sided;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class ShifterSide {
     /*Working code, just commented out until shifting gears are introduced */
    private final Solenoid shifter;

    @Inject
    public ShifterSide(int ch) {
        shifter = new Solenoid(ch);
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
    }
}
