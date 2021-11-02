
package diu;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class canvast extends JPanel{
    
    private BufferedImage imagen = null; 
    private String imageFile = null;
    private EstadisticasImagen ei;
    
    private int rmax;
    private int rmin;
    private int ra;
    
    private int gmax;
    private int gmin;
    private int ga;
    
    private int bmax;
    private int bmin;
    private int ba;
    
    public canvast(){
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (imagen == null){
            g.drawString("No image has been selected", this.getWidth()/2-80, this.getHeight()/2);
        }else{
            g.drawImage(imagen, 0, 0, null);
        }
    }
    
    public void setImage(File file){
        try {
            imagen = ImageIO.read(file);
            imageFile = file.getAbsolutePath();
        } catch (IOException ex) {
            Logger.getLogger(canvast.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setPreferredSize(new Dimension(imagen.getWidth(), imagen.getHeight()));
        repaint();
    }

    public int getRmax() {
        return rmax;
    }

    public int getRmin() {
        return rmin;
    }

    public int getRa() {
        return ra;
    }

    public int getGmax() {
        return gmax;
    }

    public int getGmin() {
        return gmin;
    }

    public int getGa() {
        return ga;
    }

    public int getBmax() {
        return bmax;
    }

    public int getBmin() {
        return bmin;
    }

    public int getBa() {
        return ba;
    }
    
    private void setAllRGBValues(){
        int[] maxs = ei.getMaximo();
        int[] mins = ei.getMinimo();
        int[] pro = ei.getPromedio();
        this.rmax = maxs[0];
        this.rmin = mins[0];
        this.ra = pro[0];
        
        this.gmax = maxs[1];
        this.gmin = mins[1];
        this.ga = pro[1];
    
        this.bmax = maxs[2];
        this.bmin = mins[2];
        this.ba = pro[2];
    }

    void setSubMatriz(Point viewPosition, Dimension extentSize) {
        if(imageFile != null){
            ei = new EstadisticasImagen();
            Mat currentMat = Imgcodecs.imread(imageFile);
            ei.calculaEstadisticas(currentMat, viewPosition, extentSize);
            setAllRGBValues();
        }
    }
    

}
