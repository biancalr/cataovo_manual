/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.entities;

import cataovo.exceptions.DirectoryNotValidException;
import java.io.File;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * Representation of an ovitrap palette, composed of several frames.
 *
 * @author bibil
 */
public class Palette{

    /**
     * The path where the palette is localized.
     */
    private String pathName;
    /**
     * The frames that composes a palette.
     */
    private Queue<Frame> frames;

    /**
     * The the directory as a file;
     */
    private File directory;
    
    /**
     *
     * @param pathName The path where the palette is localized. <strong>The file
     * has to be a directory</strong>
     * @param frames The frames that composes a palette.
     * @throws cataovo.exceptions.DirectoryNotValidException if the file is not
     * a directory.
     */
    public Palette(String pathName, Queue<Frame> frames) throws DirectoryNotValidException {
        if (verifyPathIsDirectory(pathName)) {
            this.frames = frames;
            this.pathName = pathName;
            this.directory = new File(pathName);
        } else {
            throw new DirectoryNotValidException("This file path does not represent a valid directory. Must choose another one");
        }

    }

    /**
     *
     * @param pathName The path where the palette is localized. <strong>The file
     * has to be a directory</strong>
     * @throws cataovo.exceptions.DirectoryNotValidException if the file is not
     * a directory.
     */
    public Palette(String pathName) throws DirectoryNotValidException {
        if (verifyPathIsDirectory(pathName)) {
            this.pathName = pathName;
            this.frames = new LinkedList<>();
            this.directory = new File(pathName);
        } else {
            throw new DirectoryNotValidException("This file path does not represent a valid directory. Must choose another one");
        }
    }
    
    /**
     *
     * @param file
     * @throws cataovo.exceptions.DirectoryNotValidException if the file is not
     * a directory.
     */
    public Palette(File file) throws DirectoryNotValidException {
        if (verifyPathIsDirectory(file.getAbsolutePath())) {
            this.pathName = file.getAbsolutePath();
            this.frames = new LinkedList<>();
            this.directory = new File(file.getAbsolutePath());
        } else {
            throw new DirectoryNotValidException("This file path does not represent a valid directory. Must choose another one");
        }
    }

    /**
     *
     * @param frames
     * @param directory
     * @throws DirectoryNotValidException if the file is not a directory.
     */
    public Palette(Queue<Frame> frames, File directory) throws DirectoryNotValidException {
        if (verifyPathIsDirectory(directory.getAbsolutePath())) {
            this.frames = frames;
            this.pathName = directory.getAbsolutePath();
            this.directory = directory;
        } else {
            throw new DirectoryNotValidException("This file path does not represent a valid directory. Must choose another one");
        }

    }

    /**
     *
     * @param pathName The path where the palette is localized.
     * @param frame a frames to compose a palette.
     * @throws cataovo.exceptions.DirectoryNotValidException
     */
    public Palette(String pathName, Frame frame) throws DirectoryNotValidException {
        if (!verifyPathIsDirectory(pathName)) {
            throw new DirectoryNotValidException("This file path does not represent a valid directory. Must choose another one");
        } else {
            this.pathName = pathName;
            this.directory = new File(pathName);
            if (this.frames == null) {
                this.frames = new LinkedList<>();
            }
            this.frames.offer(frame);
        }

    }

    /**
     * Empty constructor
     */
    public Palette() {
        directory = null;
        if (this.frames == null) {
            this.frames = new LinkedList<>();
        }
    }

    /**
     *
     * @return The path where the palette is localized.
     */
    public String getPathName() {
        return pathName;
    }

    /**
     *
     * @param pathName a path where a palette is localized.
     */
    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    /**
     *
     * @return The frames that composes a palette.
     */
    public Queue<Frame> getFrames() {
        return frames;
    }

    /**
     *
     * @param frames some frames that composes a palette.
     */
    public void setFrames(Queue<Frame> frames) {
        this.frames = frames;
    }

    /**
     *
     * @return the directory
     */
    public File getDirectory() {
        return directory;
    }

    /**
     *
     * @param directory the directory to be setted
     */
    public void setDirectory(File directory) {
        this.directory = directory;
    }

    /**
     * Calculates the total quantity of eggs in a pallete.
     *
     * @return the total number of eggs in a Palette
     */
    public int getTheTotalNumberOfEggsPalette() {
        int total = 0;
        if (frames == null || frames.isEmpty()) {
            total = 0;
        } else {
            total = frames.stream().map((frame) -> frame.getTotalNumberOfEggsFrame()).reduce(total, Integer::sum);
        }
        return total;
    }
    
    /**
     * 
     * @return 
     */
    public int getPaletteSize(){
        if (frames != null || !frames.isEmpty()) {
            return frames.size();
        } 
        return 0;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(pathName)
                .append(getTheTotalNumberOfEggsPalette())
                .append(",")
                .append(frames);
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

        Palette pal = (Palette) obj;
        return Objects.equals(pathName, pal.pathName)
                && Objects.equals(frames, pal.frames);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.pathName);
        hash = 97 * hash + Objects.hashCode(this.frames);
        return hash;
    }

    /**
     *
     * @param pathName
     * @return
     */
    private boolean verifyPathIsDirectory(String pathName) {
        File f = new File(pathName);
        return f.exists() && f.isDirectory();
    }

}
