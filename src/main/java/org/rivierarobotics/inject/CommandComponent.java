package org.rivierarobotics.inject;

import org.rivierarobotics.commands.DriveCommands;
import org.rivierarobotics.commands.GearCommands;
import org.rivierarobotics.commands.HatchCommands;
import org.rivierarobotics.commands.PistonCommands;

import dagger.Module;
import dagger.Subcomponent;

@Subcomponent
public abstract class CommandComponent {
    public abstract DriveCommands drive();
    public abstract PistonCommands piston();
    public abstract HatchCommands hatch();
    public abstract GearCommands gear();

    @Module(subcomponents = CommandComponent.class)
    public interface CCModule {
    }

    @Subcomponent.Builder
    public interface Builder {
        CommandComponent build();
    }
}
