package swing;

import image.Imagen;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.SwingWorker;

import paleta.Paleta;

public class TareaCargarHGT extends SwingWorker<String, Object> {

	private Imagen hgt;
	private File f;

	public TareaCargarHGT(Imagen hgt, File f) {
		this.hgt = hgt;
		this.f = f;
	}

	@Override
	protected String doInBackground() throws Exception {
		int[][] matriz;
		BufferedImage buffImg;
		Paleta paleta = hgt.getPaleta();
		
		FileInputStream fileIn = null;
		DataInputStream dataIn = null;
		int n;
		int i = 0, fila, col;        

		long begin = System.currentTimeMillis();

		// Crear Imagen
		int size = (int) Math.sqrt(f.length()/2);
		hgt.setSize(size);
//		System.out.println(size);
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
				
				if ((fila % 30 == 0) && (col == 0)){
                	int prog = fila*100/size;
    				setProgress(prog);
				}
				
				i++;
			}
			
			hgt.setMatriz(matriz);
			hgt.setBufferedImage(buffImg);
			
			dataIn.close();
			fileIn.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}    	

		long end = System.currentTimeMillis();

//		System.out.println("Tiempo de Procesamiento: " + (end - begin));
		return "Tiempo de Procesamiento: " + (end - begin);
	}

}
