/**
 *
 */
package swing;

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

import filtro.FiltroArchivoHGT;
import filtro.FiltroArchivoPNG;
import filtro.FiltroArchivoPaleta;
import image.Imagen;
import paleta.PaletaArchivo;

/**
 * @author hernan
 *
 */
public class LectorHGT {

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
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

    private JFrame frmLectorHgt;
    private JLabel labelImagen;
    private JCheckBoxMenuItem chckbxmntmInterpolar;
    private final ButtonGroup groupPaleta = new ButtonGroup();

    private JRadioButtonMenuItem mntmMapaFisico;
    // Configuracion actual
    // private File archivo;
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
     * Crear la aplicacion.
     */
    public LectorHGT() {
        initialize();
    }

    // Seleccionar archivo HGT desde JFileChooser
    protected void abrirArchivo() {
        JFileChooser fc = new JFileChooser("hgt");

        // Mostrar la ventana para abrir archivo y recoger la respuesta
        fc.setFileFilter(new FiltroArchivoHGT());
        int respuesta = fc.showOpenDialog(null);

        // Comprobar si se ha pulsado Aceptar
        if (respuesta == JFileChooser.APPROVE_OPTION) {
            abrirArchivo(fc.getSelectedFile());
        }
    }

    // Procesar archivo HGT
    private void abrirArchivo(File archivo) {
        this.cargando = new DialogCargarArchivo();

        TareaCargarHGT tarea = new TareaCargarHGT(this.imagen, archivo);
        tarea.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                String evento = evt.getPropertyName();
                if ("state".equals(evento) && evt.getNewValue().equals(StateValue.STARTED)) {
                    // System.out.println("Comenzando tarea.");
                    LectorHGT.this.cargando.setVisible(true);
                } else if ("progress".equals(evento)) {
                    // Progreso en la tarea
                    LectorHGT.this.cargando.setValue((Integer) evt.getNewValue());
                } else if ("state".equals(evento) && evt.getNewValue().equals(StateValue.DONE)) {
                    // System.out.println("Tarea finalizada.");
                    LectorHGT.this.cargando.dispose();
                    LectorHGT.this.imagen.mostrar(LectorHGT.this.labelImagen, LectorHGT.this.size);
                    LectorHGT.this.cargado = true;
                    LectorHGT.this.mntmGuardar.setEnabled(true);
                }
            }
        });
        tarea.execute();
    }

    protected void abrirAsistente() {
        (new AsistentePaleta(this)).setVisible(true);
    }

    // Seleccionar archivo PML desde JFileChooser
    protected void abrirPaleta() {
        JFileChooser fc = new JFileChooser("paletas");

        // Mostrar la ventana para abrir archivo y recoger la respuesta
        fc.setFileFilter(new FiltroArchivoPaleta());
        int respuesta = fc.showOpenDialog(null);

        // Comprobar si se ha pulsado Aceptar
        if (respuesta == JFileChooser.APPROVE_OPTION) {
            seleccionarPaleta(fc.getSelectedFile());
        }
    }

    // Cerrar imagen
    protected void cerrarArchivo() {
        this.labelImagen.setIcon(new ImageIcon());
        this.cargado = false;
        this.mntmGuardar.setEnabled(false);
    }

    public PanelReferencias getGblReferencias() {
        return this.gblReferencias;
    }

    public Imagen getImagen() {
        return this.imagen;
    }

    private void guardarImagen(File f) {
        try {
            ImageIO.write(this.imagen.getBufferedImage(), "png", f);
        } catch (IOException e) {
            // System.out.println("Error de escritura");
        }
    }

    protected void guardarImagenComo() {
        JFileChooser fc = new JFileChooser("saved");

        // Mostrar la ventana para abrir archivo y recoger la respuesta
        fc.setFileFilter(new FiltroArchivoPNG());
        int respuesta = fc.showSaveDialog(null);

        // Comprobar si se ha pulsado Aceptar
        if (respuesta == JFileChooser.APPROVE_OPTION) {
            String path = fc.getSelectedFile().getAbsolutePath();

            if (!path.endsWith(".jpg")) {
                path += ".jpg";
            }

            guardarImagen(new File(path));
        }

    }

    private void initialize() {
        this.frmLectorHgt = new JFrame();
        this.frmLectorHgt.setTitle("Lector HGT");
        this.frmLectorHgt.setBounds(50, 20, 700, 700);
        this.frmLectorHgt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        this.frmLectorHgt.setJMenuBar(menuBar);

        // ARCHIVO

        JMenu mnArchivo = new JMenu("Archivo");
        menuBar.add(mnArchivo);

        JMenuItem mntmAbrir = new JMenuItem("Abrir");
        mntmAbrir.setIcon(new ImageIcon(LectorHGT.class.getResource("/icon/open.png")));
        mntmAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        mntmAbrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                abrirArchivo();
            }
        });
        mnArchivo.add(mntmAbrir);

        JMenuItem mntmCerrar = new JMenuItem("Cerrar");
        mntmCerrar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
        mntmCerrar.addActionListener(new ActionListener() {
            @Override
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
            @Override
            public void actionPerformed(ActionEvent arg0) {
                salir();
            }
        });
        mnArchivo.add(mntmSalir);

        // PALETA

        JMenu mnPaleta = new JMenu("Paleta");
        menuBar.add(mnPaleta);

        this.chckbxmntmInterpolar = new JCheckBoxMenuItem("Interpolar");
        this.chckbxmntmInterpolar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
        this.chckbxmntmInterpolar.setSelected(true);
        this.chckbxmntmInterpolar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                LectorHGT.this.imagen.setInterpolar(LectorHGT.this.chckbxmntmInterpolar.isSelected());
                refrescar();
            }
        });
        mnPaleta.add(this.chckbxmntmInterpolar);

        mnPaleta.add(new JSeparator());

        this.mntmDefault = new JRadioButtonMenuItem("Predeterminada");
        this.mntmDefault.setSelected(true);
        this.mntmDefault.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                seleccionarPaleta(new File("paletas/default.pml"));
            }
        });
        this.groupPaleta.add(this.mntmDefault);
        mnPaleta.add(this.mntmDefault);

        this.mntmMapaFisico = new JRadioButtonMenuItem("Mapa Fisico");
        this.groupPaleta.add(this.mntmMapaFisico);
        this.mntmMapaFisico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                seleccionarPaleta(new File("paletas/fisico.pml"));
            }
        });
        this.groupPaleta.add(this.mntmMapaFisico);
        mnPaleta.add(this.mntmMapaFisico);

        JRadioButtonMenuItem mntmAbrirPaleta = new JRadioButtonMenuItem("Cargar Desde Archivo");
        mntmAbrirPaleta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                abrirPaleta();
            }
        });
        this.groupPaleta.add(mntmAbrirPaleta);
        mnPaleta.add(mntmAbrirPaleta);

        mnPaleta.add(new JSeparator());

        JMenuItem mntmEditorDePaleta = new JMenuItem("Editor De Paleta");
        mntmEditorDePaleta.setIcon(new ImageIcon(LectorHGT.class.getResource("/icon/edit.png")));
        mntmEditorDePaleta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        mntmEditorDePaleta.addActionListener(new ActionListener() {
            @Override
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
        this.groupTamImagen.add(rdbtnmntmx);
        rdbtnmntmx.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                LectorHGT.this.size = 200;
                if (LectorHGT.this.cargado) {
                    LectorHGT.this.imagen.mostrar(LectorHGT.this.labelImagen, LectorHGT.this.size);
                }
            }
        });
        mnTamao.add(rdbtnmntmx);

        JRadioButtonMenuItem rdbtnmntmx_1 = new JRadioButtonMenuItem("400x400");
        this.groupTamImagen.add(rdbtnmntmx_1);
        rdbtnmntmx_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                LectorHGT.this.size = 400;
                if (LectorHGT.this.cargado) {
                    LectorHGT.this.imagen.mostrar(LectorHGT.this.labelImagen, LectorHGT.this.size);
                }
            }
        });
        mnTamao.add(rdbtnmntmx_1);

        JRadioButtonMenuItem rdbtnmntmx_2 = new JRadioButtonMenuItem("600x600");
        this.groupTamImagen.add(rdbtnmntmx_2);
        rdbtnmntmx_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                LectorHGT.this.size = 600;
                if (LectorHGT.this.cargado) {
                    LectorHGT.this.imagen.mostrar(LectorHGT.this.labelImagen, LectorHGT.this.size);
                }
            }
        });
        rdbtnmntmx_2.setSelected(true);
        mnTamao.add(rdbtnmntmx_2);

        JRadioButtonMenuItem rdbtnmntmx_3 = new JRadioButtonMenuItem("900x900");
        this.groupTamImagen.add(rdbtnmntmx_3);
        rdbtnmntmx_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                LectorHGT.this.size = 900;
                if (LectorHGT.this.cargado) {
                    LectorHGT.this.imagen.mostrar(LectorHGT.this.labelImagen, LectorHGT.this.size);
                }
            }
        });
        mnTamao.add(rdbtnmntmx_3);

        JRadioButtonMenuItem rdbtnmntmx_4 = new JRadioButtonMenuItem("1201x1201");
        this.groupTamImagen.add(rdbtnmntmx_4);
        rdbtnmntmx_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                LectorHGT.this.size = 1201;
                if (LectorHGT.this.cargado) {
                    LectorHGT.this.imagen.mostrar(LectorHGT.this.labelImagen, LectorHGT.this.size);
                }
            }
        });
        mnTamao.add(rdbtnmntmx_4);

        this.mntmGuardar = new JMenuItem("Guardar");
        this.mntmGuardar.setIcon(new ImageIcon(LectorHGT.class.getResource("/icon/save.png")));
        this.mntmGuardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        this.mntmGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                guardarImagenComo();
            }
        });
        this.mntmGuardar.setEnabled(false);
        mnImagen.add(this.mntmGuardar);

        JMenu mnAyuda = new JMenu("Ayuda");
        menuBar.add(mnAyuda);

        JMenuItem mntmAcercaDe = new JMenuItem("Acerca De");
        mntmAcercaDe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                DialogAcercaDe acercaDe = new DialogAcercaDe();
                acercaDe.setVisible(true);
            }
        });
        mntmAcercaDe.setIcon(new ImageIcon(LectorHGT.class.getResource("/icon/about.png")));
        mnAyuda.add(mntmAcercaDe);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 630, 120, 0 };
        gridBagLayout.rowHeights = new int[] { 640, 0 };
        gridBagLayout.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
        this.frmLectorHgt.getContentPane().setLayout(gridBagLayout);

        this.labelImagen = new JLabel();
        this.labelImagen.setHorizontalAlignment(SwingConstants.CENTER);
        // frmLectorHgt.getContentPane().add(labelImagen, BorderLayout.);

        JScrollPane panelImagen = new JScrollPane();
        panelImagen.setViewportView(this.labelImagen);
        GridBagConstraints gbc_panelImagen = new GridBagConstraints();
        gbc_panelImagen.fill = GridBagConstraints.BOTH;
        gbc_panelImagen.insets = new Insets(0, 0, 0, 5);
        gbc_panelImagen.gridx = 0;
        gbc_panelImagen.gridy = 0;
        this.frmLectorHgt.getContentPane().add(panelImagen, gbc_panelImagen);

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 1;
        gbc_scrollPane.gridy = 0;
        this.frmLectorHgt.getContentPane().add(scrollPane, gbc_scrollPane);

        JPanel panelReferencias = new JPanel();
        panelReferencias.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Referencias",
                TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
        GridBagConstraints gbc_panelReferencias = new GridBagConstraints();
        gbc_panelReferencias.anchor = GridBagConstraints.NORTH;
        gbc_panelReferencias.fill = GridBagConstraints.HORIZONTAL;
        gbc_panelReferencias.gridx = 1;
        gbc_panelReferencias.gridy = 0;
        scrollPane.setViewportView(panelReferencias);
        // frmLectorHgt.getContentPane().add(panelReferencias,
        // gbc_panelReferencias);

        this.gblReferencias = new PanelReferencias(this.imagen);
        panelReferencias.add(this.gblReferencias);

        // abrirArchivo(new File("hgt/N00E010.hgt"));
        seleccionarPaleta(new File("paletas/default.pml"));
        this.size = 600;

    }

    public void refrescar() {
        if (this.cargado) {
            this.imagen.repaint();
            this.imagen.mostrar(this.labelImagen, this.size);
        }
    }

    // Salir de la aplicacion
    protected void salir() {
        this.frmLectorHgt.dispose();
    }

    // Configurar paleta
    void seleccionarPaleta(File f) {
        this.imagen.setPaleta(new PaletaArchivo(f, this.chckbxmntmInterpolar.isSelected()));
        this.gblReferencias.agregarReferencias();
        refrescar();
    }

}
