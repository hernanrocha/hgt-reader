package image;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import paleta.Paleta;
import swing.DialogCargarArchivo;

public class Imagen {

    private int[][] matriz;
    private BufferedImage buffImg;
    private int size;

    private Paleta paleta;

    public void cargarHGT(File f, final DialogCargarArchivo cargando) {
        FileInputStream fileIn = null;
        DataInputStream dataIn = null;
        int n;
        int i = 0, fila, col;

        // long begin = System.currentTimeMillis();

        // Crear Imagen
        this.size = (int) Math.sqrt(f.length() / 2);
        // System.out.println(size);
        this.matriz = new int[this.size][this.size];
        this.buffImg = new BufferedImage(this.size, this.size, BufferedImage.TYPE_INT_ARGB);

        // Leer archivo
        try {
            fileIn = new FileInputStream(f);
            dataIn = new DataInputStream(fileIn);

            while (dataIn.available() > 0) {
                // Leer valor
                n = dataIn.readUnsignedShort();

                // Calcular posicion
                col = i % this.size;
                fila = (i - col) / this.size;

                // Asignar a matriz
                this.matriz[fila][col] = n;
                this.buffImg.setRGB(fila, col, this.paleta.getColor(n).getRGB());

                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // long end = System.currentTimeMillis();

        // System.out.println("Tiempo de Procesamiento: " + (end - begin));

    }

    public BufferedImage getBufferedImage() {
        return this.buffImg;
    }

    public Paleta getPaleta() {
        return this.paleta;
    }

    public int getSize() {
        return this.size;
    }

    public void mostrar(JLabel label, int size) {
        // Escalar Imagen
        ImageIcon icon = new ImageIcon(this.buffImg);
        ImageIcon tmpIcon = new ImageIcon(icon.getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
        label.setIcon(tmpIcon);
    }

    public void repaint() {
        for (int f = 0; f < this.size; f++) {
            for (int c = 0; c < this.size; c++) {
                this.buffImg.setRGB(f, c, this.paleta.getColor(this.matriz[f][c]).getRGB());
            }
        }
    }

    public void setBufferedImage(BufferedImage buffImg) {
        this.buffImg = buffImg;
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
