package org.rivierarobotics.util;

import com.google.common.base.Joiner;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
    private double lastSetpoint;
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
        delegate.info("op=power_change, power=" + power);
    }

    private void logSetpoint(double setpoint) {
        delegate.info("op=setpoint_change, setpoint=" + setpoint);
    }

    private void logConditionChange(String name, Object value) {
        delegate.info("op=condition_change, name=" + name + ", value=" + value);
    }

    public void powerChange(double power) {
        double lastLastPower = this.lastPower;
        this.lastPower = power;
        if (basicallyEqual(lastLastPower, power)) {
            return;
        }
        if (basicallyEqual(lastLastPower, 0.0)) {
            // switching from 0 -> any power
            logPower(power);
        } else if (Math.abs(lastLastPower - power) > 0.1) {
            // change in power of >0.1
            logPower(power);
        }
    }

    public void setpointChange(double setpoint) {
        double lastLastSetpoint = lastSetpoint;
        lastSetpoint = setpoint;
        if (basicallyEqual(lastLastSetpoint, setpoint)) {
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
