package org.rivierarobotics.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import net.octyl.aptcreator.Provided;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PrintSmartDash {
    @Inject
    public PrintSmartDash(@Provided DriveTrain driveTrain) {
        SmartDashboard.putNumber("left encoder", driveTrain.getLeftSide().getDistance());
        SmartDashboard.putNumber("right encoder", driveTrain.getRightSide().getDistance());
    }
}
