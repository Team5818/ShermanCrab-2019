/*
 * This file is part of ShermanCrab-2019, licensed under the GNU General Public License (GPLv3).
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

import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;

@GenerateCreator
public class TestSolenoid extends Command {
    private final Consumer<Boolean> out;

    public TestSolenoid(@Provided TSSystem tsSystem, Consumer<Boolean> out) {
        requires(tsSystem);
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

    @Singleton
    public static class TSSystem extends Subsystem {
        @Inject
        public TSSystem() {
        }

        @Override
        protected void initDefaultCommand() {
        }
    }
}
