package org.rivierarobotics.inject;

import javax.inject.Qualifier;

@Qualifier
public @interface Input {
    enum Position {
        DRIVER_LEFT, DRIVER_RIGHT, CODRIVER_LEFT, CODRIVER_RIGHT
    }

    Position value();
}
