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

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

class SerialInputStream extends InputStream {
    private final SerialPort port;
    private ByteBuffer array;

    public SerialInputStream(SerialPort port) {
        this.port = port;
    }

    private void refill() {
        if (array != null && array.hasRemaining()) {
            return;
        }
        int bytesReceived = port.getBytesReceived();
        while (bytesReceived == 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            bytesReceived = port.getBytesReceived();
        }
        array = ByteBuffer.wrap(port.read(bytesReceived));
    }

    @Override
    public int read() throws IOException {
        refill();
        return array.get() & 0xFF;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int filled = 0;
        while (filled < len) {
            int copyable = Math.min(array.remaining(), len - filled);
            array.get(b, off + filled, copyable);
            filled += copyable;
        }
        return filled;
    }
}
