package org.rivierarobotics.inject;

import javax.inject.Qualifier;

@Qualifier
public @interface Sided {
    enum Side {
        LEFT, RIGHT
    }

    Side value();
}
