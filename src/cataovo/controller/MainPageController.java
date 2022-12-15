/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.controller;

import cataovo.entities.Frame;
import cataovo.entities.Point;
import cataovo.exceptions.DirectoryNotValidException;
import cataovo.exceptions.ImageNotValidException;
import javax.swing.Icon;
import javax.swing.JLabel;
/**
 *
 * @author bibil
 */
public interface MainPageController {
    
    /**
     * Set to the next frame
     *
     * @param parentName
     * @param parent
     * @throws ImageNotValidException
     * @throws DirectoryNotValidException
     */
    public void toNextFrame(JLabel parentName, JLabel parent) throws ImageNotValidException, DirectoryNotValidException;
    /**
     *
     * @param jLabel1
     * @param jLabel2
     * @param frame
     * @return
     * @throws ImageNotValidException
     */
    public boolean showFramesOnScreen(JLabel jLabel1, JLabel jLabel2, Icon frame) throws ImageNotValidException;
    
    /**
     *
     * @param parentName
     * @param parent
     * @param frame
     * @throws cataovo.exceptions.ImageNotValidException
     */
    public void showFramesOnScreen(JLabel parentName, JLabel parent, Frame frame) throws ImageNotValidException;
    
    /**
     * When a Frame was finished its analysis, go to next Frame on Queue.
     *
     * @param jLabel1
     * @param jLabel2
     * @throws cataovo.exceptions.ImageNotValidException
     * @throws cataovo.exceptions.DirectoryNotValidException
     */
    public void onFrameFinished(JLabel jLabel1, JLabel jLabel2) throws ImageNotValidException, DirectoryNotValidException;
    
    /**
     *
     * @param point
     * @return
     * @throws DirectoryNotValidException
     * @throws CloneNotSupportedException
     */
    public Icon paintFormat(Point point)throws DirectoryNotValidException, CloneNotSupportedException;
}
