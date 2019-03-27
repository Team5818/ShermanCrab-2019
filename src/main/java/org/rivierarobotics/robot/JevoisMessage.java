package org.rivierarobotics.robot;

import com.flowpowered.math.vector.Vector2i;

import java.util.ArrayList;
import java.util.List;

public class JevoisMessage {
    private final String id;
    private final List<Vector2i> points;
    private final String extra;

    public JevoisMessage(String id, List<Vector2i> points, String extra) {
        this.id = id;
        this.points = new ArrayList<>(points);
        this.extra = extra;
    }

    public String getId() {
        return id;
    }

    public List<Vector2i> getPoints() {
        return points;
    }

    public String getExtra() {
        return extra;
    }

    @Override
    public String toString() {
        return "JevoisMessage{" +
                "id='" + id + '\'' +
                ", points=" + points +
                ", extra='" + extra + '\'' +
                '}';
    }
}
