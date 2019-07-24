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

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import java.util.ArrayList;
import java.util.List;

public class TestControllers {
    private static final List<SpeedController> CONTROLLERS = new ArrayList<>();

    static {
        ShuffleboardTab tab = Shuffleboard.getTab("Test Controllers");
        for (int i = 1; i <= 13; i++) {
            SpeedController c;
            switch (i) {
                case 1:
                case 4:
                case 7:
                case 11:
                case 12:
                    c = new WPI_TalonSRX(i);
                    tab.add((Sendable) c);
                    break;
                default:
                    c = new CANSparkMax(i, CANSparkMaxLowLevel.MotorType.kBrushless);
                    break;
            }
            CONTROLLERS.add(c);
        }
    }

    public static SpeedController get(int i) {
        return CONTROLLERS.get(i - 1);
    }
}
