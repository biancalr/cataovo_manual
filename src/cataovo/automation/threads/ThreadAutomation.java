/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.automation.threads;

import cataovo.constants.Constants;
import java.util.logging.Logger;
import java.util.logging.Level;
import cataovo.entities.Palette;
import cataovo.filechooser.handler.FileExtension;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author bibil
 */
public abstract class ThreadAutomation extends Thread {

    private static final Logger LOG = Logger.getLogger(ThreadAutomation.class.getName());
    protected Palette palette;
    protected String savingDirectory;
    protected FileExtension fileExtension;
    private final String parent;

    public ThreadAutomation(Palette palette, String savingDirectory, FileExtension extension, String parentName) {
        this.palette = palette;
        this.savingDirectory = savingDirectory;
        this.fileExtension = extension;
        this.parent = parentName;
    }

    @Override
    public void run() {
        super.run();
        try {
            createFile();
            sleep(100);
        } catch (InterruptedException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    private synchronized boolean createFile() {
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
            return true;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return false;
    }

    /**
     *
     * @return the content of the file;
     */
    protected abstract StringBuffer createContent(); 

    /**
     * Calculates the date and the time.
     *
     * @param datePattern the pattern to return the date
     * @return date and time depending on the datePattern
     */
    public String getDateTime(String datePattern) {
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

}
