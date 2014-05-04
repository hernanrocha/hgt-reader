package swing;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JProgressBar;

public class DialogCargarArchivo extends JDialog {

	private static final long serialVersionUID = 1L;
	private JProgressBar progressBar;

	public DialogCargarArchivo() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Cargando Archivo HGT...");
		setResizable(false);
		setBounds(100, 100, 450, 60);
		
		progressBar = new JProgressBar();
		getContentPane().add(progressBar, BorderLayout.CENTER);

	}

	public void setValue(Integer valor) {
		progressBar.setValue(valor);
		
	}

}
