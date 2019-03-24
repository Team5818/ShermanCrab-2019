package org.rivierarobotics.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logging {

    public static MechLogger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }

    public static MechLogger getLogger(String name) {
        return getMechLogger(LoggerFactory.getLogger(name));
    }

    private static MechLogger getMechLogger(Logger logger) {
        return new MechLogger(logger);
    }

}
