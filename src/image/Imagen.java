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

	public void cargarHGT(File f, final DialogCargarArchivo cargando){
		FileInputStream fileIn = null;
        DataInputStream dataIn = null;
        int n;
        int i = 0, fila, col;        

//    	long begin = System.currentTimeMillis();
    	
        // Crear Imagen
    	size = (int) Math.sqrt(f.length()/2);
//    	System.out.println(size);
    	matriz = new int[size][size];
		buffImg = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        
        // Leer archivo
    	try {
			fileIn = new FileInputStream(f);
			dataIn = new DataInputStream(fileIn);
			
            while (dataIn.available() > 0) {
            	// Leer valor
            	n = dataIn.readUnsignedShort();
            	
            	// Calcular posicion
                col = i % size;
                fila = (i - col) / size;
                
                // Asignar a matriz
                matriz[fila][col] = n;
                buffImg.setRGB(fila, col, paleta.getColor(n).getRGB());
                
                i++;
            }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}    	

//    	long end = System.currentTimeMillis();
    	
//    	System.out.println("Tiempo de Procesamiento: " + (end - begin));
	    
	}
	
	public void repaint(){
		for(int f = 0; f < size; f++){
			for (int c = 0; c < size; c++){
                buffImg.setRGB(f, c, paleta.getColor(matriz[f][c]).getRGB());
			}
		}
	}

	public void mostrar(JLabel label, int size){
        //Escalar Imagen
	    ImageIcon icon = new ImageIcon(buffImg);
        ImageIcon tmpIcon = new ImageIcon(icon.getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
    	label.setIcon(tmpIcon);
	}
	
	public Paleta getPaleta() {
		return paleta;
	}

	public void setPaleta(Paleta paleta) {
		this.paleta = paleta;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public void setInterpolar(boolean interpolar){
		this.paleta.setInterpolar(interpolar);
	}

	public void setMatriz(int[][] matriz) {
		this.matriz = matriz;
		
	}

	public void setBufferedImage(BufferedImage buffImg) {
		this.buffImg = buffImg;	
	}
	
	public BufferedImage getBufferedImage(){
		return buffImg;
	}
	
}
