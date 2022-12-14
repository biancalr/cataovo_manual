/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.opencvlib.imageFrameUtils;

import cataovo.entities.Frame;
import cataovo.opencvlib.converters.Converter;
import cataovo.opencvlib.wrappers.MatWrapper;
import cataovo.opencvlib.wrappers.PointWrapper;
import cataovo.opencvlib.wrappers.RectWrapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;


/**
 *
 * @author bibil
 */
public class FrameUtils {
    
    private static final Logger LOG = Logger.getLogger(FrameUtils.class.getName());
    private PointWrapper pointWrapper = null;
    private RectWrapper rectWrapper = null;
    private MatWrapper matWrapper = null;

    public FrameUtils(Frame frame) {
        pointWrapper = new PointWrapper();
        rectWrapper = new RectWrapper();
        matWrapper = new MatWrapper();
    }

    public FrameUtils() {
        pointWrapper = new PointWrapper();
        rectWrapper = new RectWrapper();
        matWrapper = new MatWrapper();
    }

    public PointWrapper getPointWrapper() {
        return pointWrapper;
    }

    public void setPointWrapper(PointWrapper pointWrapper) {
        this.pointWrapper = pointWrapper;
    }

    public RectWrapper getRectWrapper() {
        return rectWrapper;
    }

    public void setRectWrapper(RectWrapper rectWrapper) {
        this.rectWrapper = rectWrapper;
    }

    public MatWrapper getMatWrapper() {
        return matWrapper;
    }

    public void setMatWrapper(MatWrapper matWrapper) {
        this.matWrapper = matWrapper;
    }

    public Icon drawCircle(PointWrapper pw, Frame frame) {
        LOG.log(Level.INFO, "Starting..");
        MatWrapper wrapper = Converter.getInstance().convertImageFrameToMat(frame);
        wrapper.setMat(ImageUtils.getInstance().circle(pw.getOpencvPoint(), wrapper.getMat()).clone());
        return new ImageIcon(Converter.getInstance().convertMatToImg(wrapper).get());
    }
    
    
}
