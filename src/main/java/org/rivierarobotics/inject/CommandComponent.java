package org.rivierarobotics.inject;

import org.rivierarobotics.commands.*;

import dagger.Module;
import dagger.Subcomponent;
import org.rivierarobotics.subsystems.Piston;

@Subcomponent
public abstract class CommandComponent {
    public abstract DriveCommands drive();
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
