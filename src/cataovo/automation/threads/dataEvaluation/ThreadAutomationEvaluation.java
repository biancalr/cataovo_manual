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
    protected float[] evaluateFrame(String regionsLine, String eggsLine) throws NumberFormatException {
        int tp = 0;
        int tn = 0;
        int fp = 0;
        int fn = 0;
        float[] metrics = new float[4];
        List<String> eggs;
        double percentual = 0;

        //Separar a as regioes pela vírgula
        List<Region> regions = split(Constants.RECT_FORMAT, regionsLine.split(Constants.SEPARATOR));
        //Separa as áreas dos ovos pela cerquilha
        eggs = new CopyOnWriteArrayList<>(List.of(eggsLine.split(Constants.OBJECT_SEPARATOR)));
        // Posição zero contém apenas nome e a quantidade de ovos
        eggs.remove(0);
        
        // Caso não haja ovos detectados pelo automático, a linha não será splitada
        if (eggs.size() >= 1) {
            
            // Começar a partir do 1 pois essa posição não contém coordenadas de pontos
            List<Point> points;
            for (String eggLine : eggs) {
                
                // Separar os pontos do ovo pela vírgula
                points = split(Constants.CIRCLE_FORMAT, eggLine.split(Constants.SEPARATOR));
                List<Region> regionsAux = regions;
                Region rect;
                boolean eggFound;

                LOG.log(Level.INFO, "Total of points {0}", points.size());
                // Comparação começa aqui
                // Percorrendo as marcações
                for (int i = 0; i < regionsAux.size(); i++) {
                    rect = regionsAux.get(i);
                    //percorrendo os pontos
                    for (Point point : points) {
                        // se a região contém o ponto
                        if (rect.contains(point)) {
                            LOG.log(Level.INFO, "The point {0} was found in a region", point);
                            // acrescenta na quantidade de partes encontradas
                            percentual++;
                            LOG.log(Level.INFO, "Points found in {0}: {1}", new Object[]{rect, point});
                        }
                    }
                    LOG.log(Level.INFO, "Total de ovos encontrados no frame pelo automático: {0}", eggs.size());
                    LOG.log(Level.INFO, "Pontos encontrados dentro da região {0}", percentual);
                    LOG.log(Level.INFO, "Percentual para o ovo {0}", (percentual / points.size()));
                    if ((percentual > 0.0) && ((percentual / points.size()) > 0.8)) {
                        // se foram encontrados os pontos na região demarcada
                        // acrescenta os verdadeiros positivos
                        tp++;
                        eggFound = true;
                        LOG.log(Level.INFO, "The region has found matching points : {0}", eggFound);
                        // remove a região a qual os pontos foram encontrados
                        regions.remove(rect);
                        eggs.remove(eggLine);
                        
                    }
                    percentual = 0;
                }

            }
            
            // Caso tenha sobrado regiões que não foram detectados ovos ou foram detectadas incorretamente
            fn = regions.size();
            // Caso tenha sobrado ovos que não foram detectados ou foram detectados incorretamente
            // Reduzir 1 do pois a posição 1 não contém coordenadas de pontos
            fp = eggs.size();
            
            LOG.log(Level.INFO, "Regiões remanescentes {0}", regions.size());
            LOG.log(Level.INFO, "Ovos remanescentes {0}", eggs.size());

        } else {
            // Incrementa os falsos negativos caso o automático não tenha encontrado ovos
            fn = regions.size();
        }

        metrics[0] = tp;
        metrics[1] = fn;
        metrics[2] = fp;
        metrics[3] = tn;
        return metrics;
    }

    private List split(int ofFormat, String[] data) throws NumberFormatException {
        int jumpStep;
        int atStartPoint;
        switch (ofFormat) {
            case Constants.RECT_FORMAT -> {
                jumpStep = 4;
                atStartPoint = 1;
            }
            case Constants.CIRCLE_FORMAT -> {
                jumpStep = 2;
                atStartPoint = 0;
            }
            default ->
                throw new AssertionError();
        }
        return iterateOver(data, ofFormat, atStartPoint, jumpStep);

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
                new Point(Integer.parseInt(data[ofPosition + 2]) > 0 ? (Integer.parseInt(data[ofPosition]) - Integer.parseInt(data[ofPosition + 2])) : Integer.parseInt(data[ofPosition]),
                        Integer.parseInt(data[ofPosition + 3]) > 0 ? (Integer.parseInt(data[ofPosition + 1]) - Integer.parseInt(data[ofPosition + 3])) : Integer.parseInt(data[ofPosition + 1])));

    }

}
