package org.rivierarobotics.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.rivierarobotics.inject.Sided;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Shifter extends Subsystem {
    private ShifterSide left;
    private ShifterSide right;

    @Inject
    public Shifter(@Sided(Sided.Side.LEFT) ShifterSide left,
                   @Sided(Sided.Side.RIGHT) ShifterSide right) {
        this.left = left;
        this.right = right;
    }

    public void setGear(Gear gear) {
        left.setGear(gear);
        right.setGear(gear);
    }

    public void swapGear() {
        left.swapGear();
        right.swapGear();
    }

    @Override
    protected void initDefaultCommand() {

    }
}
