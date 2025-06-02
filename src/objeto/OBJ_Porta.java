package objeto;

import java.io.IOException;

import javax.imageio.ImageIO;

import principal.GamePanel;

public class OBJ_Porta extends SuperObjeto{
	
	GamePanel gp;
	
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
