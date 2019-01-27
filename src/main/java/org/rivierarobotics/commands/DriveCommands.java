package org.rivierarobotics.commands;


import javax.inject.Inject;

public class DriveCommands {
    private final DriveForwardCreator driveForwardCreator;
    private final DriveVelocityCreator driveVelocityCreator;

    @Inject
    public DriveCommands(DriveForwardCreator driveForwardCreator, DriveVelocityCreator driveVelocityCreator) {
        this.driveForwardCreator = driveForwardCreator;
        this.driveVelocityCreator = driveVelocityCreator;
    }

    public DriveForward forward(double power, double distance) {
        return driveForwardCreator.create(power, distance);
    }

    public DriveVelocity velocity() {
        return driveVelocityCreator.create();
    }
}
