package swing;

import image.Imagen;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import paleta.ImgMuestra;
import paleta.Paleta;

public class PanelReferencias extends JPanel {

	private static final long serialVersionUID = 1L;
	private Imagen imagen;

	/**
	 * Create the panel.
	 */
	public PanelReferencias(Imagen imagen) {
		this.imagen = imagen;
		
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{29, 0};
		gbl_panel_1.rowHeights = new int[]{14, 14, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gbl_panel_1);

	}
	
	public void agregarReferencias() {
//		System.out.println("Actualizar panel de referencias");
		
		Paleta p = imagen.getPaleta();
		Vector<Integer> valores = p.getValores();
		Vector<Color> colores = p.getColores();
		
		// Borrar elementos anteriores
		removeAll();

		// Agregar elementos nuevos
		for(int i = 0; i < valores.size(); i++){
			agregarReferencia(colores.get(i), valores.get(i), i);
		}
		
		// Actualizar panel
		updateUI();
		
	}

	public void agregarReferencia(Color c, int limite, int yRef) {
		JLabel label = new JLabel("" + limite);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.fill = GridBagConstraints.BOTH;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = yRef;
		add(label, gbc_label);
		
		JLabel lblC = new JLabel();
		GridBagConstraints gbc_lblC = new GridBagConstraints();
		gbc_lblC.insets = new Insets(0, 0, 5, 0);
		gbc_lblC.gridx = 1;
		gbc_lblC.gridy = yRef;
		lblC.setIcon(new ImageIcon(new ImgMuestra(c)));
		add(lblC, gbc_lblC);
		
	}

}
