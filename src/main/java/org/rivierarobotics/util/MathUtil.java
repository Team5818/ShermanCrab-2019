package org.rivierarobotics.util;

public class MathUtil {
    private static final double DEADBAND = 0.05;
    
    public static double fitDeadband(double val) {
        double abs = Math.abs(val);
        if (abs < DEADBAND) {
            return 0;
        }
        if (abs > 1) {
            return 1;
        }
        return (val - DEADBAND) / (1 - DEADBAND);
    }
}
