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

package org.rivierarobotics.util;

import org.slf4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collector;

/**
 * Mechanism logger. Provides utilities for tracking mechanism changes.
 */
public class MechLogger {

    private static final double POWER_CMP_FUZZ = 0.001;

    private static boolean basicallyEqual(double a, double b) {
        return a - POWER_CMP_FUZZ < b && b < a + POWER_CMP_FUZZ;
    }

    private static StringJoiner newTagJoiner() {
        return new StringJoiner(",", "tags=", ", ")
            .setEmptyValue("");
    }

    private final Logger delegate;
    private final String tagsProcessed;
    private double lastPower;
    private Double lastSetpoint;
    private final Map<String, Object> conditions = new HashMap<>();

    MechLogger(Logger delegate, List<String> tags) {
        this.delegate = delegate;
        this.tagsProcessed = tags.stream().collect(Collector.of(
            MechLogger::newTagJoiner,
            StringJoiner::add,
            StringJoiner::merge,
            StringJoiner::toString
        ));
    }

    private void logPower(double power) {
        delegate.info(tagsProcessed + "op=power_change, power=" + power);
    }

    private void logSetpoint(double setpoint) {
        delegate.info(tagsProcessed + "op=setpoint_change, setpoint=" + setpoint);
    }

    private void logConditionChange(String name, Object value) {
        delegate.info(tagsProcessed + "op=condition_change, name=" + name + ", value=" + value);
    }

    public void powerChange(double power) {
        double lastLastPower = this.lastPower;
        this.lastPower = power;
        if (basicallyEqual(lastLastPower, power)) {
            return;
        }
        if (basicallyEqual(lastLastPower, 0.0) || basicallyEqual(power, 0.0)) {
            // switching from 0 -> any power, or vice versa
            logPower(power);
        } else if (Math.abs(lastLastPower - power) >= 0.05) {
            // change in power of >=0.05
            logPower(power);
        }
    }

    public void clearSetpoint() {
        lastSetpoint = null;
    }

    public void setpointChange(double setpoint) {
        Double lastLastSetpoint = lastSetpoint;
        lastSetpoint = setpoint;
        if (lastLastSetpoint != null && basicallyEqual(lastLastSetpoint, setpoint)) {
            return;
        }
        logSetpoint(setpoint);
    }

    public void conditionChange(String name, Object value) {
        if (Objects.equals(conditions.get(name), value)) {
            return;
        }
        conditions.put(name, value);
        logConditionChange(name, value);
    }
}
