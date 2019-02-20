/*
 * This file is part of Placeholder-2019, licensed under the GNU General Public License (GPLv3).
 *
 * Copyright (c) Riviera Robotics <https://github.com/Team5818>
 * Copyright (c) contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.rivierarobotics.commands;

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
