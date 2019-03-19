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
