package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import net.octyl.aptcreator.GenerateCreator;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.util.MathUtil;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

@GenerateCreator
public class TestSolenoid extends Command {
    @Singleton
    static class TSSystem extends Subsystem {
        @Inject
        public TSSystem() {
        }

        @Override
        protected void initDefaultCommand() {

        }
    }

    private final Consumer<Boolean> out;

    public TestSolenoid(@Provided TSSystem TSSystem, Consumer<Boolean> out) {
        requires(TSSystem);
        this.out = out;
    }

    @Override
    protected void execute() {
        out.accept(true);
    }

    @Override
    protected void end() {
        out.accept(false);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
