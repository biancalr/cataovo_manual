/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.automation.threads.dataSaving;

import cataovo.constants.Constants;
import cataovo.entities.Palette;
import cataovo.resources.fileChooser.handler.FileExtension;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bianca Leopoldo Ramos
 */
public class ThreadAutomationEvaluationProcess extends DataSavingThreadAutomation {

    private static final Logger LOG = Logger.getLogger(ThreadAutomationEvaluationProcess.class.getName());
    private int[] evaluationResult;

    public ThreadAutomationEvaluationProcess(Palette palette, String savingDirectory, FileExtension fileExtension, String parentTabName, int[] evaluationResult, String dateTime) {
        super(palette, savingDirectory, fileExtension, parentTabName, dateTime);
        this.evaluationResult = evaluationResult;
    }

    public int[] getEvaluationResult() {
        return evaluationResult;
    }

    public void setEvaluationResult(int[] evaluationResult) {
        this.evaluationResult = evaluationResult;
    }

    @Override
    protected StringBuffer createContent() {
//        evaluation[0]); // true positive
//        evaluation[2]); // false Positive
//        evaluation[1]); // false negative
//        evaluation[3]); // true negative

        StringBuffer sb = new StringBuffer(getPalette().getDirectory().getPath());
        sb.append(Constants.QUEBRA_LINHA);
        sb.append("Verdadeiro Positivo: ").append(this.evaluationResult[0]).append(Constants.QUEBRA_LINHA);
        sb.append("Falso Positivo: ").append(this.evaluationResult[2]).append(Constants.QUEBRA_LINHA);
        sb.append("Falso Negativo: ").append(this.evaluationResult[1]).append(Constants.QUEBRA_LINHA);
        sb.append("Verdadeiro Negativo: ").append(this.evaluationResult[3]).append(Constants.QUEBRA_LINHA);
        sb.append(Constants.QUEBRA_LINHA);
        sb.append("Taxa de Verdadeiro Positivo: ").append(getPercentageTruePositive(this.evaluationResult[1], this.evaluationResult[0])).append("%").append(Constants.QUEBRA_LINHA);
        sb.append("Taxa de Falso Positivo: ").append(getPercentageFalsePositive(evaluationResult[2], evaluationResult[3])).append("%").append(Constants.QUEBRA_LINHA);
        sb.append("Taxa de Falso Negativo: ").append(getPercentageFalseNegative(evaluationResult[3], evaluationResult[2])).append("%").append(Constants.QUEBRA_LINHA);
        sb.append("Taxa de Verdadeiro Negativo: ").append(getPercentageTrueNegative(evaluationResult[1], evaluationResult[0])).append("%").append(Constants.QUEBRA_LINHA);
        sb.append("Taxa de Acurácia: ").append(getPercentageAccuracy(evaluationResult[0], evaluationResult[3], evaluationResult[2], evaluationResult[1])).append("%").append(Constants.QUEBRA_LINHA);
        sb.append("Taxa de Precisão: ").append(getPercentagePrecision(evaluationResult[0], evaluationResult[2])).append("%").append(Constants.QUEBRA_LINHA);

        return sb;
    }

    public int getPercentageTruePositive(int falseNegative, int truePositive) {
        // TVP = VP / (FN+VP)
        LOG.log(Level.INFO, "Calculating Percentage: True Positive");
        return truePositive / (falseNegative + truePositive);
    }

    public int getPercentageFalsePositive(int falsePositive, int trueNegative) {
        //TFP = FP / (VN+FP)
        LOG.log(Level.INFO, "Calculating Percentage: False Positive");
        return falsePositive / (trueNegative + falsePositive);
    }

    public int getPercentageTrueNegative(int trueNegative, int falsePositive) {
        // TVN = VN / (VN+FP)
        LOG.log(Level.INFO, "Calculating Percentage: True Negative");
        return trueNegative / (trueNegative + falsePositive);
    }

    public int getPercentageFalseNegative(int falseNegative, int truePositive) {
        // TFN = FN / (FN+VP)
        LOG.log(Level.INFO, "Calculating Percentage: False Negative");
        return falseNegative / (falseNegative + truePositive);
    }

    public int getPercentageAccuracy(int truePositive, int trueNegative, int falsePositive, int falseNegative) {
        // Acc = (VP+VN) / (VP+VN+FP+FN)
        LOG.log(Level.INFO, "Calculating Percentage: Accuracy");
        return (truePositive + trueNegative) / (truePositive + trueNegative + falsePositive + falseNegative);
    }

    public int getPercentagePrecision(int truePositive, int falsePositive) {
        // Prec = VP / (VP+FP)
        LOG.log(Level.INFO, "Calculating Percentage: Precision");
        return truePositive / (truePositive + falsePositive);
    }

}
