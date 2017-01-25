package paleta;

import java.awt.Color;
import java.io.File;
import java.util.Vector;

public abstract class Paleta {

    protected Vector<Color> colores = new Vector<Color>();
    protected Vector<Integer> valores = new Vector<Integer>();
    protected boolean interpolar = true;

    public Paleta(boolean interpolar) {
        this.interpolar = interpolar;
    }

    // Agregar un par (Color - limite)
    public void addColor(Color c, Integer limit) {

        if (this.valores.indexOf(limit) != -1) {
            modifyColor(this.valores.indexOf(limit), c);
            return;
        }

        int i = 0;
        int tam = this.valores.size();

        while (i < tam && limit > this.valores.get(i)) {
            i++;
        }

        this.colores.add(i, c);
        this.valores.add(i, limit);

    }

    public Color getColor(int v) {
        // Si es menor al primer color de la paleta
        if (v < this.valores.firstElement())
            return this.colores.firstElement();

        for (int i = 1; i < this.valores.size(); i++) {
            if (v < this.valores.get(i)) {
                if (this.interpolar) {

                    Color cAnt = this.colores.get(i - 1);
                    Color cAct = this.colores.get(i);

                    int vAnt = this.valores.get(i - 1);
                    int vAct = this.valores.get(i);

                    int difValores = vAct - vAnt;
                    int difRed = cAct.getRed() - cAnt.getRed();
                    int difGreen = cAct.getGreen() - cAnt.getGreen();
                    int difBlue = cAct.getBlue() - cAnt.getBlue();

                    int r = cAnt.getRed() + (v - vAnt) * (difRed) / (difValores);
                    int g = cAnt.getGreen() + (v - vAnt) * (difGreen) / (difValores);
                    int b = cAnt.getBlue() + (v - vAnt) * (difBlue) / (difValores);

                    return new Color(r, g, b);

                } else
                    return this.colores.get(i);
            }
        }

        return this.colores.lastElement();

    }

    public Vector<Color> getColores() {
        return this.colores;
    }

    public Vector<Integer> getValores() {
        return this.valores;
    }

    // Modificar el color de un indice particular
    public void modifyColor(int indice, Color c) {
        // System.out.println("Modificar");
        this.colores.remove(indice);
        this.colores.add(indice, c);
    }

    public void removeAll() {
        this.colores = new Vector<Color>();
        this.valores = new Vector<Integer>();
    }

    // Borrar una entrada dado el limite
    public void removeColor(Integer limit) {
        int i = this.valores.indexOf(limit);

        if (i >= 0) {
            this.colores.removeElementAt(i);
            this.valores.removeElementAt(i);
        }

    }

    // Borrar entrada dado el indice de la misma
    public void removeIndex(Integer index) {
        if (index >= 0) {
            // System.out.println("Eliminar " + index);
            this.colores.removeElementAt(index);
            this.valores.removeElementAt(index);
        }
    }

    public abstract void reset();

    public abstract void save(File f);

    public void setInterpolar(boolean interpolar) {
        this.interpolar = interpolar;
    }
}
