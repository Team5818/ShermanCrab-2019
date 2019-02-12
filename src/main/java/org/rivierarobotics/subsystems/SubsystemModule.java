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
    // TODO change drivetrain solenoid values
    // TODO assign talon and spark IDs in Phoenix tuner and SparkMax client
    private static final int L_TALON_MASTER = 1;
    private static final int L_SPARK_SLAVE_ONE = 3;
    private static final int L_SPARK_SLAVE_TWO = 5;

    private static final int R_TALON_MASTER = 2;
    private static final int R_SPARK_SLAVE_ONE = 4;
    private static final int R_SPARK_SLAVE_TWO = 6;

    private static final int L_SHIFT_SOLENOID = 7;
    private static final int R_SHIFT_SOLENOID = 8;

    @Provides
    @Singleton
    @Sided(Sided.Side.LEFT)
    public static DriveTrainSide provideDriveSideLeft() {
        return new DriveTrainSide(L_TALON_MASTER, L_SPARK_SLAVE_ONE, L_SPARK_SLAVE_TWO, false);
    }

    @Provides
    @Singleton
    @Sided(Sided.Side.RIGHT)
    public static DriveTrainSide provideDriveSideRight() {
        return new DriveTrainSide(R_TALON_MASTER, R_SPARK_SLAVE_ONE, R_SPARK_SLAVE_TWO, true);
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
