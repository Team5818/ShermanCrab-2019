package org.rivierarobotics.subsystems;

import javax.inject.Singleton;

import org.rivierarobotics.inject.Sided;

import dagger.Module;
import dagger.Provides;

@Module
public class SubsystemModule {
    private static final int L_TALON_ENC = 1;
    private static final int R_TALON_ENC = 4;
    private static final int L_TALON_ZED = 2;
    private static final int R_TALON_ZED = 3;

    @Provides
    @Singleton
    @Sided(Sided.Side.LEFT)
    public static DriveTrainSide provideDriveSideLeft() {
        return new DriveTrainSide(L_TALON_ENC, L_TALON_ZED, false);
    }

    @Provides
    @Singleton
    @Sided(Sided.Side.RIGHT)
    public static DriveTrainSide provideDriveSideRight() {
        return new DriveTrainSide(R_TALON_ENC, R_TALON_ZED, true);
    }
}
