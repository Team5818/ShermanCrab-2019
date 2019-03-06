package org.rivierarobotics.robot;

import edu.wpi.first.wpilibj.Notifier;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

@Singleton
public class VisionState {

    private final Iterator<JevoisMessage> iterator;
    private final AtomicReference<JevoisMessage> currentMessage = new AtomicReference<>();
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
            currentMessage.set(iterator.next());
        }
    }

    @Nullable
    public JevoisMessage getCurrentMessage() {
        return currentMessage.get();
    }

}
