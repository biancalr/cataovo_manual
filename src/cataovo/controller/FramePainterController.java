/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.controller;

import cataovo.entities.Point;
import cataovo.exceptions.DirectoryNotValidException;
import javax.swing.Icon;

/**
 *
 * @author bibil
 */
public interface FramePainterController {
    
    /**
     * Chooses the format to paint the frame whether is a dot or a rectangle
     * 
     * @param point
     * @return
     * @throws DirectoryNotValidException
     * @throws CloneNotSupportedException 
     */
    public Icon paintFormat(Point point) throws DirectoryNotValidException, CloneNotSupportedException;
    
}
