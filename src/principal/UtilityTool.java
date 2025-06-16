package principal;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
* Classe utilitária para operações gráficas, como redimensionamento de imagens.
* 
* <p>
* Utilizada principalmente para otimizar a performance do jogo, permitindo
* que imagens sejam redimensionadas e desenhadas com maior eficiência.
* </p>
* 
* @author Mateus
* @version 
* @since 
*/
public class UtilityTool {
	
	/**
     * Redimensiona uma imagem para a largura e altura especificadas.
     *
     * @param original A imagem original a ser redimensionada.
     * @param width    A nova largura da imagem.
     * @param height   A nova altura da imagem.
     * @return Um novo {@link BufferedImage} com a imagem redimensionada.
     */
	public BufferedImage scaleImage(BufferedImage original, int width, int height) {
		BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(original, 0, 0, width, height, null);
		g2.dispose();
		
		return scaledImage;
	}

}
