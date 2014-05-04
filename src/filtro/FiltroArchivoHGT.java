package filtro;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FiltroArchivoHGT extends FileFilter {

	@Override
	public boolean accept(File f) {
		return f.getName().endsWith("hgt") || f.isDirectory();
	}

	@Override
	public String getDescription() {
		return "Archivo HGT (*.hgt)";
	}

}
