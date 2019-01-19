package org.rivierarobotics.inject;

import javax.inject.Singleton;

import org.rivierarobotics.inject.CommandComponent.CCModule;
import org.rivierarobotics.robot.ButtonConfiguration;
import org.rivierarobotics.robot.ControlsModule;
import org.rivierarobotics.subsystems.DriveTrain;
import org.rivierarobotics.subsystems.SubsystemModule;

import dagger.Component;

@Component(modules = { SubsystemModule.class, ControlsModule.class, CCModule.class })
@Singleton 
public abstract class GlobalComponent {
    public abstract DriveTrain getDriveTrain();

    public abstract ButtonConfiguration getButtonConfiguration();

}
