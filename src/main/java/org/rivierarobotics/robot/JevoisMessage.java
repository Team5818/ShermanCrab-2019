package org.rivierarobotics.robot;

import java.util.Arrays;

public class JevoisMessage {
    private final String id;
    private final int[] x;
    private final int[] y;
    private final String extra;

    public JevoisMessage(String id, int[] x, int[] y, String extra) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.extra = extra;
    }

    public String getId() {
        return id;
    }

    public int getX(int i) {
        return x[i];
    }

    public int getY(int i) {
        return y[i];
    }

    public String getExtra() {
        return extra;
    }

    @Override
    public String toString() {
        return "JevoisMessage{" +
                "id='" + id + '\'' +
                ", x=" + Arrays.toString(x) +
                ", y=" + Arrays.toString(y) +
                ", extra='" + extra + '\'' +
                '}';
    }
}
