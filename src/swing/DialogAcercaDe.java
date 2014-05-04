package swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.Toolkit;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DialogAcercaDe extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DialogAcercaDe dialog = new DialogAcercaDe();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DialogAcercaDe() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(DialogAcercaDe.class.getResource("/icon/about.png")));
		setModal(true);
		setTitle("Acerca De");
		setResizable(false);
		setBounds(100, 100, 450, 475);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{450, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 21, 0, 0, 0, 0, 0, 23, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		{
			JLabel lblLectorHgt = new JLabel("Lector HGT v1.0");
			lblLectorHgt.setFont(new Font("Tahoma", Font.BOLD, 20));
			GridBagConstraints gbc_lblLectorHgt = new GridBagConstraints();
			gbc_lblLectorHgt.anchor = GridBagConstraints.WEST;
			gbc_lblLectorHgt.insets = new Insets(10, 10, 15, 10);
			gbc_lblLectorHgt.gridx = 0;
			gbc_lblLectorHgt.gridy = 0;
			getContentPane().add(lblLectorHgt, gbc_lblLectorHgt);
		}
		{
			JLabel lblLaAplicacinPermite = new JLabel((String) null);
			GridBagConstraints gbc_lblLaAplicacinPermite = new GridBagConstraints();
			gbc_lblLaAplicacinPermite.insets = new Insets(0, 0, 5, 0);
			gbc_lblLaAplicacinPermite.gridx = 0;
			gbc_lblLaAplicacinPermite.gridy = 1;
			getContentPane().add(lblLaAplicacinPermite, gbc_lblLaAplicacinPermite);
		}
		{
			JTextPane txtpnLaAplicacinPermite = new JTextPane();
			txtpnLaAplicacinPermite.setBackground(UIManager.getColor("CheckBox.light"));
			txtpnLaAplicacinPermite.setEditable(false);
			txtpnLaAplicacinPermite.setText("La aplicaci\u00F3n permite visualizar archivos de relieve *.hgt. Se puede ver en diferentes tama\u00F1os, y tambi\u00E9n incluye la opci\u00F3n de exportar a una imagen PNG.\r\nLos colores dependen de la paleta de colores seleccionada, y si est\u00E1 activada o no la interpolaci\u00F3n (la transici\u00F3n continua entre las diferentes referencias de la paleta).");
			GridBagConstraints gbc_txtpnLaAplicacinPermite = new GridBagConstraints();
			gbc_txtpnLaAplicacinPermite.insets = new Insets(0, 10, 10, 10);
			gbc_txtpnLaAplicacinPermite.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtpnLaAplicacinPermite.gridx = 0;
			gbc_txtpnLaAplicacinPermite.gridy = 2;
			getContentPane().add(txtpnLaAplicacinPermite, gbc_txtpnLaAplicacinPermite);
		}
		{
			JLabel lblPaleta = new JLabel("Paleta");
			lblPaleta.setFont(new Font("Tahoma", Font.BOLD, 15));
			GridBagConstraints gbc_lblPaleta = new GridBagConstraints();
			gbc_lblPaleta.anchor = GridBagConstraints.WEST;
			gbc_lblPaleta.insets = new Insets(0, 10, 5, 10);
			gbc_lblPaleta.gridx = 0;
			gbc_lblPaleta.gridy = 3;
			getContentPane().add(lblPaleta, gbc_lblPaleta);
		}
		{
			JTextPane txtpnLaPaletaSe = new JTextPane();
			txtpnLaPaletaSe.setBackground(UIManager.getColor("CheckBox.light"));
			txtpnLaPaletaSe.setText("La paleta se puede seleccionar de entre las ya provistas por la aplicaci\u00F3n, o cargar una paleta desde un archivo *.pml. La aplicaci\u00F3n tambi\u00E9n incluye un editor (Paleta - Editor de Paleta), donde se puede agregar o quitar referencias de la paleta actual, y guardar en un archivo para su posterior uso.");
			GridBagConstraints gbc_txtpnLaPaletaSe = new GridBagConstraints();
			gbc_txtpnLaPaletaSe.insets = new Insets(0, 10, 5, 10);
			gbc_txtpnLaPaletaSe.fill = GridBagConstraints.BOTH;
			gbc_txtpnLaPaletaSe.gridx = 0;
			gbc_txtpnLaPaletaSe.gridy = 4;
			getContentPane().add(txtpnLaPaletaSe, gbc_txtpnLaPaletaSe);
		}
		{
			JLabel lblUniversidadNacionalDel = new JLabel("Universidad Nacional Del Centro");
			lblUniversidadNacionalDel.setFont(new Font("Tahoma", Font.BOLD, 14));
			GridBagConstraints gbc_lblUniversidadNacionalDel = new GridBagConstraints();
			gbc_lblUniversidadNacionalDel.insets = new Insets(0, 0, 5, 0);
			gbc_lblUniversidadNacionalDel.gridx = 0;
			gbc_lblUniversidadNacionalDel.gridy = 6;
			getContentPane().add(lblUniversidadNacionalDel, gbc_lblUniversidadNacionalDel);
		}
		{
			JLabel lblDeLaProvincia = new JLabel("De La Provincia De Buenos Aires (U.N.C.P.B.A.)");
			lblDeLaProvincia.setFont(new Font("Tahoma", Font.BOLD, 14));
			GridBagConstraints gbc_lblDeLaProvincia = new GridBagConstraints();
			gbc_lblDeLaProvincia.insets = new Insets(0, 0, 5, 0);
			gbc_lblDeLaProvincia.gridx = 0;
			gbc_lblDeLaProvincia.gridy = 7;
			getContentPane().add(lblDeLaProvincia, gbc_lblDeLaProvincia);
		}
		{
			JLabel lblIngenieraDeSistemas = new JLabel("Ingenier\u00EDa de Sistemas");
			lblIngenieraDeSistemas.setFont(new Font("Tahoma", Font.BOLD, 12));
			GridBagConstraints gbc_lblIngenieraDeSistemas = new GridBagConstraints();
			gbc_lblIngenieraDeSistemas.insets = new Insets(0, 0, 5, 0);
			gbc_lblIngenieraDeSistemas.gridx = 0;
			gbc_lblIngenieraDeSistemas.gridy = 8;
			getContentPane().add(lblIngenieraDeSistemas, gbc_lblIngenieraDeSistemas);
		}
		{
			JLabel lblVisualizacinComputacionalI = new JLabel("Visualizaci\u00F3n Computacional I - 2014");
			lblVisualizacinComputacionalI.setFont(new Font("Tahoma", Font.PLAIN, 12));
			GridBagConstraints gbc_lblVisualizacinComputacionalI = new GridBagConstraints();
			gbc_lblVisualizacinComputacionalI.insets = new Insets(0, 0, 5, 0);
			gbc_lblVisualizacinComputacionalI.gridx = 0;
			gbc_lblVisualizacinComputacionalI.gridy = 9;
			getContentPane().add(lblVisualizacinComputacionalI, gbc_lblVisualizacinComputacionalI);
		}
		{
			JLabel lblAutor = new JLabel("Hern\u00E1n Gabriel Rocha");
			lblAutor.setFont(new Font("Tahoma", Font.BOLD, 15));
			GridBagConstraints gbc_lblAutor = new GridBagConstraints();
			gbc_lblAutor.insets = new Insets(0, 10, 5, 10);
			gbc_lblAutor.gridx = 0;
			gbc_lblAutor.gridy = 10;
			getContentPane().add(lblAutor, gbc_lblAutor);
		}
		{
			JButton okButton = new JButton("Aceptar");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			GridBagConstraints gbc_okButton = new GridBagConstraints();
			gbc_okButton.insets = new Insets(10, 0, 10, 0);
			gbc_okButton.anchor = GridBagConstraints.NORTH;
			gbc_okButton.gridx = 0;
			gbc_okButton.gridy = 11;
			getContentPane().add(okButton, gbc_okButton);
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
	}

}
