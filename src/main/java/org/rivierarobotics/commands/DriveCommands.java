package org.rivierarobotics.commands;

import javax.inject.Inject;

public class DriveCommands {
    private final DriveForwardCreator driveForwardCreator;
    private final DriveVelocityCreator driveVelocityCreator;
    private final DriveDistanceCreator driveDistanceCreator;

    @Inject
    public DriveCommands(DriveForwardCreator driveForwardCreator, DriveVelocityCreator driveVelocityCreator,
            DriveDistanceCreator driveDistanceCreator) {
        this.driveForwardCreator = driveForwardCreator;
        this.driveVelocityCreator = driveVelocityCreator;
        this.driveDistanceCreator = driveDistanceCreator;
    }

    public DriveForward forward(double power, double distance) {
        return driveForwardCreator.create(power, distance);
    }

    public DriveVelocity velocity() {
        return driveVelocityCreator.create();
    }

    public DriveDistance distance(double distance) {
        return driveDistanceCreator.create(distance);
    }
}
