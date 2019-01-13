package org.rivierarobotics.inject;

import org.rivierarobotics.commands.DriveControlCommand;
import org.rivierarobotics.commands.DriveForward;

import dagger.Component;
import org.rivierarobotics.commands.DriveForwardCreator;
import org.rivierarobotics.subsystems.SubsystemModule;

import javax.inject.Singleton;

@Component(modules = {
        SubsystemModule.class
})
@Singleton
public abstract class CommandComponent {
	public abstract DriveControlCommand getDriveControlCreator();
	public abstract DriveForwardCreator getDriveForwardCreator();

	public final DriveForward newDriveForward(double power, double time) {
	    return getDriveForwardCreator().create(power, time);
    }

    public final DriveControlCommand newDriveControlCommand() {
	    return getDriveControlCreator();
    }


}
