/**
 * 
 */
package swing;

import filtro.FiltroArchivoHGT;
import filtro.FiltroArchivoPNG;
import filtro.FiltroArchivoPaleta;
import image.Imagen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import paleta.ImgMuestra;
import paleta.Paleta;

/**
 * @author hernan
 *
 */
public class AsistentePaleta extends JDialog {

	private static final long serialVersionUID = 1L;
	private JSpinner spinBlue;
	private JSpinner spinGreen;
	private JSpinner spinRed;
	private Imagen imagen;
	private JList<ColorListRenderer> list;
	private DefaultListModel<String> elementosMuestra;
	private Paleta paleta;
	private JButton btnEliminar;
	private JButton btnAgregar;
	private JList petList;
	private JSpinner spinLimite;
	private JButton btnAplicar;
	private JButton btnResetear;
	private JButton btnGuardar;
	private JButton btnEliminarTodas;
	private JPanel panelAgregarReferencia;
	private JPanel panelReferencias;
	private JPanel panelPaleta;
	private JButton btnCargar;
	private JPanel gblAgregarReferencia;
	private JLabel lblLimite;
	private JLabel lblRojo;
	private JLabel lblVerde;
	private JLabel lblAzul;
	private JLabel lblMuestra;
	private JPanel panel;
	private JPanel panelContenedor;
	private LectorHGT lector;
	private JScrollPane scrollPane;

	/**
	 * Create the dialog.
	 */
	public AsistentePaleta(final LectorHGT lector) {
		setResizable(false);
		setModal(true);
		setTitle("Editor De Paleta");
		this.imagen = lector.getImagen();
		this.lector = lector;

		setBounds(10, 10, 600, 424);
		getContentPane().setLayout(new BorderLayout());
		panelContenedor = new JPanel();
		getContentPane().add(panelContenedor, BorderLayout.NORTH);
		GridBagLayout gbl_panelContenedor = new GridBagLayout();
		gbl_panelContenedor.columnWidths = new int[]{197, 221, 0};
		gbl_panelContenedor.rowHeights = new int[]{69, 0, 0};
		gbl_panelContenedor.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panelContenedor.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panelContenedor.setLayout(gbl_panelContenedor);
		{
			panelPaleta = new JPanel();
			GridBagConstraints gbc_panelPaleta = new GridBagConstraints();
			gbc_panelPaleta.fill = GridBagConstraints.HORIZONTAL;
			gbc_panelPaleta.gridwidth = 2;
			gbc_panelPaleta.insets = new Insets(5, 5, 5, 5);
			gbc_panelPaleta.gridx = 0;
			gbc_panelPaleta.gridy = 0;
			panelContenedor.add(panelPaleta, gbc_panelPaleta);
			panelPaleta.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Paleta", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			btnAplicar = new JButton("Aplicar");
			btnAplicar.setFont(new Font("Tahoma", Font.PLAIN, 15));
			panelPaleta.add(btnAplicar);
			{
				btnResetear = new JButton("Resetear");
				btnResetear.setFont(new Font("Tahoma", Font.PLAIN, 15));
				panelPaleta.add(btnResetear);
				{
					btnCargar = new JButton("Cargar");
					btnCargar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							cargarPaleta();
						}
					});
					btnCargar.setFont(new Font("Tahoma", Font.PLAIN, 15));
					panelPaleta.add(btnCargar);
				}
				{
					btnGuardar = new JButton("Guardar");
					btnGuardar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							JFileChooser fc = new JFileChooser("paletas");

					        // Mostrar la ventana para abrir archivo y recoger la respuesta
					        fc.setFileFilter(new FiltroArchivoPaleta());
					        int respuesta = fc.showSaveDialog(null);

					        // Comprobar si se ha pulsado Aceptar
					        if (respuesta == JFileChooser.APPROVE_OPTION){
					        	String path = fc.getSelectedFile().getAbsolutePath();
					        	
					        	if (! path.endsWith(".pml")){
					        		path += ".pml";
					        	}
					        	
					            paleta.save(new File(path));
					        }
						}
					});
					btnGuardar.setFont(new Font("Tahoma", Font.PLAIN, 15));
					panelPaleta.add(btnGuardar);
				}
				btnResetear.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						paleta.reset();
						actualizarReferencias();
						lector.refrescar();					
					}
				});
			}
			btnAplicar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					lector.getGblReferencias().agregarReferencias();
					lector.refrescar();
				}
			});
		}
		panelReferencias = new JPanel();
		GridBagConstraints gbc_panelReferencias = new GridBagConstraints();
		gbc_panelReferencias.fill = GridBagConstraints.BOTH;
		gbc_panelReferencias.insets = new Insets(0, 5, 0, 5);
		gbc_panelReferencias.gridx = 0;
		gbc_panelReferencias.gridy = 1;
		panelContenedor.add(panelReferencias, gbc_panelReferencias);
		panelReferencias.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Referencias", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel = new JPanel();
		panelReferencias.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{69, 101, 0};
		gbl_panel.rowHeights = new int[]{39, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		{
			scrollPane = new JScrollPane();
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.gridwidth = 2;
			gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 0;
			gbc_scrollPane.gridy = 0;
			panel.add(scrollPane, gbc_scrollPane);
			actualizarReferencias();
		}
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_btnEliminar = new GridBagConstraints();
		gbc_btnEliminar.insets = new Insets(0, 0, 0, 5);
		gbc_btnEliminar.gridx = 0;
		gbc_btnEliminar.gridy = 1;
		panel.add(btnEliminar, gbc_btnEliminar);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = petList.getSelectedIndex();
//				System.out.println("Seleccionado :" + index);
				paleta.removeIndex(index);
				actualizarReferencias();
			}
		});
		{
			btnEliminarTodas = new JButton("Eliminar Todas");
			btnEliminarTodas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int size = paleta.getColores().size();
					for(int i = 0; i < size; i++) {
						int index = petList.getSelectedIndex();
						paleta.removeIndex(index);
						actualizarReferencias();						
					}
				}
			});
			btnEliminarTodas.setFont(new Font("Tahoma", Font.PLAIN, 15));
			GridBagConstraints gbc_btnEliminarTodas = new GridBagConstraints();
			gbc_btnEliminarTodas.gridx = 1;
			gbc_btnEliminarTodas.gridy = 1;
			panel.add(btnEliminarTodas, gbc_btnEliminarTodas);
		}
		panelAgregarReferencia = new JPanel();
		GridBagConstraints gbc_panelAgregarReferencia = new GridBagConstraints();
		gbc_panelAgregarReferencia.insets = new Insets(0, 5, 0, 5);
		gbc_panelAgregarReferencia.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelAgregarReferencia.anchor = GridBagConstraints.NORTH;
		gbc_panelAgregarReferencia.gridx = 1;
		gbc_panelAgregarReferencia.gridy = 1;
		panelContenedor.add(panelAgregarReferencia, gbc_panelAgregarReferencia);
		panelAgregarReferencia.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Agregar Referencia", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		gblAgregarReferencia = new JPanel();
		panelAgregarReferencia.add(gblAgregarReferencia);
		GridBagLayout gbl_gblAgregarReferencia = new GridBagLayout();
		gbl_gblAgregarReferencia.columnWidths = new int[]{47, 47, 47, 0};
		gbl_gblAgregarReferencia.rowHeights = new int[]{20, 0, 0, 0, 0, 0};
		gbl_gblAgregarReferencia.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_gblAgregarReferencia.rowWeights = new double[]{1.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gblAgregarReferencia.setLayout(gbl_gblAgregarReferencia);
		{
			lblRojo = new JLabel("Rojo");
			lblRojo.setFont(new Font("Arial", Font.BOLD, 13));
			GridBagConstraints gbc_lblRojo = new GridBagConstraints();
			gbc_lblRojo.anchor = GridBagConstraints.EAST;
			gbc_lblRojo.insets = new Insets(0, 0, 5, 5);
			gbc_lblRojo.gridx = 0;
			gbc_lblRojo.gridy = 0;
			gblAgregarReferencia.add(lblRojo, gbc_lblRojo);
		}
		spinRed = new JSpinner();
		GridBagConstraints gbc_spinRed = new GridBagConstraints();
		gbc_spinRed.anchor = GridBagConstraints.WEST;
		gbc_spinRed.insets = new Insets(0, 0, 5, 5);
		gbc_spinRed.gridx = 1;
		gbc_spinRed.gridy = 0;
		gblAgregarReferencia.add(spinRed, gbc_spinRed);
		spinRed.setToolTipText("Rojo");
		spinRed.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				actualizarMuestraActual();
			}
		});
		spinRed.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		{
			lblMuestra = new JLabel("");
			GridBagConstraints gbc_lblMuestra = new GridBagConstraints();
			gbc_lblMuestra.fill = GridBagConstraints.VERTICAL;
			gbc_lblMuestra.weighty = 40.0;
			gbc_lblMuestra.weightx = 40.0;
			gbc_lblMuestra.gridheight = 3;
			gbc_lblMuestra.insets = new Insets(0, 0, 5, 0);
			gbc_lblMuestra.gridx = 2;
			gbc_lblMuestra.gridy = 0;
			lblMuestra.setIcon(new ImageIcon(new ImgMuestra(Color.BLACK, 100, 100)));
			gblAgregarReferencia.add(lblMuestra, gbc_lblMuestra);
		}
		{
			lblVerde = new JLabel("Verde");
			lblVerde.setFont(new Font("Arial", Font.BOLD, 13));
			GridBagConstraints gbc_lblVerde = new GridBagConstraints();
			gbc_lblVerde.anchor = GridBagConstraints.EAST;
			gbc_lblVerde.insets = new Insets(0, 0, 5, 5);
			gbc_lblVerde.gridx = 0;
			gbc_lblVerde.gridy = 1;
			gblAgregarReferencia.add(lblVerde, gbc_lblVerde);
		}
		{
			spinGreen = new JSpinner();
			spinGreen.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					actualizarMuestraActual();
				}
			});
			GridBagConstraints gbc_spinGreen = new GridBagConstraints();
			gbc_spinGreen.anchor = GridBagConstraints.WEST;
			gbc_spinGreen.insets = new Insets(0, 0, 5, 5);
			gbc_spinGreen.gridx = 1;
			gbc_spinGreen.gridy = 1;
			gblAgregarReferencia.add(spinGreen, gbc_spinGreen);
			spinGreen.setToolTipText("Verde");
			spinGreen.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		}
		{
			lblAzul = new JLabel("Azul");
			lblAzul.setFont(new Font("Arial", Font.BOLD, 13));
			GridBagConstraints gbc_lblAzul = new GridBagConstraints();
			gbc_lblAzul.anchor = GridBagConstraints.EAST;
			gbc_lblAzul.insets = new Insets(0, 0, 5, 5);
			gbc_lblAzul.gridx = 0;
			gbc_lblAzul.gridy = 2;
			gblAgregarReferencia.add(lblAzul, gbc_lblAzul);
		}
		{
			spinBlue = new JSpinner();
			spinBlue.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					actualizarMuestraActual();
				}
			});
			GridBagConstraints gbc_spinBlue = new GridBagConstraints();
			gbc_spinBlue.insets = new Insets(0, 0, 5, 5);
			gbc_spinBlue.anchor = GridBagConstraints.WEST;
			gbc_spinBlue.gridx = 1;
			gbc_spinBlue.gridy = 2;
			gblAgregarReferencia.add(spinBlue, gbc_spinBlue);
			spinBlue.setToolTipText("Azul");
			spinBlue.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		}
		{
			lblLimite = new JLabel("Limite");
			lblLimite.setFont(new Font("Arial", Font.BOLD, 13));
			GridBagConstraints gbc_lblLimite = new GridBagConstraints();
			gbc_lblLimite.anchor = GridBagConstraints.EAST;
			gbc_lblLimite.insets = new Insets(0, 0, 5, 5);
			gbc_lblLimite.gridx = 0;
			gbc_lblLimite.gridy = 3;
			gblAgregarReferencia.add(lblLimite, gbc_lblLimite);
		}
		{
			spinLimite = new JSpinner();
			GridBagConstraints gbc_spinLimite = new GridBagConstraints();
			gbc_spinLimite.insets = new Insets(0, 0, 5, 0);
			gbc_spinLimite.anchor = GridBagConstraints.WEST;
			gbc_spinLimite.gridwidth = 2;
			gbc_spinLimite.gridx = 1;
			gbc_spinLimite.gridy = 3;
			gblAgregarReferencia.add(spinLimite, gbc_spinLimite);
			spinLimite.setModel(new SpinnerNumberModel(0, 0, 65535, 1));
			spinLimite.setToolTipText("Limite");
		}
		btnAgregar = new JButton("Aceptar");
		btnAgregar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_btnAgregar = new GridBagConstraints();
		gbc_btnAgregar.gridwidth = 3;
		gbc_btnAgregar.insets = new Insets(5, 5, 5, 5);
		gbc_btnAgregar.gridx = 0;
		gbc_btnAgregar.gridy = 4;
		gblAgregarReferencia.add(btnAgregar, gbc_btnAgregar);
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int r = (int) spinRed.getValue();
				int g = (int) spinGreen.getValue();
				int b = (int) spinBlue.getValue();
				int limite = (int) spinLimite.getValue();

				paleta.addColor(new Color(r, g, b), limite);
				actualizarReferencias();
			}
		});
	}

	protected void cargarPaleta() {
		JFileChooser fc = new JFileChooser("paletas");

		// Mostrar la ventana para abrir archivo y recoger la respuesta
		fc.setFileFilter(new FiltroArchivoPaleta());
		int respuesta = fc.showOpenDialog(null);

		// Comprobar si se ha pulsado Aceptar
		if (respuesta == JFileChooser.APPROVE_OPTION){
			lector.seleccionarPaleta(fc.getSelectedFile());
			actualizarReferencias();
		}

	}
	
	public void setMuestra(int r, int g, int b, int limit) {
		spinRed.setValue(r);
		spinGreen.setValue(g);
		spinBlue.setValue(b);
		spinLimite.setValue(limit);
		
		actualizarMuestraActual();
	}

	protected void actualizarMuestraActual() {
		int r = (int) spinRed.getValue();
		int g = (int) spinGreen.getValue();
		int b = (int) spinBlue.getValue();

		lblMuestra.setIcon(new ImageIcon(new ImgMuestra(new Color(r,g,b), 100, 100)));		
	}

	private void actualizarReferencias() {
		if(petList != null){
			petList.setCellRenderer(null);
		}
		
		petList = new JList();
		petList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		paleta = imagen.getPaleta();
		
		// Elementos iniciales
		elementosMuestra = new DefaultListModel<String>();					
		for(int i = 0; i < paleta.getValores().size(); i++){
			elementosMuestra.addElement("" + i);
		}
		
//		System.out.println("Referencias: " + paleta.getValores().size());
		petList.setModel(elementosMuestra);					
		petList.setSelectedIndex(0);
		ColorListRenderer elem = new ColorListRenderer(this, imagen.getPaleta());
		elem.setPreferredSize(new Dimension(150, 30));
		petList.setCellRenderer(elem);
//		panel_1.add(petList);
		scrollPane.setViewportView(petList);
		
		petList.updateUI();


	}

}
