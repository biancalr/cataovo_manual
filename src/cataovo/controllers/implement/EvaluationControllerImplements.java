/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.controllers.implement;

import cataovo.automation.threads.dataEvaluation.DataEvaluationThreadAutomation;
import cataovo.automation.threads.dataEvaluation.ThreadAutomationEvaluation;
import cataovo.automation.threads.dataSaving.DataSavingThreadAutomation;
import cataovo.automation.threads.dataSaving.ThreadAutomationEvaluationProcess;
import cataovo.controllers.EvaluationController;
import cataovo.entities.Palette;
import cataovo.resources.fileChooser.handler.FileExtension;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bianca Leopoldo Ramos
 */
public class EvaluationControllerImplements implements EvaluationController {

    /**
     * Logging for DataSavingThreadAutomation
     */
    private static final Logger LOG = Logger.getLogger(EvaluationControllerImplements.class.getName());
    private int[] evaluationResult;

    public EvaluationControllerImplements() {

    }

    @Override
    public int[] onEvaluateFileContentsOnPalette(String fileContentManual, String fileContentAuto) {
        try {
            this.evaluationResult = new int[4];
            DataEvaluationThreadAutomation automation;
            Future<int[]> task;
            ExecutorService executorService = Executors.newSingleThreadExecutor();

            automation = new ThreadAutomationEvaluation(fileContentManual, fileContentAuto);

            task = executorService.submit(automation);
            this.evaluationResult = task.get();
            executorService.awaitTermination(1, TimeUnit.MILLISECONDS);
            executorService.shutdown();
            LOG.log(Level.INFO, "Calculating results in evaluation {0}", Arrays.toString(this.evaluationResult));
            return this.evaluationResult;
        } catch (InterruptedException | ExecutionException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            return new int[0];
        }
    }

    /**
     *
     * @return
     */
    public int[] getEvaluationResult() {
        return evaluationResult;
    }

    /**
     *
     * @param evaluationResult
     */
    public void setEvaluationResult(int[] evaluationResult) {
        this.evaluationResult = evaluationResult;
    }

    @Override
    public int getPercentageTruePositive(int falseNegative, int truePositive) {
        // TVP = VP / (FN+VP)
        LOG.log(Level.INFO, "Calculating Percentage: True Positive");
        return truePositive / (falseNegative + truePositive);
    }

    @Override
    public int getPercentageFalsePositive(int falsePositive, int trueNegative) {
        //TFP = FP / (VN+FP)
        LOG.log(Level.INFO, "Calculating Percentage: False Positive");
        return falsePositive / (trueNegative + falsePositive);
    }

    @Override
    public int getPercentageTrueNegative(int trueNegative, int falsePositive) {
        // TVN = VN / (VN+FP)
        LOG.log(Level.INFO, "Calculating Percentage: True Negative");
        return trueNegative / (trueNegative + falsePositive);
    }

    @Override
    public int getPercentageFalseNegative(int falseNegative, int truePositive) {
        // TFN = FN / (FN+VP)
        LOG.log(Level.INFO, "Calculating Percentage: False Negative");
        return falseNegative / (falseNegative + truePositive);
    }

    @Override
    public int getPercentageAccuracy(int truePositive, int trueNegative, int falsePositive, int falseNegative) {
        // Acc = (VP+VN) / (VP+VN+FP+FN)
        LOG.log(Level.INFO, "Calculating Percentage: Accuracy");
        return (truePositive + trueNegative) / (truePositive + trueNegative + falsePositive + falseNegative);
    }

    @Override
    public int getPercentagePrecision(int truePositive, int falsePositive) {
        // Prec = VP / (VP+FP)
        LOG.log(Level.INFO, "Calculating Percentage: Precision");
        return truePositive / (truePositive + falsePositive);
    }

    @Override
    public String onActionComandSaveEvaluationRelatory(Palette palette, String savingDirectory, FileExtension fileExtension, String parentTabName) {
        if (this.evaluationResult != null) {
            try {
                String result;
                DataSavingThreadAutomation automation;
                Future<String> task;
                ExecutorService executorService = Executors.newSingleThreadExecutor();

                automation = new ThreadAutomationEvaluationProcess(
                        palette,
                        savingDirectory,
                        fileExtension,
                        parentTabName,
                        this.evaluationResult);

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

}
