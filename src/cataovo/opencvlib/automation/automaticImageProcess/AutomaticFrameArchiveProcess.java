/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.opencvlib.automation.automaticImageProcess;

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

                // Saves a point each five steps
                if ((j % 50) == 0){
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
