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

package org.rivierarobotics.robot;

import edu.wpi.first.wpilibj.SerialPort;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Optional;
import java.util.Scanner;

public class JevoisMessageIterator implements Iterator<JevoisMessage> {

    private final Scanner cameraOne;

    public JevoisMessageIterator() {
        SerialPort port = new SerialPort(115200, SerialPort.Port.kUSB1);
        this.cameraOne = new Scanner(new SerialInputStream(port), StandardCharsets.US_ASCII);
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public JevoisMessage next() {
        Optional<JevoisMessage> message;
        do {
            message = tryNext();
            cameraOne.nextLine();
        } while (message.isEmpty());
        return message.get();
    }

    private Optional<JevoisMessage> tryNext() {
        if (!cameraOne.next().equals("D2")) {
            return Optional.empty();
        }
        String id = cameraOne.next();
        int[] x = new int[4];
        int[] y = new int[4];
        for (int i =0; i< x.length; i++) {
            if (!cameraOne.hasNextInt()) {
                return Optional.empty();
            }
            x[i] = cameraOne.nextInt();
            if (!cameraOne.hasNextInt()) {
                return Optional.empty();
            }
            y[i] = cameraOne.nextInt();
        }
        String extra = cameraOne.next();
        return Optional.of(new JevoisMessage(id, x, y, extra));
    }
}