/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.resources;

import cataovo.exceptions.DirectoryNotValidException;
import cataovo.fileChooserHandler.MyFileChooserUI;
import java.io.File;
import java.util.logging.Level;
import javax.swing.filechooser.FileSystemView;
import java.util.logging.Logger;

/**
 *
 * @author bibil
 */
public class MainPageResources {
    
    private static final Logger LOG = Logger.getLogger(MainPageResources.class.getName());
    private final MyFileChooserUI fileChooserUI;
    private String filePath;
    private File current;
    private File destinyFolder;
    private static volatile MainPageResources MAIN_PAGE_RESOURCES;

    public MainPageResources() throws DirectoryNotValidException {
        current = new File(FileSystemView.getFileSystemView().getHomeDirectory().getPath());
        fileChooserUI = new MyFileChooserUI(current);
        filePath = current.getPath();
        destinyFolder = getFileFolder(current);
    }
    
    public static MainPageResources getInstance() throws DirectoryNotValidException{
        MainPageResources PAGE_RESOURCES = MainPageResources.MAIN_PAGE_RESOURCES;
        if (PAGE_RESOURCES == null) {
            synchronized (MainPageResources.class){
                PAGE_RESOURCES = MainPageResources.MAIN_PAGE_RESOURCES;
                if (PAGE_RESOURCES == null) {
                    MainPageResources.MAIN_PAGE_RESOURCES = PAGE_RESOURCES = new MainPageResources();
                }
            }
        }
        return PAGE_RESOURCES;
    }

    public File getCurrent() {
        return current;
    }

    public void setCurrent(File current) {
        this.current = current;
    }

    public File getDestinyFolder() {
        return destinyFolder;
    }

    public void setDestinyFolder(File destinyFolder) throws DirectoryNotValidException {
        this.destinyFolder = getFileFolder(destinyFolder);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public MyFileChooserUI getFileChooserUI() {
        return fileChooserUI;
    }

    public final File getFileFolder(File file) throws DirectoryNotValidException {
        if (!file.exists()) {
            LOG.log(Level.SEVERE, "This method needs an existing file. The parameter cannot be null or inexistent: {0}", filePath);
            throw new DirectoryNotValidException("This method needs an existing file. The parameter cannot be null or inexistent");
        } else {
            if (file.isDirectory()) {
                return file;
            } else {
                String selected = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("\\") - 1);
                return new File(selected);
            }
        }
    }
    
}
