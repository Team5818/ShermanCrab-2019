package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.robot.UtilMap;
import org.rivierarobotics.subsystems.DriveTrain;

import javax.inject.Inject;

// turns in place by using sides going opposite directions

@GenerateCreator
public class TurnSpin extends InstantCommand {
    private DriveTrain driveTrain;
    private double startPos;
    private double leftPos;
    private double rightPos;
    private boolean turnLeft;

    @Inject
    public TurnSpin(@Provided DriveTrain dt, boolean trnLft) {
        this.driveTrain = dt;
        this.turnLeft = trnLft;
        requires(dt);
    }

    @Override
    protected void initialize() {
        if(turnLeft) {
            startPos = driveTrain.getRightSide().getDistance();
        } else {
            startPos = driveTrain.getLeftSide().getDistance();
        }
    }

    @Override
    protected void execute() {
        if(turnLeft) {
            driveTrain.setPower(-UtilMap.PWR_MULT, UtilMap.PWR_MULT);
        } else {
            driveTrain.setPower(UtilMap.PWR_MULT, -UtilMap.PWR_MULT);
        }

        leftPos = driveTrain.getLeftSide().getDistance();
        rightPos = driveTrain.getRightSide().getDistance();
    }

    @Override
    protected void end() {
        driveTrain.stop();
    }

    @Override
    protected boolean isFinished() {
        return ((leftPos - startPos) * UtilMap.COUNTS_TO_INCHES_FACTOR >= UtilMap.NINETY_DEGREES) &&
               ((rightPos - startPos) * UtilMap.COUNTS_TO_INCHES_FACTOR >= UtilMap.NINETY_DEGREES);
    }
}
