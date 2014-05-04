package paleta;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PaletaArchivo extends Paleta {

	private File archivo;
	
	public PaletaArchivo(File f, boolean interpolar){
		super(interpolar);
		setFile(f);		
		parseXML();
	}
	
	private void setFile(File f) {
		archivo = f;
	}
	
	@Override
	public void reset(){
		colores = new Vector<Color>();	
		valores = new Vector<Integer>();
		parseXML();
	}
	
	@Override
	public void save(File f){
		setFile(f);
		
		// Generar XML
		String ret = System.lineSeparator();
		String xml = "<palette> " + ret;		
		for (int i = 0; i < valores.size(); i++){
			Color c = colores.get(i);
			xml += "  <range limit=\"" + valores.get(i) + "\" red=\"" + c.getRed() + "\" green=\"" + c.getGreen() + "\" blue=\"" + c.getBlue() + "\" />" + ret;			
		}		
		xml += "</palette>";
		
		// Guardar a archivo
		FileWriter w;
		try {
			w = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(w);
			PrintWriter wr = new PrintWriter(bw);
			
			wr.write(xml);
			
			
			wr.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private void parseXML() {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();

			// Load the input XML document, parse it and return an instance of the
			// Document class.
			Document document = builder.parse(archivo);
			
			NodeList nodeList = document.getDocumentElement().getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
//				System.out.println(node.getNodeName());
				
				if (node.getNodeType() == Node.ELEMENT_NODE) {
//					Element elem = (Element) node;

					// Obtener valores de cada rango
					int limit = Integer.parseInt(node.getAttributes().getNamedItem("limit").getNodeValue());
					int red = Integer.parseInt(node.getAttributes().getNamedItem("red").getNodeValue());
					int green = Integer.parseInt(node.getAttributes().getNamedItem("green").getNodeValue());
					int blue = Integer.parseInt(node.getAttributes().getNamedItem("blue").getNodeValue());

					addColor(new Color(red, green, blue), limit);
				}else{
//					System.out.println("Tipo" + node.getNodeType());
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
