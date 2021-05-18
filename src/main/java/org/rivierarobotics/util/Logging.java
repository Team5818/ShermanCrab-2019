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

package org.rivierarobotics.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Logging {
    private Logging() {
    }

    public static MechLogger getLogger(Class<?> clazz, String... tags) {
        return getLogger(clazz.getName(), tags);
    }

    public static MechLogger getLogger(String name, String... tags) {
        return getMechLogger(LoggerFactory.getLogger(name), tags);
    }

    private static MechLogger getMechLogger(Logger logger, String[] tags) {
        return new MechLogger(logger, Stream.of(tags).collect(Collectors.toList()));
    }
}
