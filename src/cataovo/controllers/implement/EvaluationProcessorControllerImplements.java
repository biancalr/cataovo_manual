/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.controllers.implement;

import cataovo.automation.threads.dataEvaluation.DataEvaluationThreadAutomation;
import cataovo.automation.threads.dataEvaluation.ThreadAutomationEvaluation;
import cataovo.externals.writers.csvWriter.CsvFileWriter;
import cataovo.automation.threads.dataSaving.ThreadAutomationEvaluationProcess;
import cataovo.constants.Constants;
import cataovo.entities.Palette;
import cataovo.enums.FileExtension;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import cataovo.controllers.EvaluationProcessorController;

/**
 *
 * @author Bianca Leopoldo Ramos
 */
public class EvaluationProcessorControllerImplements implements EvaluationProcessorController {

    /**
     * Logging for CsvFileWriter
     */
    private static final Logger LOG = Logger.getLogger(EvaluationProcessorControllerImplements.class.getName());
    private String evaluationResult;

    public EvaluationProcessorControllerImplements() {

    }

    @Override
    public String onEvaluateFileContentsOnPalette(String fileContentManual, String fileContentAuto) {
        try {
            DataEvaluationThreadAutomation automation;
            Future<String> task;
            ExecutorService executorService = Executors.newSingleThreadExecutor();

            automation = new ThreadAutomationEvaluation(fileContentManual, fileContentAuto);

            task = executorService.submit(automation);
            this.evaluationResult = task.get();
            executorService.awaitTermination(1, TimeUnit.MILLISECONDS);
            executorService.shutdown();
            LOG.log(Level.INFO, "Calculating results in evaluation {0}", Arrays.toString(this.evaluationResult.split(Constants.QUEBRA_LINHA)));
            return this.evaluationResult;
        } catch (InterruptedException | ExecutionException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            return "";
        }
    }
    
    @Override
    public String getPercentageOf(final int method, final String strValue1, final String strValue2){
        int value1 = Integer.parseInt(strValue1);
        int value2 = Integer.parseInt(strValue2);
        switch (method) {
            case Constants.CALCULATE_METHOD_TRUE_POSITIVE -> {
                return "" + getPercentageTruePositive(value1, value2);
            }
            case Constants.CALCULATE_METHOD_FALSE_POSITIVE -> {
                return "" + getPercentageFalsePositive(value1, value2);
            }
            case Constants.CALCULATE_METHOD_TRUE_NEGATIVE -> {
                return "" + getPercentageTrueNegative(value1, value2);
            }
            case Constants.CALCULATE_METHOD_FALSE_NEGATIVE -> {
                return "" + getPercentageFalseNegative(value1, value2);
            }
            case Constants.CALCULATE_METHOD_PRECISION -> {
                return "" + getPercentagePrecision(value1, value2);
            }
            default -> {
                throw new AssertionError();
            }
        }
    }
    
    @Override
    public String getPercentageOf(final int method, final String strValue1, final String strValue2, final String strValue3, final String strValue4){
        int value1 = Integer.parseInt(strValue1);
        int value2 = Integer.parseInt(strValue2);
        int value3 = Integer.parseInt(strValue3);
        int value4 = Integer.parseInt(strValue4);
        switch (method) {
            case Constants.CALCULATE_METHOD_ACCURACY -> {
                return "" + getPercentageAccuracy(value1, value2, value3, value4);
            }
            default -> {
                throw new AssertionError();
            }
        }
        
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

    @Override
    public String onActionComandSaveEvaluationRelatory(final Palette palette, final String savingDirectory, final FileExtension fileExtension, final String tabName) {
        if (this.evaluationResult != null) {
            final String dateTime = getDateTime("dd-MM-yyyy_HH-mm-ss");
            try {
                String result;
                CsvFileWriter automation;
                Future<String> task;
                ExecutorService executorService = Executors.newSingleThreadExecutor();

                automation = new ThreadAutomationEvaluationProcess(
                        palette,
                        savingDirectory,
                        fileExtension,
                        tabName,
                        this.evaluationResult, 
                        dateTime);

                task = executorService.submit(automation);
                result = task.get();
                executorService.awaitTermination(1, TimeUnit.MILLISECONDS);
                executorService.shutdown();
                LOG.log(Level.INFO, "Saving evaluation data in: {0}", result);
                return result;
            } catch (InterruptedException | ExecutionException ex) {
                LOG.log(Level.SEVERE, ex.getMessage(), ex);
                return null;
            }
        } else {
            return null;
        }
    }
    
    /**
     * Calculates the date and the time.
     *
     * @param datePattern the pattern to return the date
     * @return date and time according to the the datePattern
     */
    private String getDateTime(final String datePattern) {
        DateFormat dateFormat = new SimpleDateFormat(datePattern);
        Date date = new Date();
        return dateFormat.format(date);
    }

}
