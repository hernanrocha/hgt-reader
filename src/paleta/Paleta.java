package paleta;

import java.awt.Color;
import java.io.File;
import java.util.Vector;

public abstract class Paleta {
	
	protected Vector<Color> colores = new Vector<Color>();	
	protected Vector<Integer> valores = new Vector<Integer>();
	protected boolean interpolar = true;
	
	public void setInterpolar(boolean interpolar) {
		this.interpolar = interpolar;
	}

	public Paleta(boolean interpolar) {		
		this.interpolar = interpolar;
	}
	
	// Agregar un par (Color - limite)
	public void addColor(Color c, Integer limit){
		
		if(valores.indexOf(limit) != -1){
			modifyColor(valores.indexOf(limit), c);
			return;
		}

		int i = 0;
		int tam = valores.size();
		
		while (i < tam && limit > valores.get(i)){
			i++;
		}
				
		colores.add(i, c);
		valores.add(i, limit);
		
	}
	
	// Borrar una entrada dado el limite
	public void removeColor(Integer limit){
		int i = valores.indexOf(limit);
		
		if(i >= 0){
			colores.removeElementAt(i);
			valores.removeElementAt(i);
		}
		
	}
	
	// Borrar entrada dado el indice de la misma
	public void removeIndex(Integer index){
		if(index >= 0){
//			System.out.println("Eliminar " + index);
			colores.removeElementAt(index);
			valores.removeElementAt(index);
		}
	}
	
	// Modificar el color de un indice particular
	public void modifyColor(int indice, Color c){
//		System.out.println("Modificar");
		colores.remove(indice);
		colores.add(indice, c);
	}

	public Color getColor(int v) {
		// Si es menor al primer color de la paleta
		if (v < valores.firstElement()){
			return colores.firstElement();
		}
		
		for (int i = 1; i < valores.size(); i++){
			if(v < valores.get(i)){
				if (interpolar){				

					Color cAnt = colores.get(i-1);
					Color cAct = colores.get(i);

					int vAnt = valores.get(i-1);
					int vAct = valores.get(i);

					int difValores = vAct - vAnt;
					int difRed = cAct.getRed() - cAnt.getRed();
					int difGreen = cAct.getGreen() - cAnt.getGreen();
					int difBlue = cAct.getBlue() - cAnt.getBlue();

					int r = cAnt.getRed() + (v - vAnt) * (difRed) / (difValores);
					int g = cAnt.getGreen() + (v - vAnt) * (difGreen) / (difValores);
					int b = cAnt.getBlue() + (v - vAnt) * (difBlue) / (difValores);

					return new Color(r, g, b);

				} else{
					
					return colores.get(i);
					
				}
			}
		}
		
		return colores.lastElement();
		
	}
	
	public void removeAll(){
		colores = new Vector<Color>();	
		valores = new Vector<Integer>();
	}
	
	public Vector<Color> getColores() {
		return colores;
	}

	public Vector<Integer> getValores() {
		return valores;
	}
	
	public abstract void reset();
	public abstract void save(File f);
}
