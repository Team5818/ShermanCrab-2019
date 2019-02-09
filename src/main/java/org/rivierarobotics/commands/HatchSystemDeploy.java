package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.subsystems.HatchController;
import org.rivierarobotics.subsystems.Piston;

@GenerateCreator
public class HatchSystemDeploy extends InstantCommand {
    private HatchController hc;

    public HatchSystemDeploy(@Provided HatchController hc) {
        this.hc = hc;
        requires(hc);
    }

    @Override
    protected void initialize() {
        if(!hc.getPistonState(Piston.DEPLOY_LEFT) || !hc.getPistonState(Piston.DEPLOY_RIGHT)) {
            hc.extendPiston(Piston.DEPLOY_LEFT);
            hc.extendPiston(Piston.DEPLOY_RIGHT);
        } else {
            hc.retractPiston(Piston.DEPLOY_LEFT);
            hc.retractPiston(Piston.DEPLOY_RIGHT);
        }
    }
}
