/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.controllers;

import cataovo.entities.Point;
import cataovo.exceptions.DirectoryNotValidException;
import cataovo.exceptions.ImageNotValidException;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JLabel;
/**
 * Controls how the frames are demonstrated on screen and how to move from one to the other
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
     * @param tabComponent
     * @param parentNameLabel
     * @param parentLabel 
     * @param frame 
     * @throws cataovo.exceptions.ImageNotValidException 
     * @throws cataovo.exceptions.DirectoryNotValidException 
     */
    public void showFramesOnSelectedTabScreen(Component tabComponent, JLabel parentNameLabel, JLabel parentLabel, Object frame) throws ImageNotValidException, DirectoryNotValidException;

    /**
     * 
     * @param subframeNameLabel
     * @param subframeLabel
     * @param frame 
     * @param point 
     * @throws cataovo.exceptions.DirectoryNotValidException 
     */
    public void showSubFrameOnSelectedTabScreen(JLabel subframeNameLabel, JLabel subframeLabel, Icon frame, Point point) throws DirectoryNotValidException;


}
