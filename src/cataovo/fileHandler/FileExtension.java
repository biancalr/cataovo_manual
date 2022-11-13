/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.fileHandler;

/**
 *
 * @author bibil
 */
public enum FileExtension {
    
    PNG(1), CSV(2);

    private int extension;
    
    private FileExtension(int extension) {
        this.extension = extension;
    }

    public int getExtension() {
        return extension;
    }

    public void setExtension(int extension) {
        this.extension = extension;
    }
    
}
