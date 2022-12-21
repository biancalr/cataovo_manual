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
public interface AutomaticProcessController {

    /**
     * Automation to process the Palette
     * 
     * @param labelName
     * @param labelImage
     * @return 
     */
    public boolean isOnProcessPalette(JLabel labelName, JLabel labelImage);
    
}
