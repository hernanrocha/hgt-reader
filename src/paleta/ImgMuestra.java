package paleta;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImgMuestra extends BufferedImage {

	private static final int SIZE = 40;
	
	public ImgMuestra(Color c){
		this(c, SIZE, SIZE/2);
	}
	
	public ImgMuestra(Color c, int width, int height){
		super(width, height, BufferedImage.TYPE_INT_ARGB);
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				if (i > 0 && i < width - 1 && j > 0 && j < height - 1){
					setRGB(i, j, c.getRGB());
				}else{
					setRGB(i, j, Color.BLACK.getRGB());
				}
			}
		}
	}
	
	
	
}
