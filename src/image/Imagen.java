package image;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import paleta.Paleta;

public class Imagen {

    private int[][] matriz;    
    private int size;
    private Paleta paleta;
    private BufferedImage bufferedImage;

    public BufferedImage getBufferedImage() {
        return this.bufferedImage;
    }

    public Paleta getPaleta() {
        return this.paleta;
    }

    public int getSize() {
        return this.size;
    }

    public void show(JLabel label, int size) {
        // Escalar Imagen
        ImageIcon icon = new ImageIcon(this.bufferedImage);
        ImageIcon tmpIcon = new ImageIcon(icon.getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
        label.setIcon(tmpIcon);
    }

    public void repaint() {
        for (int f = 0; f < this.size; f++) {
            for (int c = 0; c < this.size; c++) {
                this.bufferedImage.setRGB(f, c, this.paleta.getColor(this.matriz[f][c]).getRGB());
            }
        }
    }

    public void setBufferedImage(BufferedImage buffImg) {
        this.bufferedImage = buffImg;
    }

    public void setInterpolar(boolean interpolar) {
        this.paleta.setInterpolar(interpolar);
    }

    public void setMatriz(int[][] matriz) {
        this.matriz = matriz;
    }

    public void setPaleta(Paleta paleta) {
        this.paleta = paleta;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
