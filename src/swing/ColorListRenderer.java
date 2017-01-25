package swing;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

import paleta.ImgMuestra;
import paleta.Paleta;

public class ColorListRenderer extends JLabel implements ListCellRenderer {

    private static final long serialVersionUID = 1L;
    private Paleta paleta;
    private Vector<Color> colores;
    private Vector<Integer> valores;
    private AsistentePaleta asistente;

    public ColorListRenderer(AsistentePaleta asistente, Paleta paleta) {
        this.asistente = asistente;
        this.paleta = paleta;
        this.colores = paleta.getColores();
        this.valores = paleta.getValores();
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
            boolean cellHasFocus) {
        int selectedIndex = Integer.parseInt((String) value);

        if (isSelected) {
            // System.out.println("Selected" + selectedIndex);
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
            setBorder(new LineBorder(Color.BLACK, 2));

            Color c = this.paleta.getColores().get(selectedIndex);
            int limit = this.paleta.getValores().get(selectedIndex);

            this.asistente.setMuestra(c.getRed(), c.getGreen(), c.getBlue(), limit);
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            setBorder(new LineBorder(Color.WHITE));
        }

        // Setear maximo
        setText("" + this.valores.get(selectedIndex));
        // Setear color de muestra
        ImageIcon icon = new ImageIcon(new ImgMuestra(this.colores.get(selectedIndex)));
        setIcon(icon);
        return this;
    }

}
