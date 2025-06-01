package objeto;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Vida extends SuperObjeto{
	public OBJ_Vida() {
		nome = "Vida";
		try {
			imagem = ImageIO.read(getClass().getResourceAsStream("/objetos/vida_cheia.png"));
			imagem1 = ImageIO.read(getClass().getResourceAsStream("/objetos/vida_media.png"));
			imagem2 = ImageIO.read(getClass().getResourceAsStream("/objetos/vida_baixa.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}

	}
}
