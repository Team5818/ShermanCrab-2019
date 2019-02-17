package org.rivierarobotics.subsystems;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;

public class SparkMaxVolts {

    private static final MethodHandle SETPOINT_COMMAND_METHOD;
    static {
        Method method;
        try {
            method = CANSparkMaxLowLevel.class.getDeclaredMethod("setpointCommand", double.class, ControlType.class);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }
        method.setAccessible(true);
        try {
            SETPOINT_COMMAND_METHOD = MethodHandles.lookup().unreflect(method);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void set(CANSparkMax sm, double volts) {
        try {
            SETPOINT_COMMAND_METHOD.invokeExact(sm, volts, ControlType.kVoltage);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

}
