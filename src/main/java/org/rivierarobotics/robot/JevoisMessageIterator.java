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