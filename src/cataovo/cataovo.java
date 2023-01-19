/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cataovo;

import cataovo.entities.Frame;
import cataovo.entities.Palette;
import org.opencv.core.Core;
import cataovo.entities.Point;
import cataovo.entities.Region;
import cataovo.exceptions.DirectoryNotValidException;
import cataovo.exceptions.ImageNotValidException;
import cataovo.opencvlib.wrappers.PointWrapper;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Just testing some stuf.
 *
 * @author Bianca Leopoldo Ramos
 */
public class cataovo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            
            System.out.println("Realizando testes com as entidades");
            Point p = new Point(1, 2);
            Region r = new Region(3, 4, p);
            System.out.println(p.toString());
            System.out.println(r.toString());
            
            Frame f = new Frame("frame", r);
            f.getRegionsContainingEggs().add(r);
            Palette t = new Palette("palette", f);
            t.getFrames().add(f);
            System.out.println(f.toString());
            System.out.println(t.toString());
            
            PointWrapper pw = new PointWrapper();
            System.out.println(pw.dot(p));
        } catch (ImageNotValidException | DirectoryNotValidException ex) {
            Logger.getLogger(cataovo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
}
