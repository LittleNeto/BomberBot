package principal;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

//feita para ajudar a melhorar a performance do jogo, desenha mais rápido
public class UtilityTool {
	
	public BufferedImage scaleImage(BufferedImage original, int width, int height) {
		BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(original, 0, 0, width, height, null);
		g2.dispose();
		
		return scaledImage;
	}

}
