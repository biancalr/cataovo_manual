/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.opencvlib.frameUtil;

import cataovo.entities.Frame;
import cataovo.entities.Point;
import cataovo.exceptions.ImageNotValidException;
import cataovo.opencvlib.wrappers.MatWrapper;

/**
 *
 * @author bibil
 */
public final class ImageFrameUtil {
    
    private static volatile ImageFrameUtil IMAGE_FRAME_UTIL;
    
    public static ImageFrameUtil getInstance() throws ImageNotValidException {
        ImageFrameUtil FRAME_UTIL = ImageFrameUtil.IMAGE_FRAME_UTIL;
        if (FRAME_UTIL == null) {
            synchronized (ImageFrameUtil.class){
                FRAME_UTIL = ImageFrameUtil.IMAGE_FRAME_UTIL;
                if (FRAME_UTIL == null) {
                    ImageFrameUtil.IMAGE_FRAME_UTIL = FRAME_UTIL = new ImageFrameUtil();
                }
            }
        }
        return FRAME_UTIL;
    }

    public Frame paintPoint(Point beginPoint, MatWrapper matWrapper) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
