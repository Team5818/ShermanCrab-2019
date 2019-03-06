package org.rivierarobotics.robot;

import edu.wpi.first.wpilibj.SerialPort;
import org.jetbrains.annotations.NotNull;

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
    public int read(@NotNull byte[] b, int off, int len) throws IOException {
        int filled = 0;
        while (filled < len) {
            int copyable = Math.min(array.remaining(), len - filled);
            array.get(b, off + filled, copyable);
            filled += copyable;
        }
        return filled;
    }
}
