package org.rivierarobotics.robot;

import edu.wpi.first.wpilibj.SerialPort;

import java.util.ArrayList;

public class JevoisDataParsing {
    private final SerialPort port;
    private String data;
    private ArrayList<String> lines;

    public JevoisDataParsing() {
        port = new SerialPort(115200, SerialPort.Port.kUSB1);
        data = "";
        lines = new ArrayList<String>();
    }

    private void getLines() {
        //if(port.setTimeout(.033))
        if (port.getBytesReceived() > 0) {
            data += port.readString();
        }

        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == '\r') {
                data.substring(i, data.length() - 1);
                break;
            }
        }
        int startLine = 0;
        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == '\r') {
                lines.add(data.substring(startLine, i));
                startLine = i + 1;
            }
        }
    }

    public ArrayList<String[]> getBlobsInFrame() {
        getLines();
        ArrayList<String[]> blobs = new ArrayList<String[]>();
        int frameNumb = Integer.parseInt(lines.get(0).split(" ")[0]);
        for (int i = 0; i < lines.size(); i++) {
            if (Integer.parseInt(lines.get(i).split(" ")[0]) == frameNumb) {
                blobs.add(refineData(lines.get(i)));
            } else {
                break;
            }
        }

        return blobs;
    }

    private String[] refineData(String l) {
        String[] rawData = l.split(" ");
        String[] refinedData = new String[9];
        refinedData[0] = rawData[0];
        for(int i = 4; i < rawData.length; i++) {
            refinedData[i - 4] = rawData[i];
        }
        return refinedData;
    }
}
