package org.rivierarobotics.inject;

import org.rivierarobotics.commands.DriveForward;
import org.rivierarobotics.commands.DriveForwardCreator;
import org.rivierarobotics.commands.ExtendPiston;
import org.rivierarobotics.commands.RetractPiston;

import dagger.Module;
import dagger.Subcomponent;

@Subcomponent
public abstract class CommandComponent {
    abstract DriveForwardCreator getDriveForwardCreator();

    public final DriveForward newDriveForward(double power, double time) {
        return getDriveForwardCreator().create(power, time);
    }

    public abstract ExtendPiston newExtendPiston();

    public abstract RetractPiston newRetractPiston();

    @Module(subcomponents = CommandComponent.class)
    public interface CCModule {
    }

    @Subcomponent.Builder
    public interface Builder {
        CommandComponent build();
    }
}
