/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cataovo.controllers;

import cataovo.entities.Palette;
import cataovo.exceptions.AutomationExecutionException;
import cataovo.exceptions.DirectoryNotValidException;
import javax.swing.JLabel;

/**
 * Controls the automatic processment.
 *
 * @author Bianca Leopoldo Ramos
 */
public interface AutomaticProcessorController {
       
    /**
     * Starts the automatic processing of the {@link cataovo.entities.Palette} as a {@link java.util.concurrent.Callable}.
     * 
     * @param jLabelImageName
     * @param jLabelImageFrame
     * @param currentPalette
     * @param savingFolderPath
     * @param tabName
     * @return the final relatory's path
     * @throws cataovo.exceptions.DirectoryNotValidException
     * @throws cataovo.exceptions.AutomationExecutionException
     */
    public String onNewAutoProcessPalette(JLabel jLabelImageName, JLabel jLabelImageFrame, Palette currentPalette, String savingFolderPath, String tabName) throws DirectoryNotValidException, AutomationExecutionException;
    
}
