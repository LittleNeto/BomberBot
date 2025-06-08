package objeto;

import java.io.IOException;

import javax.imageio.ImageIO;

import principal.GamePanel;

public class OBJ_Vida extends SuperObjeto{
	
	GamePanel gp;
	
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
