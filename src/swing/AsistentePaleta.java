/**
 *
 */
package swing;

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

import filtro.FiltroArchivoPaleta;
import image.Imagen;
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
        this.panelContenedor = new JPanel();
        getContentPane().add(this.panelContenedor, BorderLayout.NORTH);
        GridBagLayout gbl_panelContenedor = new GridBagLayout();
        gbl_panelContenedor.columnWidths = new int[] { 197, 221, 0 };
        gbl_panelContenedor.rowHeights = new int[] { 69, 0, 0 };
        gbl_panelContenedor.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
        gbl_panelContenedor.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        this.panelContenedor.setLayout(gbl_panelContenedor);
        {
            this.panelPaleta = new JPanel();
            GridBagConstraints gbc_panelPaleta = new GridBagConstraints();
            gbc_panelPaleta.fill = GridBagConstraints.HORIZONTAL;
            gbc_panelPaleta.gridwidth = 2;
            gbc_panelPaleta.insets = new Insets(5, 5, 5, 5);
            gbc_panelPaleta.gridx = 0;
            gbc_panelPaleta.gridy = 0;
            this.panelContenedor.add(this.panelPaleta, gbc_panelPaleta);
            this.panelPaleta.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Paleta",
                    TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
            this.btnAplicar = new JButton("Aplicar");
            this.btnAplicar.setFont(new Font("Tahoma", Font.PLAIN, 15));
            this.panelPaleta.add(this.btnAplicar);
            {
                this.btnResetear = new JButton("Resetear");
                this.btnResetear.setFont(new Font("Tahoma", Font.PLAIN, 15));
                this.panelPaleta.add(this.btnResetear);
                {
                    this.btnCargar = new JButton("Cargar");
                    this.btnCargar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            cargarPaleta();
                        }
                    });
                    this.btnCargar.setFont(new Font("Tahoma", Font.PLAIN, 15));
                    this.panelPaleta.add(this.btnCargar);
                }
                {
                    this.btnGuardar = new JButton("Guardar");
                    this.btnGuardar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            JFileChooser fc = new JFileChooser("paletas");

                            // Mostrar la ventana para abrir archivo y recoger
                            // la respuesta
                            fc.setFileFilter(new FiltroArchivoPaleta());
                            int respuesta = fc.showSaveDialog(null);

                            // Comprobar si se ha pulsado Aceptar
                            if (respuesta == JFileChooser.APPROVE_OPTION) {
                                String path = fc.getSelectedFile().getAbsolutePath();

                                if (!path.endsWith(".pml")) {
                                    path += ".pml";
                                }

                                AsistentePaleta.this.paleta.save(new File(path));
                            }
                        }
                    });
                    this.btnGuardar.setFont(new Font("Tahoma", Font.PLAIN, 15));
                    this.panelPaleta.add(this.btnGuardar);
                }
                this.btnResetear.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        AsistentePaleta.this.paleta.reset();
                        actualizarReferencias();
                        lector.refrescar();
                    }
                });
            }
            this.btnAplicar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    lector.getGblReferencias().agregarReferencias();
                    lector.refrescar();
                }
            });
        }
        this.panelReferencias = new JPanel();
        GridBagConstraints gbc_panelReferencias = new GridBagConstraints();
        gbc_panelReferencias.fill = GridBagConstraints.BOTH;
        gbc_panelReferencias.insets = new Insets(0, 5, 0, 5);
        gbc_panelReferencias.gridx = 0;
        gbc_panelReferencias.gridy = 1;
        this.panelContenedor.add(this.panelReferencias, gbc_panelReferencias);
        this.panelReferencias.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Referencias",
                TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        this.panel = new JPanel();
        this.panelReferencias.add(this.panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] { 69, 101, 0 };
        gbl_panel.rowHeights = new int[] { 39, 0, 0 };
        gbl_panel.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
        gbl_panel.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
        this.panel.setLayout(gbl_panel);
        {
            this.scrollPane = new JScrollPane();
            GridBagConstraints gbc_scrollPane = new GridBagConstraints();
            gbc_scrollPane.gridwidth = 2;
            gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
            gbc_scrollPane.fill = GridBagConstraints.BOTH;
            gbc_scrollPane.gridx = 0;
            gbc_scrollPane.gridy = 0;
            this.panel.add(this.scrollPane, gbc_scrollPane);
            actualizarReferencias();
        }
        this.btnEliminar = new JButton("Eliminar");
        this.btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 15));
        GridBagConstraints gbc_btnEliminar = new GridBagConstraints();
        gbc_btnEliminar.insets = new Insets(0, 0, 0, 5);
        gbc_btnEliminar.gridx = 0;
        gbc_btnEliminar.gridy = 1;
        this.panel.add(this.btnEliminar, gbc_btnEliminar);
        this.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                int index = AsistentePaleta.this.petList.getSelectedIndex();
                // System.out.println("Seleccionado :" + index);
                AsistentePaleta.this.paleta.removeIndex(index);
                actualizarReferencias();
            }
        });
        {
            this.btnEliminarTodas = new JButton("Eliminar Todas");
            this.btnEliminarTodas.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    int size = AsistentePaleta.this.paleta.getColores().size();
                    for (int i = 0; i < size; i++) {
                        int index = AsistentePaleta.this.petList.getSelectedIndex();
                        AsistentePaleta.this.paleta.removeIndex(index);
                        actualizarReferencias();
                    }
                }
            });
            this.btnEliminarTodas.setFont(new Font("Tahoma", Font.PLAIN, 15));
            GridBagConstraints gbc_btnEliminarTodas = new GridBagConstraints();
            gbc_btnEliminarTodas.gridx = 1;
            gbc_btnEliminarTodas.gridy = 1;
            this.panel.add(this.btnEliminarTodas, gbc_btnEliminarTodas);
        }
        this.panelAgregarReferencia = new JPanel();
        GridBagConstraints gbc_panelAgregarReferencia = new GridBagConstraints();
        gbc_panelAgregarReferencia.insets = new Insets(0, 5, 0, 5);
        gbc_panelAgregarReferencia.fill = GridBagConstraints.HORIZONTAL;
        gbc_panelAgregarReferencia.anchor = GridBagConstraints.NORTH;
        gbc_panelAgregarReferencia.gridx = 1;
        gbc_panelAgregarReferencia.gridy = 1;
        this.panelContenedor.add(this.panelAgregarReferencia, gbc_panelAgregarReferencia);
        this.panelAgregarReferencia.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
                "Agregar Referencia", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        this.gblAgregarReferencia = new JPanel();
        this.panelAgregarReferencia.add(this.gblAgregarReferencia);
        GridBagLayout gbl_gblAgregarReferencia = new GridBagLayout();
        gbl_gblAgregarReferencia.columnWidths = new int[] { 47, 47, 47, 0 };
        gbl_gblAgregarReferencia.rowHeights = new int[] { 20, 0, 0, 0, 0, 0 };
        gbl_gblAgregarReferencia.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
        gbl_gblAgregarReferencia.rowWeights = new double[] { 1.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
        this.gblAgregarReferencia.setLayout(gbl_gblAgregarReferencia);
        {
            this.lblRojo = new JLabel("Rojo");
            this.lblRojo.setFont(new Font("Arial", Font.BOLD, 13));
            GridBagConstraints gbc_lblRojo = new GridBagConstraints();
            gbc_lblRojo.anchor = GridBagConstraints.EAST;
            gbc_lblRojo.insets = new Insets(0, 0, 5, 5);
            gbc_lblRojo.gridx = 0;
            gbc_lblRojo.gridy = 0;
            this.gblAgregarReferencia.add(this.lblRojo, gbc_lblRojo);
        }
        this.spinRed = new JSpinner();
        GridBagConstraints gbc_spinRed = new GridBagConstraints();
        gbc_spinRed.anchor = GridBagConstraints.WEST;
        gbc_spinRed.insets = new Insets(0, 0, 5, 5);
        gbc_spinRed.gridx = 1;
        gbc_spinRed.gridy = 0;
        this.gblAgregarReferencia.add(this.spinRed, gbc_spinRed);
        this.spinRed.setToolTipText("Rojo");
        this.spinRed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent arg0) {
                actualizarMuestraActual();
            }
        });
        this.spinRed.setModel(new SpinnerNumberModel(0, 0, 255, 1));
        {
            this.lblMuestra = new JLabel("");
            GridBagConstraints gbc_lblMuestra = new GridBagConstraints();
            gbc_lblMuestra.fill = GridBagConstraints.VERTICAL;
            gbc_lblMuestra.weighty = 40.0;
            gbc_lblMuestra.weightx = 40.0;
            gbc_lblMuestra.gridheight = 3;
            gbc_lblMuestra.insets = new Insets(0, 0, 5, 0);
            gbc_lblMuestra.gridx = 2;
            gbc_lblMuestra.gridy = 0;
            this.lblMuestra.setIcon(new ImageIcon(new ImgMuestra(Color.BLACK, 100, 100)));
            this.gblAgregarReferencia.add(this.lblMuestra, gbc_lblMuestra);
        }
        {
            this.lblVerde = new JLabel("Verde");
            this.lblVerde.setFont(new Font("Arial", Font.BOLD, 13));
            GridBagConstraints gbc_lblVerde = new GridBagConstraints();
            gbc_lblVerde.anchor = GridBagConstraints.EAST;
            gbc_lblVerde.insets = new Insets(0, 0, 5, 5);
            gbc_lblVerde.gridx = 0;
            gbc_lblVerde.gridy = 1;
            this.gblAgregarReferencia.add(this.lblVerde, gbc_lblVerde);
        }
        {
            this.spinGreen = new JSpinner();
            this.spinGreen.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent arg0) {
                    actualizarMuestraActual();
                }
            });
            GridBagConstraints gbc_spinGreen = new GridBagConstraints();
            gbc_spinGreen.anchor = GridBagConstraints.WEST;
            gbc_spinGreen.insets = new Insets(0, 0, 5, 5);
            gbc_spinGreen.gridx = 1;
            gbc_spinGreen.gridy = 1;
            this.gblAgregarReferencia.add(this.spinGreen, gbc_spinGreen);
            this.spinGreen.setToolTipText("Verde");
            this.spinGreen.setModel(new SpinnerNumberModel(0, 0, 255, 1));
        }
        {
            this.lblAzul = new JLabel("Azul");
            this.lblAzul.setFont(new Font("Arial", Font.BOLD, 13));
            GridBagConstraints gbc_lblAzul = new GridBagConstraints();
            gbc_lblAzul.anchor = GridBagConstraints.EAST;
            gbc_lblAzul.insets = new Insets(0, 0, 5, 5);
            gbc_lblAzul.gridx = 0;
            gbc_lblAzul.gridy = 2;
            this.gblAgregarReferencia.add(this.lblAzul, gbc_lblAzul);
        }
        {
            this.spinBlue = new JSpinner();
            this.spinBlue.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent arg0) {
                    actualizarMuestraActual();
                }
            });
            GridBagConstraints gbc_spinBlue = new GridBagConstraints();
            gbc_spinBlue.insets = new Insets(0, 0, 5, 5);
            gbc_spinBlue.anchor = GridBagConstraints.WEST;
            gbc_spinBlue.gridx = 1;
            gbc_spinBlue.gridy = 2;
            this.gblAgregarReferencia.add(this.spinBlue, gbc_spinBlue);
            this.spinBlue.setToolTipText("Azul");
            this.spinBlue.setModel(new SpinnerNumberModel(0, 0, 255, 1));
        }
        {
            this.lblLimite = new JLabel("Limite");
            this.lblLimite.setFont(new Font("Arial", Font.BOLD, 13));
            GridBagConstraints gbc_lblLimite = new GridBagConstraints();
            gbc_lblLimite.anchor = GridBagConstraints.EAST;
            gbc_lblLimite.insets = new Insets(0, 0, 5, 5);
            gbc_lblLimite.gridx = 0;
            gbc_lblLimite.gridy = 3;
            this.gblAgregarReferencia.add(this.lblLimite, gbc_lblLimite);
        }
        {
            this.spinLimite = new JSpinner();
            GridBagConstraints gbc_spinLimite = new GridBagConstraints();
            gbc_spinLimite.insets = new Insets(0, 0, 5, 0);
            gbc_spinLimite.anchor = GridBagConstraints.WEST;
            gbc_spinLimite.gridwidth = 2;
            gbc_spinLimite.gridx = 1;
            gbc_spinLimite.gridy = 3;
            this.gblAgregarReferencia.add(this.spinLimite, gbc_spinLimite);
            this.spinLimite.setModel(new SpinnerNumberModel(0, 0, 65535, 1));
            this.spinLimite.setToolTipText("Limite");
        }
        this.btnAgregar = new JButton("Aceptar");
        this.btnAgregar.setFont(new Font("Tahoma", Font.PLAIN, 15));
        GridBagConstraints gbc_btnAgregar = new GridBagConstraints();
        gbc_btnAgregar.gridwidth = 3;
        gbc_btnAgregar.insets = new Insets(5, 5, 5, 5);
        gbc_btnAgregar.gridx = 0;
        gbc_btnAgregar.gridy = 4;
        this.gblAgregarReferencia.add(this.btnAgregar, gbc_btnAgregar);
        this.btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                int r = (int) AsistentePaleta.this.spinRed.getValue();
                int g = (int) AsistentePaleta.this.spinGreen.getValue();
                int b = (int) AsistentePaleta.this.spinBlue.getValue();
                int limite = (int) AsistentePaleta.this.spinLimite.getValue();

                AsistentePaleta.this.paleta.addColor(new Color(r, g, b), limite);
                actualizarReferencias();
            }
        });
    }

    protected void actualizarMuestraActual() {
        int r = (int) this.spinRed.getValue();
        int g = (int) this.spinGreen.getValue();
        int b = (int) this.spinBlue.getValue();

        this.lblMuestra.setIcon(new ImageIcon(new ImgMuestra(new Color(r, g, b), 100, 100)));
    }

    private void actualizarReferencias() {
        if (this.petList != null) {
            this.petList.setCellRenderer(null);
        }

        this.petList = new JList();
        this.petList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.paleta = this.imagen.getPaleta();

        // Elementos iniciales
        this.elementosMuestra = new DefaultListModel<String>();
        for (int i = 0; i < this.paleta.getValores().size(); i++) {
            this.elementosMuestra.addElement("" + i);
        }

        // System.out.println("Referencias: " + paleta.getValores().size());
        this.petList.setModel(this.elementosMuestra);
        this.petList.setSelectedIndex(0);
        ColorListRenderer elem = new ColorListRenderer(this, this.imagen.getPaleta());
        elem.setPreferredSize(new Dimension(150, 30));
        this.petList.setCellRenderer(elem);
        // panel_1.add(petList);
        this.scrollPane.setViewportView(this.petList);

        this.petList.updateUI();

    }

    protected void cargarPaleta() {
        JFileChooser fc = new JFileChooser("paletas");

        // Mostrar la ventana para abrir archivo y recoger la respuesta
        fc.setFileFilter(new FiltroArchivoPaleta());
        int respuesta = fc.showOpenDialog(null);

        // Comprobar si se ha pulsado Aceptar
        if (respuesta == JFileChooser.APPROVE_OPTION) {
            this.lector.seleccionarPaleta(fc.getSelectedFile());
            actualizarReferencias();
        }

    }

    public void setMuestra(int r, int g, int b, int limit) {
        this.spinRed.setValue(r);
        this.spinGreen.setValue(g);
        this.spinBlue.setValue(b);
        this.spinLimite.setValue(limit);

        actualizarMuestraActual();
    }

}
