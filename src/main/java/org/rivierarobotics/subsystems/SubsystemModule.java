/*
 * This file is part of Placeholder-2019, licensed under the GNU General Public License (GPLv3).
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

package org.rivierarobotics.subsystems;

import javax.inject.Singleton;

import org.rivierarobotics.inject.Sided;

import dagger.Module;
import dagger.Provides;

@Module
public class SubsystemModule {
    //TODO change drivetrain solenoid values
    private static final int L_TALON_ENC = 4;
    private static final int L_TALON_ZED = 5;
    private static final int L_SPARK_ZED = 8;

    private static final int R_TALON_ZED = 2;
    private static final int R_TALON_ENC = 1;
    private static final int R_SPARK_ZED = 9;

    private static final int L_SHIFT_SOLENOID = 6;
    private static final int R_SHIFT_SOLENOID = 7;

    @Provides
    @Singleton
    @Sided(Sided.Side.LEFT)
    public static DriveTrainSide provideDriveSideLeft() {
        return new DriveTrainSide(L_TALON_ENC, L_TALON_ZED, L_SPARK_ZED, false);
    }

    @Provides
    @Singleton
    @Sided(Sided.Side.RIGHT)
    public static DriveTrainSide provideDriveSideRight() {
        return new DriveTrainSide(R_TALON_ENC, R_TALON_ZED, R_SPARK_ZED, true);
    }

    @Provides
    @Singleton
    @Sided(Sided.Side.LEFT)
    public static ShifterSide provideShifterSideLeft() {
        return new ShifterSide(L_SHIFT_SOLENOID);
    }

    @Provides
    @Singleton
    @Sided(Sided.Side.RIGHT)
    public static ShifterSide provideShifterSideRight() {
        return new ShifterSide(R_SHIFT_SOLENOID);
    }
}
