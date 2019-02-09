package org.rivierarobotics.inject;

import org.rivierarobotics.commands.*;

import dagger.Module;
import dagger.Subcomponent;

@Subcomponent
public abstract class CommandComponent {
    public abstract DriveCommands drive();
    public abstract PistonCommands piston();
    public abstract HatchCommands hatch();
    public abstract GearCommands gear();
    public abstract HoodCommands hood();
    public abstract ArmCommands arm();

    @Module(subcomponents = CommandComponent.class)
    public interface CCModule {
    }

    @Subcomponent.Builder
    public interface Builder {
        CommandComponent build();
    }
}
