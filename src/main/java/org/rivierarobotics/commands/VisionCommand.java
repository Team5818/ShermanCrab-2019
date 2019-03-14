package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.Command;
import net.octyl.aptcreator.Provided;
import org.rivierarobotics.robot.JevoisDataParsing;
import org.rivierarobotics.subsystems.DriveTrain;

import java.util.ArrayList;

public class VisionCommand extends Command {
    public JevoisDataParsing data;
    private DriveTrain dt;
    private static final double RECTANGLE_CENTER_YCOORD = 50;
    private static final double CENTER_COORD_BUFFER = 5;
    private static final int CAMERA_PIXEL_WIDTH = 160;
    private static final int CENTERED_IMAGE_PIXEL_LEFT = 40;
    private static final int CENTERED_IMAGE_PIXEL_RIGHT = 60;
    private static final double TARGET_LENGTH = 15;

    public VisionCommand(@Provided DriveTrain dt, JevoisDataParsing data) {
        this.dt = dt;
        this.data = data;
        requires(dt);
    }

    @Override
    protected void execute() {
        ArrayList<String[]> blobs = screenBlobs();
        double objectOne = getCenter(getXCoords(0, blobs)[0], getYCoords(0, blobs)[0], getXCoords(0, blobs)[1],
                getYCoords(0, blobs)[1], getXCoords(0, blobs)[2], getYCoords(0, blobs)[2])[0] / CAMERA_PIXEL_WIDTH;
        double objectTwo = getCenter(getXCoords(1, blobs)[0], getYCoords(1, blobs)[0], getXCoords(1, blobs)[1],
                getYCoords(1, blobs)[1], getXCoords(1, blobs)[2], getYCoords(1, blobs)[2])[0] / CAMERA_PIXEL_WIDTH;


        double power = .4;
        if (objectOne > objectTwo) {
            if (objectTwo < CENTERED_IMAGE_PIXEL_LEFT) {
                dt.setPower(power, (objectTwo + 1) * power);
            } else if (objectTwo > CENTERED_IMAGE_PIXEL_LEFT) {
                dt.setPower((objectTwo + 1) * power, power);
            } else {
                dt.setPower(power, power);
            }
        } else if (objectTwo > objectOne) {
            if (objectOne < CENTERED_IMAGE_PIXEL_RIGHT) {
                dt.setPower(power, (objectOne + 1) * power);
            } else if (objectOne > CENTERED_IMAGE_PIXEL_RIGHT) {
                dt.setPower((objectOne + 1) * power, power);
            } else {
                dt.setPower(power, power);
            }
        }

    }

    @Override
    protected boolean isFinished() {
        if (getTargetLength(0) > TARGET_LENGTH && getTargetLength(1) > TARGET_LENGTH) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<String[]> screenBlobs() {
        ArrayList<String[]> blobs = getBlobs();
        for (int x = 0; x < getNumBlobs(); x++) {
            if (getCenter(x)[1] > RECTANGLE_CENTER_YCOORD + CENTER_COORD_BUFFER || getCenter(x)[1] < RECTANGLE_CENTER_YCOORD - CENTER_COORD_BUFFER) {
                blobs.remove(x);
            }
        }
        if (blobs.size() > 2) {
            for (int i = 2; i < blobs.size(); i++) {
                blobs.remove(i);
            }

            return blobs;
        }
        return blobs;
    }

    public double getTargetAngleRadians(int component) {

        double[] lengths = new double[4];
        double[] xDist = new double[4];
        for (int i = 0; i < 3; i++) {
            lengths[i] = calculateDistance(getXCoords(component)[i], getYCoords(component)[i], getXCoords(component)[i + 1], getYCoords(component)[i + 1]);
            if (getXCoords(component)[i] > getXCoords(component)[i + 1]) {
                xDist[i] = getXCoords(component)[i] - getXCoords(component)[i + 1];
            } else {
                xDist[i] = getXCoords(component)[i + 1] - getXCoords(component)[i];
            }
        }
        lengths[3] = calculateDistance(getXCoords(component)[3], getYCoords(component)[3], getXCoords(component)[0], getYCoords(component)[0]);
        if (getXCoords(component)[3] > getXCoords(component)[0]) {
            xDist[3] = getXCoords(component)[3] - getXCoords(component)[0];
        } else {
            xDist[3] = getXCoords(component)[0] - getXCoords(component)[3];
        }

        double sideOne = average(lengths[0], lengths[2]);
        double sideOneXDist = average(xDist[0], xDist[2]);

        double sideTwo = average(lengths[1], lengths[3]);
        double sideTwoXDist = average(xDist[1], xDist[3]);

        if (sideOne > sideTwo) {
            return (sideOneXDist / sideOne);
        } else {
            return (sideTwoXDist / sideTwo);
        }
    }

    public double getTargetLength(int component) {
        ArrayList<String[]> blobs = screenBlobs();
        double[] lengths = new double[4];
        for (int i = 0; i < 3; i++) {
            lengths[i] = calculateDistance(getXCoords(component, blobs)[i], getYCoords(component, blobs)[i], getXCoords(component, blobs)[i + 1], getYCoords(component, blobs)[i + 1]);
        }
        lengths[3] = calculateDistance(getXCoords(component, blobs)[3], getYCoords(component, blobs)[3], getXCoords(component, blobs)[0], getYCoords(component, blobs)[0]);
        double sideOne = average(lengths[0], lengths[2]);
        double sideTwo = average(lengths[1], lengths[3]);
        if (sideOne > sideTwo) {
            return sideOne;
        } else {
            return sideTwo;
        }
    }

    public int getNumBlobs() {
        return getBlobs().size();
    }

    private ArrayList<String[]> getBlobs() {
        return data.getBlobsInFrame();
    }

    public String[] getComponent(int component) {
        if (component > getBlobs().size() - 1 || component < 0) {
            String[] error = {"not", "valid", "index"};
            return error;
        }
        return getBlobs().get(component);
    }

    public int[] getXCoords(int component, ArrayList<String[]> blobs) {
        int[] xCoords = new int[4];
        int count = 0;
        for (int x = 1; x < blobs.get(component).length; x += 2) {
            xCoords[count] = Integer.parseInt(blobs.get(component)[x]);
            count++;
        }
        return xCoords;
    }

    public int[] getYCoords(int component, ArrayList<String[]> blobs) {
        int[] yCoords = new int[4];
        int count = 0;
        for (int x = 2; x < blobs.get(component).length; x += 2) {
            yCoords[count] = Integer.parseInt(blobs.get(component)[x]);
            count++;
        }
        return yCoords;
    }

    public int[] getXCoords(int component) {
        int[] xCoords = new int[4];
        int count = 0;
        for (int x = 1; x < getComponent(component).length; x += 2) {
            xCoords[count] = Integer.parseInt(getComponent(component)[x]);
            count++;
        }
        return xCoords;
    }

    public int[] getYCoords(int component) {
        int[] yCoords = new int[4];
        int count = 0;
        for (int x = 2; x < getComponent(component).length; x += 2) {
            yCoords[count] = Integer.parseInt(getComponent(component)[x]);
            count++;
        }
        return yCoords;
    }

    public double[] getCenter(int component) {
        double[] centerCoords = new double[2];
        for (int i = 0; i < 2; i++) {
            centerCoords[i] = calculateDistance(getXCoords(component)[i], getYCoords(component)[i], getXCoords(component)[i+1], getYCoords(component)[i+1]);
        }
        return centerCoords;
    }

    public double[] getCenter(int x1, int y1, int x2, int y2, int x3, int y3) {
        double[] centerCoords = new double[2];
        centerCoords[0] = calculateDistance(x1, y1, x2, y2);
        centerCoords[1] = calculateDistance(x2, y2, x3, y3);

        return centerCoords;
    }

    public double calculateDistance(int x1, int y1, int x2, int y2) {
        int xDistance = x2 - x1;
        int yDistance = y2 - y1;
        return Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
    }

    public double average(double x, double y) {
        return (x + y) / 2;
    }
}
