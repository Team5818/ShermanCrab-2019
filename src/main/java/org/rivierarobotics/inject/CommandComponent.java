package org.rivierarobotics.inject;

import org.rivierarobotics.commands.*;
import org.rivierarobotics.subsystems.PrintSmartDash;
import org.rivierarobotics.subsystems.SubsystemModule;
import org.rivierarobotics.robot.ControlsModule;

import javax.inject.Singleton;
import dagger.Component;

@Component(modules = {
		SubsystemModule.class, ControlsModule.class
})
@Singleton
public abstract class CommandComponent {
	public abstract DriveControl newDriveControl();
	public abstract RequireAllSubsystems getRequireAllSubsystems();
	public abstract PrintSmartDash getPrintSmartDash();
	public abstract DriveForwardCreator getDriveForwardCreator();
	public abstract TurnPivotCreator getTurnPivotCreator();
	public abstract TurnSpinCreator getTurnSpinCreator();

	public final DriveForward newDriveForward(double power, double time) {
		return getDriveForwardCreator().create(power, time);
	}

	public final TurnPivot newTurnPivot(boolean turnLeft) {
		return getTurnPivotCreator().create(turnLeft);
	}

    public final TurnSpin newTurnSpin(boolean turnLeft) {
        return getTurnSpinCreator().create(turnLeft);
    }
}