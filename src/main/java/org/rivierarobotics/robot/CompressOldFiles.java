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

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.GZIPOutputStream;

import static java.util.stream.Collectors.toList;

class CompressOldFiles {
    private static final Pattern MECH_LOG = Pattern.compile("mechanisms-.+\\.log");

    void run() throws IOException {
        List<Path> files;
        try (Stream<Path> logs = Files.list(Paths.get("/home/lvuser/logs"))) {
            files = logs.collect(toList());
        }
        for (Path file : files) {
            String fileName = file.getFileName().toString();
            if (MECH_LOG.matcher(fileName).matches()) {
                compressLog(file);
            }
        }
    }

    private void compressLog(Path file) throws IOException {
        Path compressed = file.resolveSibling(file.getFileName().toString() + ".gz");
        try (OutputStream gzip = new GZIPOutputStream(new BufferedOutputStream(Files.newOutputStream(compressed)));
             InputStream src = Files.newInputStream(file)) {
            byte[] buffer = new byte[8192];
            while (true) {
                int read = src.read(buffer);
                if (read == -1) {
                    break;
                }

                gzip.write(buffer, 0, read);
            }
        }
        Files.delete(file);
    }
}
