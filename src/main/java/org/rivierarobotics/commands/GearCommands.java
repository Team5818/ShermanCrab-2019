package org.rivierarobotics.commands;

import org.rivierarobotics.subsystems.Gear;

import javax.inject.Inject;

public class GearCommands {
    private final ShiftGearCreator shiftGearCreator;
    private final SwapGearCreator swapGearCreator;

    @Inject
    public GearCommands(ShiftGearCreator shiftGearCreator,
                         SwapGearCreator swapGearCreator) {
        this.shiftGearCreator = shiftGearCreator;
        this.swapGearCreator = swapGearCreator;
    }

    public ShiftGear shift(Gear gear) {
        return shiftGearCreator.create(gear);
    }

    public SwapGear swap() {
        return swapGearCreator.create();
    }
}
