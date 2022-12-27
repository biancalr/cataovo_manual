/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.automation.threads.opencvlib.automaticImageProcess;

import cataovo.entities.Point;
import cataovo.opencvlib.converters.Converter;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.MatOfPoint;

/**
 *
 * @author bianc
 */
public class AutomaticFrameArchiveProcess {

    private int totalOfElements;
    private List<MatOfPoint> contours;

    public AutomaticFrameArchiveProcess(int totalOfElements, List<MatOfPoint> contours) {
        this.totalOfElements = totalOfElements;
        this.contours = contours;
    }

    public String getMainPointsInEgg() {
        StringBuffer buffer = new StringBuffer();
        List<Point> auxlist;
        List<Point> mainPoints = new ArrayList<>();
        for (int i = 0; i < contours.size(); i++) {
            MatOfPoint get = contours.get(i);

            auxlist = Converter.getInstance().convertMatOfPointToList(get);
            System.out.println("auxlist.size: " + auxlist.size());
            for (int j = 0; j < auxlist.size(); j++) {

                if (j == (auxlist.size() * 0.25)
                        || j == (auxlist.size() * 0.50)
                        || j == (auxlist.size() * 0.75)
                        || j == (auxlist.size() - 1)) {

                    mainPoints.add(auxlist.get(j));
                }

            }

        }
        System.out.println("mainPoints.size: " + mainPoints.size());
        mainPoints.stream().forEach((p) -> {
            buffer.append(",");
            buffer.append(p.getX());
            buffer.append(",");
            buffer.append(p.getY());
        });

        return buffer.toString();
    }

    public int getTotalOfEggs() {
        return totalOfElements;
    }

}
