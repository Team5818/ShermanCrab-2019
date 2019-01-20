package org.rivierarobotics.inject;

import org.rivierarobotics.commands.*;

import dagger.Module;
import dagger.Subcomponent;
import org.rivierarobotics.subsystems.Piston;

@Subcomponent
public abstract class CommandComponent {
    abstract DriveForwardCreator getDriveForwardCreator();

    public final DriveForward newDriveForward(double power, double time) {
        return getDriveForwardCreator().create(power, time);
    }

    abstract RetractPistonCreator getRetractPistonCreator();

    public final RetractPiston newRetractPiston(Piston piston) {
        return getRetractPistonCreator().create(piston);
    }

    public abstract PistonCommands piston();
    public abstract HatchPull newHatchPull();

    @Module(subcomponents = CommandComponent.class)
    public interface CCModule {
    }

    @Subcomponent.Builder
    public interface Builder {
        CommandComponent build();
    }
}
