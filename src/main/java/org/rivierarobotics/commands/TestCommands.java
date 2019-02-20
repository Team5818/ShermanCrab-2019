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

import javax.inject.Inject;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

public class TestCommands {
    private final TestMotorCreator testMotorCreator;
    private final TestSolenoidCreator testSolenoidCreator;

    @Inject
    public TestCommands(TestMotorCreator testMotorCreator, TestSolenoidCreator testSolenoidCreator) {
        this.testMotorCreator = testMotorCreator;
        this.testSolenoidCreator = testSolenoidCreator;
    }

    public TestMotor motor(DoubleSupplier stick, DoubleConsumer out) {
        return testMotorCreator.create(stick, out);
    }

    public TestSolenoid solenoid(Consumer<Boolean> out) {
        return testSolenoidCreator.create(out);
    }
}
