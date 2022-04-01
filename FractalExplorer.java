package lab5;

import lab5.JImageDisplay;
import org.w3c.dom.css.RGBColor;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.ComboBoxUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;

public class FractalExplorer {
private int displaySize;
private JImageDisplay imageDisplay ;
private Rectangle2D.Double rect = new Rectangle2D.Double();
private Mandelbrot mandelbrot = new Mandelbrot();
private FractalGenerator fractalGenerator = mandelbrot;
private Tricorn tricorn = new Tricorn();
private  BurningShip burningShip= new BurningShip();
private JFrame jfrm = new JFrame("Fractal Explorer");

FractalExplorer(int displaySize){
    this.displaySize = displaySize;
    fractalGenerator.getInitialRange(rect);
    imageDisplay = new JImageDisplay(displaySize,displaySize);
 }

 public void createAndShowGUI(){

     jfrm.setSize(displaySize,displaySize);
     JButton resetbtn = new JButton();
     JButton saveBtn = new JButton();
     ActionListener saveBtnListener = new SaveBtnListener();
     saveBtn.addActionListener(saveBtnListener);
     JPanel panelWithBtn = new JPanel();
     panelWithBtn.add(resetbtn);
     panelWithBtn.add(saveBtn);
     saveBtn.setText("Save image");
     resetbtn.setText("Reset Display");
     ActionListener btnListener = new BtnActionListener();
     resetbtn.addActionListener(btnListener);
     panelWithBtn.setBackground(Color.orange);
     resetbtn.setFocusPainted(false);
     jfrm.add(imageDisplay, BorderLayout.CENTER);
     jfrm.add(panelWithBtn, BorderLayout.SOUTH);
     MouseListener msListener = new MouseListener();
     jfrm.addMouseListener(msListener);
     jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     jfrm.pack();
     JPanel jPanel = new JPanel();
     JLabel jLabel = new JLabel();
     jLabel.setText("Select fractal type: ");
     jPanel.setBackground(Color.ORANGE);
     JComboBox jComboBox = new JComboBox();
     jComboBox.addItem(mandelbrot);
     jComboBox.addItem(burningShip);
     jComboBox.addItem(tricorn);
     jPanel.add(jLabel);
     jPanel.add(jComboBox);
     jfrm.add(jPanel,BorderLayout.NORTH);
     ActionListener comboBoxListener = new ComboBoxListener();
     jComboBox.addActionListener(comboBoxListener);
     jfrm.setVisible(true);
     jfrm.setResizable(false);
     jfrm.setLocationRelativeTo(null);
 }

public class BtnActionListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        imageDisplay.clearImage();
        fractalGenerator.getInitialRange(rect);
        imageDisplay.repaint();
        drawFractal();
    }
}

public class SaveBtnListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images","png");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.showSaveDialog(jfrm);
        try {
        ImageIO.write(imageDisplay.img,"PNG", fileChooser.getSelectedFile());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(jfrm,ex.getMessage(),"Error saving",JOptionPane.ERROR_MESSAGE);
        };
    }
}
public class ComboBoxListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox box = (JComboBox)e.getSource();
        fractalGenerator = (FractalGenerator)box.getSelectedItem();
        fractalGenerator.getInitialRange(rect);
        imageDisplay.repaint();
        drawFractal();
    }
}
public class MouseListener extends MouseAdapter implements java.awt.event.MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
       Point point = e.getPoint();
       double x = FractalGenerator.getCoord(rect.x,rect.x+rect.width,displaySize,point.x);
       double y = FractalGenerator.getCoord(rect.y,rect.y+rect.height,displaySize,point.y);
       fractalGenerator.recenterAndZoomRange(rect,x,y,0.5);
       drawFractal();
    }
}


 public void drawFractal(){
     int rgbColor;
     for (int i=0;i<displaySize;i++){
         for (int j=0;j<displaySize;j++){
             double xCoord = FractalGenerator.getCoord(rect.x,rect.x+rect.width,displaySize,i);
             double yCoord = FractalGenerator.getCoord(rect.y,rect.y+rect.height,displaySize,j);
            int color = fractalGenerator.numIterations(xCoord,yCoord);
            if (color==-1) rgbColor = 0;
            else {
                float hue = 0.7f + (float) color / 200f;
                rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
            }
             imageDisplay.drawPixel(i,j,rgbColor);
         }
     }
     imageDisplay.repaint();
 }
}

