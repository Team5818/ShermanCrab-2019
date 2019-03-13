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

package org.rivierarobotics.commands;

import javax.inject.Inject;
import javax.inject.Provider;

public class ClimbCommands {
    public static boolean SUCTION_DONE = false;
    private final Provider<PistonClimb> pistonClimbProvider;
    private final Provider<PistonClimbCleanup> pistonClimbCleanupProvider;
    private final Provider<ScissorClimb> scissorClimbProvider;
    private final Provider<ScissorClimbCleanup> scissorClimbCleanupProvider;
    private final Provider<SuctionClimb> suctionClimbProvider;
    private final Provider<SuctionClimbCleanup> SuctionClimbCleanupProvider;

    @Inject
    public ClimbCommands(Provider<PistonClimb> pistonClimbProvider,
                         Provider<PistonClimbCleanup> pistonClimbCleanupProvider,
                         Provider<ScissorClimb> scissorClimbProvider,
                         Provider<ScissorClimbCleanup> scissorClimbCleanupProvider,
                         Provider<SuctionClimb> suctionClimbProvider,
                         Provider<SuctionClimbCleanup> SuctionClimbCleanupProvider) {
        this.pistonClimbProvider = pistonClimbProvider;
        this.pistonClimbCleanupProvider = pistonClimbCleanupProvider;
        this.scissorClimbProvider = scissorClimbProvider;
        this.scissorClimbCleanupProvider = scissorClimbCleanupProvider;
        this.suctionClimbProvider = suctionClimbProvider;
        this.SuctionClimbCleanupProvider = SuctionClimbCleanupProvider;
    }

    public final PistonClimb piston() {
        return pistonClimbProvider.get();
    }

    public final PistonClimbCleanup pistonCleanup() {
        return pistonClimbCleanupProvider.get();
    }

    public final ScissorClimb scissor() {
        return scissorClimbProvider.get();
    }

    public final ScissorClimbCleanup scissorCleanup() {
        return scissorClimbCleanupProvider.get();
    }

    public final SuctionClimb suction() {
        SUCTION_DONE = false;
        return suctionClimbProvider.get();
    }

    public final SuctionClimbCleanup suctionCleanup() {
        SUCTION_DONE = true;
        return SuctionClimbCleanupProvider.get();
    }
}
