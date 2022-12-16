/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.threads;

import java.util.logging.Logger;
import java.util.logging.Level;
import cataovo.entities.Palette;
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
public class ThreadCreateRelatories extends Thread {

    private static final Logger LOG = Logger.getLogger(ThreadCreateRelatories.class.getName());
    private Palette palette;
    private String savingDirectory;

    public ThreadCreateRelatories(Palette palette, String savingDirectory) {
        this.palette = palette;
        this.savingDirectory = savingDirectory;
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
        String dst = "Relatory_" + getDateTime("dd-MM-yyyy_HH-mm");
        File directory = new File(savingDirectory + "//relatories");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try (FileWriter csvWriter = new FileWriter(savingDirectory + "//relatories//" + dst + ".csv");
                PrintWriter csvPrinter = new PrintWriter(csvWriter);) {
            
            sb = createContent();
            csvPrinter.print(sb);

        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     *
     * @param palette
     * @return
     */
    private synchronized StringBuffer createContent() {
        StringBuffer sb = new StringBuffer();
        sb.append(palette.toString());
        return sb;
    }

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
