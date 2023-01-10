/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.automation.threads.callable;

import cataovo.constants.Constants;
import cataovo.entities.Palette;
import cataovo.filechooser.handler.FileExtension;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author bianc
 */
public abstract class NewThreadAutomation implements Callable<String> {

    private static final Logger LOG = Logger.getLogger(NewThreadAutomation.class.getName());
    protected Palette palette;
    protected String savingDirectory;
    protected FileExtension fileExtension;
    private final String parent;

    public NewThreadAutomation(Palette palette, String savingDirectory, FileExtension fileExtension, String parent) {
        this.palette = palette;
        this.savingDirectory = savingDirectory;
        this.fileExtension = fileExtension;
        this.parent = parent;
    }
    
    @Override
    public String call() throws Exception {
        return createFile();
    }
    
    /**
     * Responsible for creating the contents of each folder needed to save the final products
     *
     * @return the content of the file;
     */
    protected abstract StringBuffer createContent(); 
    
    private synchronized String createFile() {
        StringBuffer sb = new StringBuffer();
        String processingMode = (this.parent == null ? Constants.TAB_NAME_MANUAL == null : this.parent.equals(Constants.TAB_NAME_MANUAL)) ? "manual" : (this.parent == null ? Constants.TAB_NAME_AUTOMATICO == null : this.parent.equals(Constants.TAB_NAME_AUTOMATICO))? "auto" : "result";
        String dstn = palette.getDirectory().getName() + "/" + processingMode + "/" + getDateTime("dd-MM-yyyy_HH-mm") + "/" + "Relatory";
        File directory = new File(savingDirectory + "/cataovo/" + palette.getDirectory().getName() + "/" + processingMode + "/" + getDateTime("dd-MM-yyyy_HH-mm"));
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try (FileWriter csvWriter = new FileWriter(savingDirectory + "/cataovo/" + dstn + "." + this.fileExtension.toString().toLowerCase());
                PrintWriter csvPrinter = new PrintWriter(csvWriter);) {

            sb.append(createContent());
            csvPrinter.print(sb);
            LOG.log(Level.INFO, "The file will be saved under the name: {0}", savingDirectory + "/cataovo/" + dstn + "." + this.fileExtension.toString().toLowerCase());
            return directory.getPath();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return "";
    }

    /**
     * Calculates the date and the time.
     *
     * @param datePattern the pattern to return the date
     * @return date and time depending on the datePattern
     */
    protected String getDateTime(String datePattern) {
        DateFormat dateFormat = new SimpleDateFormat(datePattern);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public Palette getPalette() {
        return palette;
    }

    public void setPalette(Palette palette) {
        this.palette = palette;
    }

    public String getSavingDirectory() {
        return savingDirectory;
    }

    public void setSavingDirectory(String savingDirectory) {
        this.savingDirectory = savingDirectory;
    }

    public FileExtension getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(FileExtension fileExtension) {
        this.fileExtension = fileExtension;
    }
    
    
    
}
