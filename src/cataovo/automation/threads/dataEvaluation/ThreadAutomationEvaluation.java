/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.automation.threads.dataEvaluation;

import cataovo.constants.Constants;
import cataovo.entities.Point;
import cataovo.entities.Region;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bianca Leopoldo Ramos
 */
public class ThreadAutomationEvaluation extends DataEvaluationThreadAutomation {

    private static final Logger LOG = Logger.getLogger(ThreadAutomationEvaluation.class.getName());

    public ThreadAutomationEvaluation(String fileContentManual, String fileContentAuto) {
        super(fileContentManual, fileContentAuto);
    }

    @Override
    protected int[] evaluateFrame(String regionsLine, String pointsLine) throws NumberFormatException {
        int tp = 0;
        int tn = 0;
        int fp = 0;
        int fn = 0;
        //Separar a as regioes pela vírgula
        List<Region> regions = split(Constants.RECT_FORMAT, regionsLine.split(","));
        List<Region> regionsAux = regions;
        // Separar os pontos pela vírgula
        List<Point> points = split(Constants.CIRCLE_FORMAT, pointsLine.split(","));
        List<Point> pointsAux = points;
        Region rect;
        int part;
        boolean eggFound;
        int[] metrics = new int[4];

        // Algoritmo começa aqui
        // Percorrendo as marcações
        for (int i = 0; i < regionsAux.size(); i++) {
            rect = regionsAux.get(i);
            part = 0;
            //percorrendo os pontos
            for (Point point : pointsAux) {
                // se a região contém um ponto
                if (rect.contains(point)) {
                    LOG.log(Level.INFO, "The point {0} was found in a region", point);
                    // remover o ponto
//                    points.remove(point);
                    // acrescenta na quantidade de partes encontradas
                    part++;
                    LOG.log(Level.INFO, "Points found in {0}: {1}", new Object[]{rect, part});
                    break;
                }
            }
            if (part > 0) {
                // se foram encontrados os pontos na região demarcada
                // acrescenta os verdadeiros positivos
                tp++;
                eggFound = true;
                LOG.log(Level.INFO, "The region has found matching points : {0}", eggFound);
                // remove a região a qual os pontos foram encontrados
                regions.remove(rect);
            } else {
                // se foram encontrados os pontos na região demarcada
                eggFound = false;
                LOG.log(Level.INFO, "Nao casou um contorno com a marcacao {0}", eggFound);
                // acrescenta a quantidade de falsos negativos
                fn++;
            }

        }

        LOG.log(Level.INFO, "Regiões remanescentes {0}", regions.size());
        LOG.log(Level.INFO, "Pontos remanescentes {0}", points.size());

        fp = points.size() / 78;

        metrics[0] = tp;
        metrics[1] = fn;
        metrics[2] = fp;
        metrics[3] = tn;
        return metrics;
    }

    private List split(int ofFormat, String[] data) throws NumberFormatException {
        int jumpStep;
        int atStartPoint;
        List formatList;
        switch (ofFormat) {
            case Constants.RECT_FORMAT -> {
                jumpStep = 4;
                atStartPoint = 1;
            }
            case Constants.CIRCLE_FORMAT -> {
                jumpStep = 2;
                atStartPoint = 2;
            }
            default ->
                throw new AssertionError();
        }
        formatList = iterateOver(data, ofFormat, atStartPoint, jumpStep);
        return formatList;

    }

    private List iterateOver(String[] data, int ofFormat, int atStartPoint, int jumpStep) throws NumberFormatException {
        List formatList = new CopyOnWriteArrayList<>();
        for (int i = atStartPoint; i < data.length; i += jumpStep) {
            if (data[i] != null && !data[i].isBlank()) {
                switch (ofFormat) {
                    case Constants.RECT_FORMAT ->
                        formatList.add(addRegion(data, i));
                    case Constants.CIRCLE_FORMAT ->
                        formatList.add(addPoint(data, i));
                    default ->
                        throw new AssertionError();
                }

            }
        }
        return formatList;
    }

    /**
     * Converts a line of string data into {@link cataovo.entities.Point points}
     *
     * @param data
     * @return the list of points
     * @throws NumberFormatException
     */
    private Point addPoint(String[] data, int ofPosition) throws NumberFormatException {
        return new Point(
                Integer.parseInt(data[ofPosition].replace(".0", "").trim()),
                Integer.parseInt(data[ofPosition + 1].replace(".0", "").trim()));

    }

    /**
     * Converts a line of string data into
     * {@link cataovo.entities.Region regions}
     *
     * @param data
     * @return the list of regions
     * @throws NumberFormatException
     */
    private Region addRegion(String[] data, int ofPosition) throws NumberFormatException {
        return new Region(
                //Acrescentando correção em caso de valores negativos
                Integer.parseInt(data[ofPosition + 3]) > 0 ? Integer.parseInt(data[ofPosition + 3]) : Math.abs(Integer.parseInt(data[ofPosition + 3])),
                Integer.parseInt(data[ofPosition + 2]) > 0 ? Integer.parseInt(data[ofPosition + 2]) : Math.abs(Integer.parseInt(data[ofPosition + 2])),
                new Point(Integer.parseInt(data[ofPosition + 3]) > 0 ? (Integer.parseInt(data[ofPosition]) - Integer.parseInt(data[ofPosition + 3])) : Integer.parseInt(data[ofPosition]),
                        Integer.parseInt(data[ofPosition + 1]) > 0 ? (Integer.parseInt(data[ofPosition + 1]) - Integer.parseInt(data[ofPosition + 2])) : Integer.parseInt(data[ofPosition + 1])));

    }

}
