package org.rivierarobotics.commands;


import javax.inject.Inject;

public class DriveCommands {
    private final DriveForwardCreator driveForwardCreator;

    @Inject
    public DriveCommands(DriveForwardCreator driveForwardCreator) {
        this.driveForwardCreator = driveForwardCreator;
    }

    public DriveForward forward(double power, double distance) {
        return driveForwardCreator.create(power, distance);
    }
 }
