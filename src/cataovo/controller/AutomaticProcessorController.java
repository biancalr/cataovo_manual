/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cataovo.controller;

import javax.swing.JLabel;

/**
 *
 * @author bianc
 */
public interface AutomaticProcessorController {
    
    /**
     * Starts the automatic processing of the Palette
     * 
     * @param jLabel4
     * @param jLabel3
     * @return 
     */
    public boolean onAutoProcessPalette(JLabel jLabel4, JLabel jLabel3);
    
    /**
     * Starts the automatic processing of the Palette
     * 
     * @param jLabel4
     * @param jLabel3
     * @return 
     */
    public String onNewAutoProcessPalette(JLabel jLabel4, JLabel jLabel3);
    
}
