package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.subsystems.ArmPosition;

@GenerateCreator
public class ArmClimb extends CommandGroup {
    public ArmClimb(@Provided ArmCommands arm) {
        addSequential(arm.setFrontPosition(ArmPosition.L2_CLIMB));
        addSequential(new TimedCommand(5.0));
        addSequential(arm.setFrontPosition(ArmPosition.NINETY_DEGREES));
    }
}
