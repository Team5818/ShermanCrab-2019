package org.rivierarobotics.subsystems;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import org.rivierarobotics.inject.Sided;

@Module
public class SubsystemModule {
    //@TODO determine talon IDs
    private static final int L_TALON_ENC = 6;
    private static final int R_TALON_ENC = 3;
    private static final int L_TALON_ZED = 1;
    private static final int R_TALON_ZED = 2;

    private static final int JS_THROTTLE = 0;
    private static final int JS_TURNING = 1;

    @Provides
    @Singleton
    @Sided(Sided.Side.LEFT)
    public static DriveTrainSide provideDriveSideLeft() {
        return new DriveTrainSide(L_TALON_ENC, L_TALON_ZED, true);
    }

    @Provides
    @Singleton
    @Sided(Sided.Side.RIGHT)
    public static DriveTrainSide provideDriveSideRight() {
        return new DriveTrainSide(R_TALON_ENC, R_TALON_ZED, false);
    }
}
