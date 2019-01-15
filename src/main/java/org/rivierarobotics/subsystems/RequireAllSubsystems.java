package org.rivierarobotics.subsystems;

import edu.wpi.first.wpilibj.command.Command;
import net.octyl.aptcreator.Provided;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RequireAllSubsystems extends Command {
    @Inject
    public RequireAllSubsystems(@Provided DriveTrain driveTrain) {
        requires(driveTrain);
        setInterruptible(false);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
