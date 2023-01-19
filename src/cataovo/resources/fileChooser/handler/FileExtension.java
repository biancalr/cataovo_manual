/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.resources.fileChooser.handler;

/**
 *
 * @author bibil
 */
public enum FileExtension {
    
    PNG(1), CSV(2), JPG(3);

    /**
     * The value of the extension to work with.
     */
    private int extension;
    
    /**
     * 
     * @param extension the value of the extension to work with
     */
    private FileExtension(int extension) {
        this.extension = extension;
    }

    /**
     * 
     * @return the extension selected to work with
     */
    public int getExtension() {
        return extension;
    }

    /**
     * 
     * @param extension 
     */
    public void setExtension(int extension) {
        this.extension = extension;
    }
    
}
