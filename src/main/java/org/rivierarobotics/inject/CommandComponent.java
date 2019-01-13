package org.rivierarobotics.inject;

import org.rivierarobotics.commands.DriveControl;
import org.rivierarobotics.commands.DriveForward;
import dagger.Component;
import org.rivierarobotics.commands.DriveForwardCreator;
import org.rivierarobotics.subsystems.SubsystemModule;
import org.rivierarobotics.robot.ControlsModule;
import javax.inject.Singleton;

@Component(modules = {
        SubsystemModule.class, ControlsModule.class
})
@Singleton
public abstract class CommandComponent {
	public abstract DriveControl newDriveControl();
	abstract DriveForwardCreator getDriveForwardCreator();

	public final DriveForward newDriveForward(double power, double time) {
	    return getDriveForwardCreator().create(power, time);
    }


}
