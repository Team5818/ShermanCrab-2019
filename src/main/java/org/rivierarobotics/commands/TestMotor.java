package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.util.MathUtil;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

@GenerateCreator
public class TestMotor extends Command {
    @Singleton
    static class TMSystem extends Subsystem {
        @Inject
        public TMSystem() {

        }

        @Override
        protected void initDefaultCommand() {

        }
    }

    private final DoubleSupplier stick;
    private final DoubleConsumer out;

    public TestMotor(@Provided TMSystem tmSystem, DoubleSupplier stick, DoubleConsumer out) {
        requires(tmSystem);
        this.stick = stick;
        this.out = out;
    }

    @Override
    protected void execute() {
        out.accept(MathUtil.fitDeadband(stick.getAsDouble()));
    }

    @Override
    protected void end() {
        out.accept(0);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
