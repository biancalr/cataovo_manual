/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.opencvlib.imageFrameUtils;

import cataovo.entities.Frame;
import cataovo.entities.Point;
import cataovo.entities.Region;
import cataovo.opencvlib.converters.Converter;
import cataovo.opencvlib.wrappers.MatWrapper;
import cataovo.opencvlib.wrappers.PointWrapper;
import cataovo.opencvlib.wrappers.RectWrapper;
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

    public Frame getFrame() {
        return frame;
    }

    /**
     *
     * @param pw
     * @return
     */
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
        return super.rectangle(rw);
    }
    
    /**
     * 
     * @return 
     */
    public MatWrapper updateGridsOnFrame(){
        return super.updateGrids();
    }

    /**
     * 
     * @param beginGrid
     * @param endGrid
     * @return 
     */
    @Override
    protected Region captureGridSubmat(PointWrapper beginGrid, PointWrapper endGrid) {
        return super.captureGrid(beginGrid, endGrid);
    }

    /**
     * 
     * @return 
     */
    @Override
    protected MatWrapper prepareGrids() {
        return super.updateGrids();
    }
    
    /**
     * 
     * @param initialPoint
     * @param pointClick
     * @return 
     */
    public Icon captureSubframe(Point initialPoint, Point pointClick) {
        rectWrapper = new RectWrapper(captureGrid(new PointWrapper(initialPoint), new PointWrapper(pointClick)));
        matWrapper = new MatWrapper(imageUtils.captureSubmat(rectWrapper.getOpencvRect(), Converter.getInstance().convertImageFrameToMat(frame).getOpencvMat()), null);
        return new ImageIcon(Converter.getInstance().convertMatToImg(matWrapper).get());
    }

}
