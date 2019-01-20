package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class CommandGroups {
    public static Command inParallel(Command... commands) {
        CommandGroup group = new CommandGroup();
        for(Command c : commands) {
            group.addParallel(c);
        }
        return group;
    }
}
