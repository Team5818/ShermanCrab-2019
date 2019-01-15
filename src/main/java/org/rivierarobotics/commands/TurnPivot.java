package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.robot.UtilMap;
import org.rivierarobotics.subsystems.DriveTrain;

import javax.inject.Inject;

// turns by pivoting the robot the direction to turn
// pivots around front left/right respectively

@GenerateCreator
public class TurnPivot extends InstantCommand {
    private DriveTrain driveTrain;
    private double startPos;
    private double currentPos;
    private boolean turnLeft;

    @Inject
    public TurnPivot(@Provided DriveTrain dt, boolean trnLft) {
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
            driveTrain.setPower(0, UtilMap.PWR_MULT);
            currentPos = driveTrain.getRightSide().getDistance();
        } else {
            driveTrain.setPower(UtilMap.PWR_MULT, 0);
            currentPos = driveTrain.getLeftSide().getDistance();
        }
    }

    @Override
    protected void end() {
        driveTrain.stop();
    }

    @Override
    protected boolean isFinished() {
        return (currentPos - startPos) * UtilMap.COUNTS_TO_INCHES_FACTOR >= UtilMap.NINETY_DEGREES;
    }
}
