/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.filechooser.handler;

import cataovo.exceptions.ImageNotValidException;
import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author bibil
 * @param <T>
 */
public final class MyFileListHandler<T> {

    Collection<T> fileList;
    private static final Logger LOG = Logger.getLogger(MyFileListHandler.class.getName());

    public MyFileListHandler(Collection<T> fileList) {
        this.fileList = fileList;
    }

    public MyFileListHandler() {
    }

    /**
     *
     * @param file array of files. Onlu PNG Files.
     * @param fileExtension the supported FileExtesion
     * @return
     * @throws ImageNotValidException
     */
    public Collection<T> normalizeFilesOnAList(File[] file, FileExtension fileExtension) throws ImageNotValidException {
        fileList = new LinkedList<>();
        for (File file1 : file) {
            if (file1.exists()&& file1.isFile() && getFileExtension(file1).equalsIgnoreCase(fileExtension.toString().toLowerCase())) {
                fileList.add((T) file1);
                LOG.log(Level.INFO, "Adding following file: {0}", file1.getName());
            } else if (file1.isDirectory()) {
                normalizeFilesOnAList(file1.listFiles(), fileExtension);
            }
        }
        return fileList;
    }

    /**
     *
     * @param f the File
     * @return the file Extension
     */
    public final String getFileExtension(File f) {
        String fileExtension;
        // Verify which is the file extension.
        if (f.getAbsolutePath().substring(f.getAbsolutePath().indexOf(".")) != null) {
            fileExtension = f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf(".") + 1);
        } else {
            fileExtension = "NONE";
        }
        return fileExtension;
    }

    public Collection<T> getFileList() {
        return fileList;
    }

    public void setFileList(Collection<T> fileList) {
        this.fileList = fileList;
    }

}
