package filtro;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FiltroArchivoPNG extends FileFilter {

	@Override
	public boolean accept(File f) {
		return f.getName().endsWith("png") || f.isDirectory();
	}

	@Override
	public String getDescription() {
		return "Imagen PNG (*.png)";
	}

}
