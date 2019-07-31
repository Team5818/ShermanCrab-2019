package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.subsystems.WinchController;

@GenerateCreator
public class WinchSafetyOverride extends InstantCommand {
    private WinchController winchController;

    public WinchSafetyOverride(@Provided WinchController winchController) {
        this.winchController = winchController;
        requires(winchController);
    }

    @Override
    protected void execute() {
        winchController.LOCK_OVERRIDE = !winchController.LOCK_OVERRIDE;
    }
}
