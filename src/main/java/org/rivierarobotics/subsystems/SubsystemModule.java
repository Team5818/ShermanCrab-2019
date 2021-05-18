/*
 * This file is part of ShermanCrab-2019, licensed under the GNU General Public License (GPLv3).
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

import dagger.Module;
import dagger.Provides;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.commands.ArmControl;
import org.rivierarobotics.commands.HoodControl;
import org.rivierarobotics.inject.Sided;

import javax.inject.Provider;
import javax.inject.Singleton;

@Module
public class SubsystemModule {
    private static final int L_DRIVE_TALON_MASTER = 4;
    private static final int L_DRIVE_SPARK_SLAVE_ONE = 5;
    private static final int L_DRIVE_SPARK_SLAVE_TWO = 6;

    private static final int R_DRIVE_TALON_MASTER = 1;
    private static final int R_DRIVE_SPARK_SLAVE_ONE = 2;
    private static final int R_DRIVE_SPARK_SLAVE_TWO = 3;

    private static final int ARM_TALON_MASTER = 7;
    private static final int ARM_SPARK_SLAVE_ONE = 8;
    private static final int ARM_SPARK_SLAVE_TWO = 9;

    private static final int HOOD_DRIVE_SPARK = 10;
    private static final int HOOD_ENCODER_TALON = 11;
    private static final int TENTACLE_TALON = 12;
    private static final int WINCH_SPARK = 13;

    private static final int CLIMB_LIMIT_SWITCH = 0;

    private static final int SHIFT_SOLENOID = 0;

    private SubsystemModule() {
    }

    @Provides
    @Singleton
    @Sided(Sided.Side.LEFT)
    public static DriveTrainSide provideDriveSideLeft() {
        return new DriveTrainSide(L_DRIVE_TALON_MASTER, L_DRIVE_SPARK_SLAVE_ONE, L_DRIVE_SPARK_SLAVE_TWO, true);
    }

    @Provides
    @Singleton
    @Sided(Sided.Side.RIGHT)
    public static DriveTrainSide provideDriveSideRight() {
        return new DriveTrainSide(R_DRIVE_TALON_MASTER, R_DRIVE_SPARK_SLAVE_ONE, R_DRIVE_SPARK_SLAVE_TWO, false);
    }

    @Provides
    @Singleton
    public static Shifter provideShifter() {
        return new Shifter(SHIFT_SOLENOID);
    }

    @Provides
    @Singleton
    public static ArmController provideArmMotorGroup(@Provided PistonController pistonController, Provider<ArmControl> command) {
        return new ArmController(pistonController, command, ARM_TALON_MASTER, ARM_SPARK_SLAVE_ONE, ARM_SPARK_SLAVE_TWO);
    }

    @Provides
    @Singleton
    public static HoodController provideHoodController(@Provided ArmController armController, Provider<HoodControl> command) {
        return new HoodController(armController, command, HOOD_DRIVE_SPARK, HOOD_ENCODER_TALON);
    }

    @Provides
    @Singleton
    public static TentacleController provideTentacleController() {
        return new TentacleController(TENTACLE_TALON);
    }

    @Provides
    @Singleton
    public static WinchController provideWinchController(@Provided PistonController pistonController) {
        return new WinchController(pistonController, WINCH_SPARK, CLIMB_LIMIT_SWITCH);
    }
}
