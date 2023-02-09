/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.entities;

import cataovo.exceptions.ImageNotValidException;
import cataovo.resources.fileChooser.handler.FileExtension;
import java.io.File;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A frame represents a fraction of the various images that make up a palette.
 *
 * @author bibil
 */
public final class Frame implements Cloneable, Serializable{

    /**
     * the frame identifier name
     */
    private String name;

    /**
     * The list of the regions wich indicates an egg of mosquito. A demarked
     * region must contain an egg in it
     */
    private Set<Region> regionsContainingEggs;

    /**
     * The file of the frame;
     */
    private File paletteFrame;

    /**
     *
     * @param filePath the file path of this image
     * @throws cataovo.exceptions.ImageNotValidException
     */
    public Frame(String filePath) throws ImageNotValidException {
        if (verifyFileIsAValidImage(filePath)) {
            this.paletteFrame = new File(filePath);
            this.name = chopName(filePath);
            if (this.regionsContainingEggs == null) {
                this.regionsContainingEggs = new HashSet<>();
            }
        }

    }

    /**
     *
     * @param filePath
     * @param region the area containg an egg.
     * @throws cataovo.exceptions.ImageNotValidException
     */
    public Frame(String filePath, Region region) throws ImageNotValidException {
        if (verifyFileIsAValidImage(filePath)) {
            this.paletteFrame = new File(filePath);
            this.name = chopName(filePath);
            if (this.regionsContainingEggs == null) {
                this.regionsContainingEggs = new HashSet<>();
            }
            this.regionsContainingEggs.add(region);
        }
    }

    /**
     * 
     * @param name
     * @param paletteFrame
     * @throws ImageNotValidException 
     */
    public Frame(String name, File paletteFrame) throws ImageNotValidException {
        if (verifyFileIsAValidImage(name)) {
            this.paletteFrame = paletteFrame;
            this.name = chopName(name);
            if (this.regionsContainingEggs == null) {
                this.regionsContainingEggs = new HashSet<>();
            }
        }
    }

    /**
     * Empty Constructor
     */
    public Frame() {
        this.regionsContainingEggs = new HashSet<>();
        this.name = "";
    }

    /**
     *
     * @return the frame identifier name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name a frame identifier name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the regions with eggs in it
     */
    public Set<Region> getRegionsContainingEggs() {
        return regionsContainingEggs;
    }

    /**
     *
     * @param regionsContainingEggs regions with eggs in it.
     */
    public void setRegionsContainingEggs(Set<Region> regionsContainingEggs) {
        this.regionsContainingEggs = regionsContainingEggs;
    }

    /**
     *
     * @return the palette frame File
     */
    public File getPaletteFrame() {
        return paletteFrame;
    }

    /**
     *
     * @param paletteFrame the palette frame to set
     */
    public void setPaletteFrame(File paletteFrame) {
        this.paletteFrame = paletteFrame;
    }

    /**
     * Calculates the total number of eggs in a frame.
     *
     * @return the quantity of eggs in a single frame if the list is not null or
     * Empty. 0 otherwise.
     */
    public int getTotalNumberOfEggsFrame() {
        if (this.regionsContainingEggs == null || regionsContainingEggs.isEmpty()) {
            return 0;
        } else {
            return regionsContainingEggs.size();
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(name)
                .append(",")
                .append(regionsContainingEggs.toString());
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Frame frame = (Frame) obj;
        return Objects.equals(frame.name, name)
                && Objects.equals(frame.regionsContainingEggs, regionsContainingEggs);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.name);
        hash = 11 * hash + Objects.hashCode(this.regionsContainingEggs);
        return hash;
    }

    /**
     *
     * @param pathName
     * @return true if the image is valid. False otherwise.
     * @throws ImageNotValidException if the image does not exists, is not a
     * file or is not a PNG image file.
     */
    private boolean verifyFileIsAValidImage(String pathName) throws ImageNotValidException {
        File f = new File(pathName);
        String fileExtension;

        fileExtension = getFileExtension(f);
        // Verify if this file exists, is a file and is a .png file type
        if (f.exists() && f.isFile() && (fileExtension.contains(FileExtension.PNG.toString().toLowerCase()) || fileExtension.contains(FileExtension.JPG.toString().toLowerCase()))) {
            return true;
        } else {
            throw new ImageNotValidException("This file with extension " +
                    " does not represent a valid image, wich should be " + fileExtension);
        }
    }

    /**
     *
     * @param f the File
     * @return the file Extension
     */
    private String getFileExtension(File f) {
        String fileExtension;
        // Verify which is the file extension. Only png is valid.
        if (f.getAbsolutePath().substring(f.getAbsolutePath().indexOf(".")) != null) {
            fileExtension = f.getAbsolutePath().substring(f.getAbsolutePath().indexOf(".") + 1);
        } else {
            fileExtension = "NONE";
        }
        return fileExtension;
    }

    /**
     * Chopps the name to extract the image name.
     *
     * @param filePath the file path to extract the name tag
     * @return the name tag chopped
     */
    private String chopName(String filePath) {
        return filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.lastIndexOf("."));
    }

    @Override
    public Frame clone() throws CloneNotSupportedException {
        return (Frame) super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

}
