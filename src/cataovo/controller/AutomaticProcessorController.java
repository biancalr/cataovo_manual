/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cataovo.controller;

import javax.swing.JLabel;

/**
 * Controls the automatic processment.
 *
 * @author Bianca Leopoldo Ramos
 */
public interface AutomaticProcessorController {
    
    /**
     * Starts the automatic processing of the {@link cataovo.entities.Palette} as a {@link Thread}.
     * 
     * @param jLabelImageName
     * @param jLabelImageFrame
     * @return the final relatory's path
     */
    public boolean onAutoProcessPalette(JLabel jLabelImageName, JLabel jLabelImageFrame);
    
    /**
     * Starts the automatic processing of the {@link cataovo.entities.Palette} as a {@link java.util.concurrent.Callable}.
     * 
     * @param jLabelImageName
     * @param jLabelImageFrame
     * @return the final relatory's path
     */
    public String onNewAutoProcessPalette(JLabel jLabelImageName, JLabel jLabelImageFrame);
    
}
