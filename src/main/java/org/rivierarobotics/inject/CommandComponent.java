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

package org.rivierarobotics.inject;

import dagger.Module;
import dagger.Subcomponent;
import org.rivierarobotics.commands.ArmCommands;
import org.rivierarobotics.commands.ClimbCommands;
import org.rivierarobotics.commands.DriveCommands;
import org.rivierarobotics.commands.GearCommands;
import org.rivierarobotics.commands.HatchCommands;
import org.rivierarobotics.commands.HoodCommands;
import org.rivierarobotics.commands.PistonCommands;
import org.rivierarobotics.commands.TentacleCommands;
import org.rivierarobotics.commands.TestCommands;
import org.rivierarobotics.commands.WinchCommands;

@Subcomponent
public abstract class CommandComponent {
    public abstract DriveCommands drive();

    public abstract PistonCommands piston();

    public abstract HatchCommands hatch();

    public abstract GearCommands gear();

    public abstract HoodCommands hood();

    public abstract ArmCommands arm();

    public abstract TentacleCommands tentacle();

    public abstract TestCommands test();

    public abstract ClimbCommands climb();

    public abstract WinchCommands winch();

    @Module(subcomponents = CommandComponent.class)
    public interface CCModule {
    }

    @Subcomponent.Builder
    public interface Builder {
        CommandComponent build();
    }
}
