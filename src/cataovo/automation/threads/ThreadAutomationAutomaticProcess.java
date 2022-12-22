/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.automation.threads;

import cataovo.entities.Palette;
import cataovo.filechooser.handler.FileExtension;
import java.awt.Component;

/**
 *
 * @author bianc
 */
public class ThreadAutomationAutomaticProcess extends ThreadAutomation {

    public ThreadAutomationAutomaticProcess(Palette palette, String savingDirectory, FileExtension finalFileExtension, Component parent) {
        super(palette, savingDirectory, finalFileExtension, parent);
    }

    @Override
    protected StringBuffer createContent() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
