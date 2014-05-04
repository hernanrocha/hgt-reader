package filtro;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FiltroArchivoPaleta extends FileFilter {

	@Override
	public boolean accept(File f) {
		return f.getName().endsWith("pml") || f.isDirectory();
	}

	@Override
	public String getDescription() {
		return "Archivo Paleta (*.pml)";
	}

}
