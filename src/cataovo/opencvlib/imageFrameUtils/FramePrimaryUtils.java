/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.opencvlib.imageFrameUtils;

import cataovo.entities.Frame;
import cataovo.entities.Point;
import cataovo.opencvlib.converters.Converter;
import cataovo.opencvlib.wrappers.PointWrapper;
import cataovo.opencvlib.wrappers.RectWrapper;
import java.util.logging.Level;
import javax.swing.Icon;
import javax.swing.ImageIcon;


/**
 *
 * @author bibil
 */
public class FramePrimaryUtils extends FrameUtils{

    public FramePrimaryUtils(Frame frame) {
        super(frame);
    }
    
    public FramePrimaryUtils() {
        super();
    }

    @Override
    public Icon drawCircle(PointWrapper pw) {
        return super.dot(pw);
    }

    /**
     *
     * @param rw
     * @return
     */
    @Override
    public Icon drawRectangle(RectWrapper rw) {
        return super.square(rw);
    }
    
}
