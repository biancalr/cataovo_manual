/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.automation.threads.dataEvaluation;

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

    private int tp, tn, fp, fn;

    public ThreadAutomationEvaluation(String fileContentManual, String fileContentAuto) {
        super(fileContentManual, fileContentAuto);
        this.tp = this.tn = this.fp = this.fn = 0;
    }

    public int getTp() {
        return tp;
    }

    public void setTp(int tp) {
        this.tp = tp;
    }

    public int getTn() {
        return tn;
    }

    public void setTn(int tn) {
        this.tn = tn;
    }

    public int getFp() {
        return fp;
    }

    public void setFp(int fp) {
        this.fp = fp;
    }

    public int getFn() {
        return fn;
    }

    public void setFn(int fn) {
        this.fn = fn;
    }

    @Override
    protected int[] evaluateFrame(String regionsLine, String pointsLine) throws NumberFormatException {
        this.tp = this.tn = this.fp = this.fn = 0;
        //Separar a as regioes pela vírgula
        List<Region> regions = splitRegions(regionsLine.split(","));
        List<Region> regionsAux = regions;
        // Separar os pontos pela vírgula
        List<Point> points = splitPoints(pointsLine.split(","));
        List<Point> pointsAux = points;
        Region rect;
        int part;
        boolean eggFound = false;
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
                LOG.log(Level.INFO, "N\u00e3o casou um contorno com a marca\u00e7\u00e3o {0}", eggFound);
                // acrescenta a quantidade de falsos negativos
                this.fn++;
            }

        }

        LOG.log(Level.INFO, "Regiões remanescentes {0}", regions.size());
        LOG.log(Level.INFO, "Pontos remanescentes {0}", points.size());

        this.fp = points.size() / 78;

        metrics[0] = this.tp;
        metrics[1] = this.fn;
        metrics[2] = this.fp;
        metrics[3] = this.tn;
        return metrics;
    }

    /**
     * Converts a line of string data into {@link cataovo.entities.Point points}
     *
     * @param data
     * @return the list of points
     * @throws NumberFormatException
     */
    private List<Point> splitPoints(String[] data) throws NumberFormatException {
        List<Point> points = new CopyOnWriteArrayList<>();
        //Separar a string pela vírgula
        // transformar os pontos de String para Lista
        // ignorar a posição 1 e a 2 as quais representam o nome do frame e o total de ovos contidos
        for (int i = 2; i < data.length; i += 2) {
            if (data[i] != null && !data[i].isBlank()) {
                points.add(new Point(
                        Integer.parseInt(data[i].replace(".0", "").trim()),
                        Integer.parseInt(data[i + 1].replace(".0", "").trim())));
            }
        }
        return points;
    }

    /**
     * Converts a line of string data into
     * {@link cataovo.entities.Region regions}
     *
     * @param data
     * @return the list of regions
     * @throws NumberFormatException
     */
    private List<Region> splitRegions(String[] data) throws NumberFormatException {
        List<Region> regions = new CopyOnWriteArrayList<>();
        // transformar as regioes de String para Lista
        // ignorar a posição 1 a qual representa o nome do frame
        for (int i = 1; i < data.length; i += 4) {
            if (data[i] != null && !data[i].isBlank()) {
                regions.add(new Region(
                        //Acrescentando correção em caso de valores negativos
                        Integer.parseInt(data[i + 3]) > 0 ? Integer.parseInt(data[i + 3]) : Math.abs(Integer.parseInt(data[i + 3])),
                        Integer.parseInt(data[i + 2]) > 0 ? Integer.parseInt(data[i + 2]) : Math.abs(Integer.parseInt(data[i + 2])),
                        new Point(Integer.parseInt(data[i + 3]) > 0 ? (Integer.parseInt(data[i]) - Integer.parseInt(data[i + 3])) : Integer.parseInt(data[i]),
                                Integer.parseInt(data[i + 1]) > 0 ? (Integer.parseInt(data[i + 1]) - Integer.parseInt(data[i + 2])) : Integer.parseInt(data[i + 1]))));
            }
        }
        return regions;
    }

}
