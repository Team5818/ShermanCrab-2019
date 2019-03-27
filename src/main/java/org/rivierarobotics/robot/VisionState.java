package org.rivierarobotics.robot;

import edu.wpi.first.wpilibj.Notifier;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Singleton
public class VisionState {

    private final Iterator<JevoisMessage> iterator;
    private final Lock lock = new ReentrantLock();
    private final ArrayDeque<JevoisMessage> currentMessage = new ArrayDeque<>();
    private final Thread thread;

    @Inject
    public VisionState() {
        this.iterator = new JevoisMessageIterator();
        this.thread = new Thread(this::setCurrentMessage);
        thread.setName("vision-state-updater");
        thread.setDaemon(true);
        thread.start();
    }

    private void setCurrentMessage() {
        while (iterator.hasNext()) {
            var next = iterator.next();
            lock.lock();
            try {
                if (currentMessage.size() >= 5) {
                    currentMessage.removeFirst();
                }
                currentMessage.addLast(next);
            } finally {
                lock.unlock();
            }
        }
    }

    public List<JevoisMessage> getCurrentMessage() {
        lock.lock();
        try {
            return new ArrayList<>(currentMessage);
        } finally {
            lock.unlock();
        }
    }

}
