/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.automation.threads.dataEvaluation;

import cataovo.constants.Constants;
import cataovo.exceptions.ReportNotValidException;
import java.util.concurrent.Callable;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author Bianca Leopoldo Ramos
 */
public abstract class DataEvaluationThreadAutomation implements Callable<int[]> {

    /**
     * Logging for DataSavingThreadAutomation
     */
    private static final Logger LOG = Logger.getLogger(cataovo.automation.threads.dataSaving.DataSavingThreadAutomation.class.getName());

    private String fileContentManual;
    private String fileContentAuto;
    private int[] evaluationResult;

    /**
     *
     * @param fileContentManual
     * @param fileContentAuto
     */
    public DataEvaluationThreadAutomation(String fileContentManual, String fileContentAuto) {
        this.fileContentManual = fileContentManual;
        this.fileContentAuto = fileContentAuto;
        this.evaluationResult = new int[4];
        this.evaluationResult[0] = 0;
        this.evaluationResult[1] = 0;
        this.evaluationResult[2] = 0;
        this.evaluationResult[3] = 0;
    }

    @Override
    public int[] call() throws NumberFormatException, ReportNotValidException {
        return evaluationAnalysis();
    }

    /**
     *
     * @param regionsInFrame
     * @param pointsInFrame
     * @return
     */
    protected abstract int[] evaluateFrame(String regionsInFrame, String pointsInFrame) throws NumberFormatException;

    /**
     *
     * @return
     */
    private synchronized int[] evaluationAnalysis() throws NumberFormatException, ReportNotValidException{
        LOG.log(Level.INFO, "Starting Evaluation...");

        //split both strings
        String[] regionsOnFrame = this.fileContentManual.split(Constants.QUEBRA_LINHA);
        String[] pointsOnFrame = this.fileContentAuto.split(Constants.QUEBRA_LINHA);

        if (regionsOnFrame.length != pointsOnFrame.length) {
            throw new ReportNotValidException("Os relatórios não têm a mesma quatidade de Frames");
        }

        for (int i = 0; i < regionsOnFrame.length; i++) {
            int[] frameResult = evaluateFrame(regionsOnFrame[i], pointsOnFrame[i]);
            this.evaluationResult[0] += frameResult[0];
            this.evaluationResult[1] += frameResult[1];
            this.evaluationResult[2] += frameResult[2];
            this.evaluationResult[3] += frameResult[3];
        }

        return this.evaluationResult;
    }

    public String getFileContentManual() {
        return fileContentManual;
    }

    public void setFileContentManual(String fileContentManual) {
        this.fileContentManual = fileContentManual;
    }

    public String getFileContentAuto() {
        return fileContentAuto;
    }

    public void setFileContentAuto(String fileContentAuto) {
        this.fileContentAuto = fileContentAuto;
    }

    public int[] getEvaluationResult() {
        return evaluationResult;
    }

}
