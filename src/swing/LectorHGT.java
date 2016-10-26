/**
 * 
 */
package swing;

import filtro.FiltroArchivoHGT;
import filtro.FiltroArchivoPNG;
import filtro.FiltroArchivoPaleta;
import image.Imagen;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker.StateValue;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import paleta.PaletaArchivo;

/**
 * @author hernan
 *
 */
public class LectorHGT {

	private JFrame frmLectorHgt;
	private JLabel labelImagen;
	private JCheckBoxMenuItem chckbxmntmInterpolar;
	private final ButtonGroup groupPaleta = new ButtonGroup();
	private JRadioButtonMenuItem mntmMapaFisico;
	
	// Configuracion actual
//	private File archivo;
	private Imagen imagen = new Imagen();
	private boolean cargado = false;
	private PaletaArchivo paleta;
	private JRadioButtonMenuItem mntmDefault;
	private PanelReferencias gblReferencias;
	private DialogCargarArchivo cargando;	
	private int size;
	private final ButtonGroup groupTamImagen = new ButtonGroup();
	private JMenuItem mntmGuardar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LectorHGT window = new LectorHGT();
					window.frmLectorHgt.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Crear la aplicacion.
	 */
	public LectorHGT() {
		initialize();
	}

	private void initialize() {
		frmLectorHgt = new JFrame();
		frmLectorHgt.setTitle("Lector HGT");
		frmLectorHgt.setBounds(50, 20, 700, 700);
		frmLectorHgt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmLectorHgt.setJMenuBar(menuBar);
		
		// ARCHIVO
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		JMenuItem mntmAbrir = new JMenuItem("Abrir");
		mntmAbrir.setIcon(new ImageIcon(LectorHGT.class.getResource("/icon/open.png")));
		mntmAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mntmAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirArchivo();
			}
		});
		mnArchivo.add(mntmAbrir);
		
		JMenuItem mntmCerrar = new JMenuItem("Cerrar");
		mntmCerrar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
		mntmCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cerrarArchivo();
			}
		});
		mnArchivo.add(mntmCerrar);
		
		mnArchivo.add(new JSeparator());
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.setIcon(new ImageIcon(LectorHGT.class.getResource("/icon/close.png")));
		mntmSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				salir();
			}
		});
		mnArchivo.add(mntmSalir);
		
		// PALETA
		
		JMenu mnPaleta = new JMenu("Paleta");
		menuBar.add(mnPaleta);
		
		chckbxmntmInterpolar = new JCheckBoxMenuItem("Interpolar");
		chckbxmntmInterpolar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
		chckbxmntmInterpolar.setSelected(true);
		chckbxmntmInterpolar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				imagen.setInterpolar(chckbxmntmInterpolar.isSelected());
				refrescar();
			}
		});
		mnPaleta.add(chckbxmntmInterpolar);
		
		mnPaleta.add(new JSeparator());
		
		mntmDefault = new JRadioButtonMenuItem("Predeterminada");
		mntmDefault.setSelected(true);
		mntmDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				seleccionarPaleta(new File("paletas/default.pml"));
			}
		});
		groupPaleta.add(mntmDefault);
		mnPaleta.add(mntmDefault);
		
		mntmMapaFisico = new JRadioButtonMenuItem("Mapa Fisico");
		groupPaleta.add(mntmMapaFisico);
		mntmMapaFisico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				seleccionarPaleta(new File("paletas/fisico.pml"));
			}
		});
		groupPaleta.add(mntmMapaFisico);
		mnPaleta.add(mntmMapaFisico);
		
		JRadioButtonMenuItem mntmAbrirPaleta = new JRadioButtonMenuItem("Cargar Desde Archivo");
		mntmAbrirPaleta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirPaleta();
			}
		});
		groupPaleta.add(mntmAbrirPaleta);
		mnPaleta.add(mntmAbrirPaleta);
		
		mnPaleta.add(new JSeparator());
		
		JMenuItem mntmEditorDePaleta = new JMenuItem("Editor De Paleta");
		mntmEditorDePaleta.setIcon(new ImageIcon(LectorHGT.class.getResource("/icon/edit.png")));
		mntmEditorDePaleta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		mntmEditorDePaleta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirAsistente();
			}
		});
		mnPaleta.add(mntmEditorDePaleta);
		
		JMenu mnImagen = new JMenu("Imagen");
		menuBar.add(mnImagen);
		
		JMenu mnTamao = new JMenu("Tama\u00F1o");
		mnTamao.setIcon(new ImageIcon(LectorHGT.class.getResource("/icon/size.png")));
		mnImagen.add(mnTamao);
		
		JRadioButtonMenuItem rdbtnmntmx = new JRadioButtonMenuItem("200x200");
		groupTamImagen.add(rdbtnmntmx);
		rdbtnmntmx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				size = 200;
				if(cargado){
	        		imagen.mostrar(labelImagen, size);
				}
			}
		});
		mnTamao.add(rdbtnmntmx);
		
		JRadioButtonMenuItem rdbtnmntmx_1 = new JRadioButtonMenuItem("400x400");
		groupTamImagen.add(rdbtnmntmx_1);
		rdbtnmntmx_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				size = 400;
				if(cargado){
	        		imagen.mostrar(labelImagen, size);
				}
			}
		});
		mnTamao.add(rdbtnmntmx_1);
		
		JRadioButtonMenuItem rdbtnmntmx_2 = new JRadioButtonMenuItem("600x600");
		groupTamImagen.add(rdbtnmntmx_2);
		rdbtnmntmx_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				size = 600;
				if(cargado){
	        		imagen.mostrar(labelImagen, size);
				}
			}
		});
		rdbtnmntmx_2.setSelected(true);
		mnTamao.add(rdbtnmntmx_2);
		
		JRadioButtonMenuItem rdbtnmntmx_3 = new JRadioButtonMenuItem("900x900");
		groupTamImagen.add(rdbtnmntmx_3);
		rdbtnmntmx_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				size = 900;
				if(cargado){
	        		imagen.mostrar(labelImagen, size);
				}
			}
		});
		mnTamao.add(rdbtnmntmx_3);
		
		JRadioButtonMenuItem rdbtnmntmx_4 = new JRadioButtonMenuItem("1201x1201");
		groupTamImagen.add(rdbtnmntmx_4);
		rdbtnmntmx_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				size = 1201;
				if(cargado){
	        		imagen.mostrar(labelImagen, size);
				}
			}
		});
		mnTamao.add(rdbtnmntmx_4);
		
		mntmGuardar = new JMenuItem("Guardar");
		mntmGuardar.setIcon(new ImageIcon(LectorHGT.class.getResource("/icon/save.png")));
		mntmGuardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mntmGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guardarImagenComo();
			}
		});
		mntmGuardar.setEnabled(false);
		mnImagen.add(mntmGuardar);
		
		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);
		
		JMenuItem mntmAcercaDe = new JMenuItem("Acerca De");
		mntmAcercaDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DialogAcercaDe acercaDe = new DialogAcercaDe();
				acercaDe.setVisible(true);
			}
		});
		mntmAcercaDe.setIcon(new ImageIcon(LectorHGT.class.getResource("/icon/about.png")));
		mnAyuda.add(mntmAcercaDe);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {630, 120, 0};
		gridBagLayout.rowHeights = new int[]{640, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		frmLectorHgt.getContentPane().setLayout(gridBagLayout);
		
		labelImagen = new JLabel();
		labelImagen.setHorizontalAlignment(SwingConstants.CENTER);
		//		frmLectorHgt.getContentPane().add(labelImagen, BorderLayout.);

		JScrollPane panelImagen = new JScrollPane();
		panelImagen.setViewportView(labelImagen);
		GridBagConstraints gbc_panelImagen = new GridBagConstraints();
		gbc_panelImagen.fill = GridBagConstraints.BOTH;
		gbc_panelImagen.insets = new Insets(0, 0, 0, 5);
		gbc_panelImagen.gridx = 0;
		gbc_panelImagen.gridy = 0;
		frmLectorHgt.getContentPane().add(panelImagen, gbc_panelImagen);		

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 0;
		frmLectorHgt.getContentPane().add(scrollPane, gbc_scrollPane);
		
		JPanel panelReferencias = new JPanel();
		panelReferencias.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Referencias", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_panelReferencias = new GridBagConstraints();
		gbc_panelReferencias.anchor = GridBagConstraints.NORTH;
		gbc_panelReferencias.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelReferencias.gridx = 1;
		gbc_panelReferencias.gridy = 0;
		scrollPane.setViewportView(panelReferencias);
//		frmLectorHgt.getContentPane().add(panelReferencias, gbc_panelReferencias);
		
		gblReferencias = new PanelReferencias(imagen);
		panelReferencias.add(gblReferencias);

				
//		abrirArchivo(new File("hgt/N00E010.hgt"));
		seleccionarPaleta(new File("paletas/default.pml"));
		size = 600;
		
	}

	protected void abrirAsistente() {
		(new AsistentePaleta(this)).setVisible(true);		
	}

	protected void guardarImagenComo() {
		JFileChooser fc = new JFileChooser("saved");

        // Mostrar la ventana para abrir archivo y recoger la respuesta
        fc.setFileFilter(new FiltroArchivoPNG());
        int respuesta = fc.showSaveDialog(null);

        // Comprobar si se ha pulsado Aceptar
        if (respuesta == JFileChooser.APPROVE_OPTION){
        	String path = fc.getSelectedFile().getAbsolutePath();
        	
        	if (! path.endsWith(".jpg")){
        		path += ".jpg";
        	}
        	
            guardarImagen(new File(path));
        }
		
	}

	private void guardarImagen(File f) {
		try {
			ImageIO.write(imagen.getBufferedImage(), "png", f);
		} catch (IOException e) {
//			System.out.println("Error de escritura");
		}		
	}

	

	// Seleccionar archivo PML desde JFileChooser
	protected void abrirPaleta() {
		JFileChooser fc = new JFileChooser("paletas");

        // Mostrar la ventana para abrir archivo y recoger la respuesta
        fc.setFileFilter(new FiltroArchivoPaleta());
        int respuesta = fc.showOpenDialog(null);

        // Comprobar si se ha pulsado Aceptar
        if (respuesta == JFileChooser.APPROVE_OPTION){
            seleccionarPaleta(fc.getSelectedFile());
        }		
	}

	// Configurar paleta
	void seleccionarPaleta(File f) {
		imagen.setPaleta(new PaletaArchivo(f, chckbxmntmInterpolar.isSelected()));
        gblReferencias.agregarReferencias();
        refrescar();
	}

	public PanelReferencias getGblReferencias() {
		return gblReferencias;
	}

	// Seleccionar archivo HGT desde JFileChooser
	protected void abrirArchivo() {
		JFileChooser fc = new JFileChooser("hgt");

        // Mostrar la ventana para abrir archivo y recoger la respuesta
        fc.setFileFilter(new FiltroArchivoHGT());
        int respuesta = fc.showOpenDialog(null);

        // Comprobar si se ha pulsado Aceptar
        if (respuesta == JFileChooser.APPROVE_OPTION){
            abrirArchivo(fc.getSelectedFile());
        }
	}
	
	// Procesar archivo HGT
	private void abrirArchivo(File archivo){
        cargando = new DialogCargarArchivo();

        TareaCargarHGT tarea = new TareaCargarHGT(imagen, archivo);
        tarea.addPropertyChangeListener(
            new PropertyChangeListener() {

				@Override
                public  void propertyChange(PropertyChangeEvent evt) {
					String evento = evt.getPropertyName();
					if ("state".equals(evento) && evt.getNewValue().equals(StateValue.STARTED)){
//						System.out.println("Comenzando tarea.");
				        cargando.setVisible(true);
					}else if ("progress".equals(evento)) {
                    	// Progreso en la tarea
                        cargando.setValue((Integer)evt.getNewValue());
                    }else if("state".equals(evento) && evt.getNewValue().equals(StateValue.DONE)){
//                    	System.out.println("Tarea finalizada.");
                    	cargando.dispose();
                		imagen.mostrar(labelImagen, size);
                		cargado = true;
                		mntmGuardar.setEnabled(true);
                    }
                }
            });
        tarea.execute();
	}

	// Cerrar imagen
	protected void cerrarArchivo() {
		labelImagen.setIcon(new ImageIcon());
		cargado = false;
		mntmGuardar.setEnabled(false);
	}
	
	// Salir de la aplicacion
	protected void salir() {
		frmLectorHgt.dispose();		
	}

	public void refrescar() {
		if(cargado){
			imagen.repaint();
			imagen.mostrar(labelImagen, size);
		}		
	}

	public Imagen getImagen() {
		return imagen;
	}
	
}
