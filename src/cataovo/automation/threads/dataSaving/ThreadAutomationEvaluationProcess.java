/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.automation.threads.dataSaving;

import cataovo.externals.writers.csvWriter.CsvFileWriter;
import cataovo.constants.Constants;
import cataovo.entities.Palette;
import cataovo.enums.FileExtension;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bianca Leopoldo Ramos
 */
public class ThreadAutomationEvaluationProcess extends CsvFileWriter {

    private static final Logger LOG = Logger.getLogger(ThreadAutomationEvaluationProcess.class.getName());
    private String evaluationResult;

    public ThreadAutomationEvaluationProcess(Palette palette, String savingDirectory, FileExtension fileExtension, String parentTabName, String evaluationResult, String dateTime) {
        super(palette, savingDirectory, fileExtension, parentTabName, dateTime);
        this.evaluationResult = evaluationResult;
    }

    @Override
    protected StringBuffer createContent() {
//        evaluation[0]); // true positive
//        evaluation[2]); // false Positive
//        evaluation[1]); // false negative
//        evaluation[3]); // true negative

        String[] resultLineSplitted = this.evaluationResult.split(Constants.QUEBRA_LINHA);
        StringBuffer sb = new StringBuffer(getPalette().getDirectory().getPath());
        sb.append(Constants.QUEBRA_LINHA);
        sb.append("Verdadeiro Positivo: ").append(resultLineSplitted[0]).append(Constants.QUEBRA_LINHA);
        sb.append("Falso Positivo: ").append(resultLineSplitted[2]).append(Constants.QUEBRA_LINHA);
        sb.append("Falso Negativo: ").append(resultLineSplitted[1]).append(Constants.QUEBRA_LINHA);
        sb.append("Verdadeiro Negativo: ").append(resultLineSplitted[3]).append(Constants.QUEBRA_LINHA);
        sb.append(Constants.QUEBRA_LINHA);
        sb.append("Taxa de Verdadeiro Positivo: ").append(getPercentageOf(Constants.CALCULATE_METHOD_TRUE_POSITIVE, resultLineSplitted[1], resultLineSplitted[0])).append("%").append(Constants.QUEBRA_LINHA);
        sb.append("Taxa de Falso Positivo: ").append(getPercentageOf(Constants.CALCULATE_METHOD_FALSE_POSITIVE , resultLineSplitted[2], resultLineSplitted[3])).append("%").append(Constants.QUEBRA_LINHA);
        sb.append("Taxa de Falso Negativo: ").append(getPercentageOf(Constants.CALCULATE_METHOD_FALSE_NEGATIVE, resultLineSplitted[3], resultLineSplitted[2])).append("%").append(Constants.QUEBRA_LINHA);
        sb.append("Taxa de Verdadeiro Negativo: ").append(getPercentageOf(Constants.CALCULATE_METHOD_TRUE_NEGATIVE, resultLineSplitted[1], resultLineSplitted[0])).append("%").append(Constants.QUEBRA_LINHA);
        sb.append("Taxa de Acurácia: ").append(getPercentageOf(Constants.CALCULATE_METHOD_ACCURACY, resultLineSplitted[0], resultLineSplitted[3], resultLineSplitted[2], resultLineSplitted[1])).append("%").append(Constants.QUEBRA_LINHA);
        sb.append("Taxa de Precisão: ").append(getPercentageOf(Constants.CALCULATE_METHOD_PRECISION, resultLineSplitted[0], resultLineSplitted[2])).append("%").append(Constants.QUEBRA_LINHA);

        return sb;
    }
    
    private int getPercentageOf(int method, String strValue1, String strValue2){
        int value1 = Integer.parseInt(strValue1);
        int value2 = Integer.parseInt(strValue2);
        switch (method) {
            case Constants.CALCULATE_METHOD_TRUE_POSITIVE -> {
                return getPercentageTruePositive(value1, value2);
            }
            case Constants.CALCULATE_METHOD_FALSE_POSITIVE -> {
                return getPercentageFalsePositive(value1, value2);
            }
            case Constants.CALCULATE_METHOD_TRUE_NEGATIVE -> {
                return getPercentageTrueNegative(value1, value2);
            }
            case Constants.CALCULATE_METHOD_FALSE_NEGATIVE -> {
                return getPercentageFalseNegative(value1, value2);
            }
            case Constants.CALCULATE_METHOD_PRECISION -> {
                return getPercentagePrecision(value1, value2);
            }
            default -> {
                throw new AssertionError();
            }
        }
    }
    
    private int getPercentageOf(int method, String strValue1, String strValue2, String strValue3, String strValue4){
        int value1 = Integer.parseInt(strValue1);
        int value2 = Integer.parseInt(strValue2);
        int value3 = Integer.parseInt(strValue3);
        int value4 = Integer.parseInt(strValue4);
        switch (method) {
            case Constants.CALCULATE_METHOD_ACCURACY -> {
                return getPercentageAccuracy(value1, value2, value3, value4);
            }
            default -> {
                throw new AssertionError();
            }
        }
        
    }

    private int getPercentageTruePositive(int falseNegative, int truePositive) {
        // TVP = VP / (FN+VP)
        LOG.log(Level.INFO, "Calculating Percentage: True Positive");
        return truePositive / (falseNegative + truePositive);
    }

    private int getPercentageFalsePositive(int falsePositive, int trueNegative) {
        //TFP = FP / (VN+FP)
        LOG.log(Level.INFO, "Calculating Percentage: False Positive");
        return falsePositive / (trueNegative + falsePositive);
    }

    private int getPercentageTrueNegative(int trueNegative, int falsePositive) {
        // TVN = VN / (VN+FP)
        LOG.log(Level.INFO, "Calculating Percentage: True Negative");
        return trueNegative / (trueNegative + falsePositive);
    }

    private int getPercentageFalseNegative(int falseNegative, int truePositive) {
        // TFN = FN / (FN+VP)
        LOG.log(Level.INFO, "Calculating Percentage: False Negative");
        return falseNegative / (falseNegative + truePositive);
    }

    private int getPercentageAccuracy(int truePositive, int trueNegative, int falsePositive, int falseNegative) {
        // Acc = (VP+VN) / (VP+VN+FP+FN)
        LOG.log(Level.INFO, "Calculating Percentage: Accuracy");
        return (truePositive + trueNegative) / (truePositive + trueNegative + falsePositive + falseNegative);
    }

    private int getPercentagePrecision(int truePositive, int falsePositive) {
        // Prec = VP / (VP+FP)
        LOG.log(Level.INFO, "Calculating Percentage: Precision");
        return truePositive / (truePositive + falsePositive);
    }

}
