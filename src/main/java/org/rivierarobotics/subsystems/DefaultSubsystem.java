package org.rivierarobotics.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import javax.inject.Provider;

public abstract class DefaultSubsystem<T extends Command> extends SubsystemBase {
    private final Provider<T> defaultCommand;

    public DefaultSubsystem(Provider<T> defaultCommand) {
        this.defaultCommand = defaultCommand;
    }

    @Override
    public void periodic() {
        if (getDefaultCommand() == null) {
            setDefaultCommand(defaultCommand.get());
        }
    }
}
