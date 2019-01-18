package org.rivierarobotics.inject;

import javax.inject.Singleton;

import org.rivierarobotics.commands.DriveControl;
import org.rivierarobotics.commands.DriveForward;
import org.rivierarobotics.commands.DriveForwardCreator;
import org.rivierarobotics.robot.ControlsModule;
import org.rivierarobotics.subsystems.SubsystemModule;

import dagger.Component;

@Component(modules = { SubsystemModule.class, ControlsModule.class })
@Singleton
public abstract class CommandComponent {
    public abstract DriveControl newDriveControl();

    abstract DriveForwardCreator getDriveForwardCreator();

    public final DriveForward newDriveForward(double power, double time) {
        return getDriveForwardCreator().create(power, time);
    }

}
