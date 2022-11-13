/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.fileHandler;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author bibil
 */
public class FileFilterExtensions extends FileFilter {

    public FileExtension extension;

    public FileFilterExtensions(FileExtension extension) {
        this.extension = extension;
    }

    public FileFilterExtensions() {
        
    }

    @Override
    public boolean accept(File f) {
        return f.isDirectory() || 
                f.getAbsolutePath()
                        .endsWith("." + extension.toString().toLowerCase());
    }

    @Override
    public String getDescription() {
        return "*." + extension.toString().toLowerCase();
    }

    public void setExtension(FileExtension extension) {
        this.extension = extension;
    }

    public FileExtension getExtension() {
        return extension;
    }

}
