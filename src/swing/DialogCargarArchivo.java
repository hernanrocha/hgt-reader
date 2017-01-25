package swing;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

public class DialogCargarArchivo extends JDialog {

    private static final long serialVersionUID = 1L;
    private JProgressBar progressBar;

    public DialogCargarArchivo() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cargando Archivo HGT...");
        setResizable(false);
        setBounds(100, 100, 450, 60);

        this.progressBar = new JProgressBar();
        getContentPane().add(this.progressBar, BorderLayout.CENTER);

    }

    public void setValue(Integer valor) {
        this.progressBar.setValue(valor);

    }

}
