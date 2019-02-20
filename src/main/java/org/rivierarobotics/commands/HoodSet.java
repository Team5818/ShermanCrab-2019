package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.subsystems.HoodController;
import org.rivierarobotics.subsystems.HoodPosition;

@GenerateCreator
public class HoodSet extends InstantCommand {
    private final HoodController hood;
    private final int pos;

    public HoodSet(@Provided HoodController hood, int pos) {
        this.hood = hood;
        this.pos = pos;
        requires(hood);
    }

    @Override
    protected void execute() {
        hood.setAngle(pos);
    }

    private int enumToAngle(HoodPosition pos) {
        if(pos == HoodPosition.NINETY_DEGREES) {
            return 90;
        } else if(pos == HoodPosition.ONEHUNDREDEIGHTY_DEGREES) {
            return 180;
        } else if(pos == HoodPosition.ZERO_DEGREES) {
            return 0;
        } else {
            throw new IllegalArgumentException("Invalid hood position" + pos);
        }
    }

    //@Override
    //protected boolean isFinished() {
    //    return hood.getAngle() >= enumToAngle(pos);
    //}
}
