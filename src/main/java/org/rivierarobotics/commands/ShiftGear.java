package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.subsystems.Gear;
import org.rivierarobotics.subsystems.Shifter;

@GenerateCreator
public class ShiftGear extends InstantCommand {
    private final Shifter shifter;
    private final Gear gear;

    public ShiftGear(@Provided Shifter shifter, Gear gear) {
        this.shifter = shifter;
        this.gear = gear;
        requires(shifter);
    }

    @Override
    protected void execute() {
        /*commented out until geared motors are introduced*/
       // shifter.setGear(gear);
    }
}
