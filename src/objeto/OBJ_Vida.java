package objeto;

import java.io.IOException;

import javax.imageio.ImageIO;

import principal.GamePanel;

/**
 * Representa o contador da vida do personagem, que é
 * constantemente atualizada a medida que o jogador recebe
 * dano tanto do bot quanto da bomba.
 * 
 * Subclasse de {@link objeto.SuperObjeto}
 * 
 * @author Mateus
 * @version 4.0
 * @since 2025-06-01
 */
public class OBJ_Vida extends SuperObjeto{
	
	/**GamePanel que representa o contexto do jogo.  */
	GamePanel gp;
	
	/**
	 * Construtor do OBJ_Vida.
	 * 
	 * @param gpGamePanel que representa o contexto do jogo. 
	 */
	public OBJ_Vida(GamePanel gp) {
		
		this.gp = gp;
		
		nome = "Vida";
		try {
			imagem = ImageIO.read(getClass().getResourceAsStream("/objetos/vida_cheia.png"));
			imagem1 = ImageIO.read(getClass().getResourceAsStream("/objetos/vida_media.png"));
			imagem2 = ImageIO.read(getClass().getResourceAsStream("/objetos/vida_baixa.png"));
			imagem3 = ImageIO.read(getClass().getResourceAsStream("/objetos/vida_vazia.png"));	
			imagem = uTool.scaleImage(imagem, gp.getTileSize(), gp.getTileSize());
			imagem1 = uTool.scaleImage(imagem1, gp.getTileSize(), gp.getTileSize());
			imagem2 = uTool.scaleImage(imagem2, gp.getTileSize(), gp.getTileSize());
			imagem3 = uTool.scaleImage(imagem3, gp.getTileSize(), gp.getTileSize());
		} catch(IOException e) {
			e.printStackTrace();
		}

	}
}
