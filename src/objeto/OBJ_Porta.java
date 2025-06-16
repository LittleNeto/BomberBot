package objeto;

import java.io.IOException;

import javax.imageio.ImageIO;

import principal.GamePanel;

/**
 * Representa a porta que será responsável por faser o jogador passar para
 * a proxima fase, que é quando os bots são todos eliminados.
 * 
 * Subclasse de {@link objeto.SuperObjeto}
 * 
 * @author Mateus
 * @version
 * @since
 */
public class OBJ_Porta extends SuperObjeto{
	
	/**GamePanel que representa o contexto do jogo. */
	GamePanel gp;
	
	/**
	 * Construtor do OBJ_Porta
	 * 
	 * @param gp GamePanel que representa o contexto do jogo. 
	 */
	public OBJ_Porta(GamePanel gp) {
		nome = "Porta";
		try {
			imagem = ImageIO.read(getClass().getResourceAsStream("/objetos/porta.png"));
			uTool.scaleImage(imagem, gp.getTileSize(), gp.getTileSize());
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		colisao = false; //podemos passar "por cima" da porta
	}
	
}
