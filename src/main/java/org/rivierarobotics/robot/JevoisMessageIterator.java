package org.rivierarobotics.robot;

import com.flowpowered.math.vector.Vector2i;
import edu.wpi.first.wpilibj.SerialPort;

import java.nio.charset.StandardCharsets;
import java.util.*;

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
        var points = new ArrayList<Vector2i>();
        for (int i =0; i< 4; i++) {
            if (!cameraOne.hasNextInt()) {
                return Optional.empty();
            }
            int x = cameraOne.nextInt();
            if (!cameraOne.hasNextInt()) {
                return Optional.empty();
            }
            int y = cameraOne.nextInt();
            points.add(Vector2i.from(x, y));
        }
        String extra = cameraOne.next();
        return Optional.of(new JevoisMessage(id, points, extra));
    }
}