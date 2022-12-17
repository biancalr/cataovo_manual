/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.threads;

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
public abstract class ThreadCreateRelatories extends Thread {

    private static final Logger LOG = Logger.getLogger(ThreadCreateRelatories.class.getName());
    protected Palette palette;
    protected String savingDirectory;
    protected FileExtension fileExtension;

    public ThreadCreateRelatories(Palette palette, String savingDirectory, FileExtension extension) {
        this.palette = palette;
        this.savingDirectory = savingDirectory;
        this.fileExtension = extension;
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

    private synchronized void createFile() {
        StringBuffer sb;
        String dstn = palette.getDirectory().getName() + "//" + "Relatory_" + getDateTime("dd-MM-yyyy_HH-mm");
        File directory = new File(savingDirectory + "//relatories//" + palette.getDirectory().getName());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try (FileWriter csvWriter = new FileWriter(savingDirectory + "//relatories//" + dstn + "." + this.fileExtension);
                PrintWriter csvPrinter = new PrintWriter(csvWriter);) {

            sb = createContent();
            csvPrinter.print(sb);
            LOG.log(Level.INFO, "The file will be saved under the name: {0}", dstn + "." + this.fileExtension);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
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
    public static String getDateTime(String datePattern) {
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
