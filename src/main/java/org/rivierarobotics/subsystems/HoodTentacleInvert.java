package org.rivierarobotics.subsystems;

public enum HoodTentacleInvert {
    FRONT_AND_BACK(true, true),
    FRONT_ONLY(true, false),
    BACK_ONLY(false, true),
    NEITHER(false, false);

    public final boolean front, back;

    HoodTentacleInvert(boolean front, boolean back) {
        this.front = front;
        this.back = back;
    }
}
