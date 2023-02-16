/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cataovo.controllers;

import cataovo.entities.Palette;
import cataovo.resources.fileChooser.handler.FileExtension;

/**
 *
 * @author bianc
 */
public interface EvaluationController {

    /**
     * 
     * @param fileContentManual
     * @param fileContentAuto
     * @return 
     */
    public int[] onEvaluateFileContentsOnPalette(String fileContentManual, String fileContentAuto);
    
    /**
     * 
     * @param falseNegative
     * @param truePositive
     * @return 
     */
    public int getPercentageTruePositive(int falseNegative, int truePositive);
    
    /**
     * 
     * @param falsePositive
     * @param trueNegative
     * @return 
     */
    public int getPercentageFalsePositive(int falsePositive, int trueNegative);
    
    /**
     * 
     * @param trueNegative
     * @param falsePositive
     * @return 
     */
    public int getPercentageTrueNegative(int trueNegative, int falsePositive);
    
    /**
     * 
     * @param falseNegative
     * @param truePositive
     * @return 
     */
    public int getPercentageFalseNegative(int falseNegative, int truePositive);
    
    /**
     * 
     * @param truePositive
     * @param trueNegative
     * @param falsePositive
     * @param falseNegative
     * @return 
     */
    public int getPercentageAccuracy(int truePositive, int trueNegative, int falsePositive, int falseNegative);
    
    /**
     * 
     * @param truePositive
     * @param falsePositive
     * @return 
     */
    public int getPercentagePrecision(int truePositive, int falsePositive);

    /**
     * 
     * @param palette
     * @param savingDirectory
     * @param fileExtension
     * @param parentTabName
     * @return 
     */
    public String onActionComandSaveEvaluationRelatory(Palette palette, String savingDirectory, FileExtension fileExtension, String parentTabName);
    
}
