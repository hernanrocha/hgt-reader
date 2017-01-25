package swing;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.SwingWorker;

import image.Imagen;
import paleta.Paleta;

public class TareaCargarHGT extends SwingWorker<String, Object> {

    private Imagen hgtImage;
    private File file;

    public TareaCargarHGT(Imagen hgtImage, File file) {
        this.hgtImage = hgtImage;
        this.file = file;
    }

    @Override
    protected String doInBackground() throws Exception {
        int[][] matriz;
        BufferedImage buffImg;
        Paleta paleta = this.hgtImage.getPaleta();

        int i = 0;

        long begin = System.currentTimeMillis();

        // Crear Imagen
        int size = (int) Math.sqrt(this.file.length() / 2);
        this.hgtImage.setSize(size);
        System.out.println(size);
        matriz = new int[size][size];
        buffImg = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

        // Leer archivo
        try {
            FileInputStream fileStream = new FileInputStream(this.file);
            DataInputStream dataStream = new DataInputStream(fileStream);

            while (dataStream.available() > 0) {
                // Leer valor
                int n = dataStream.readUnsignedShort();

                // Calcular posicion
                int col = i % size;
                int fila = (i - col) / size;

                // Asignar a matriz
                matriz[fila][col] = n;
                buffImg.setRGB(fila, col, paleta.getColor(n).getRGB());

                // Setear progreso
                if ((fila % 30 == 0) && (col == 0)) {
                    int prog = fila * 100 / size;
                    setProgress(prog);
                }

                i++;
            }

            this.hgtImage.setMatriz(matriz);
            this.hgtImage.setBufferedImage(buffImg);

            dataStream.close();
            fileStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        // System.out.println("Tiempo de Procesamiento: " + (end - begin));
        return "Tiempo de Procesamiento: " + (end - begin);
    }

}
