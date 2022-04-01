package lab5;

import org.w3c.dom.css.RGBColor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JImageDisplay extends JComponent {
    public BufferedImage img;
    JImageDisplay(int width, int length){
       img = new BufferedImage(width,length,BufferedImage.TYPE_INT_RGB);
       Dimension dim = new Dimension(img.getWidth(),img.getHeight());
       setPreferredSize(dim);
    }

    protected void paintComponent(Graphics g){
    g.drawImage(img,0,0,img.getWidth(),img.getHeight(),null);
    }

    public void clearImage(){
        for (int i=0;i<img.getHeight();i++){
            for (int j=0;j<img.getWidth();j++){
                img.setRGB(i,j,0);
            }
        }
    }
    public void drawPixel(int x, int y,int rgbColor){
        img.setRGB(x,y,rgbColor);
    }
}
