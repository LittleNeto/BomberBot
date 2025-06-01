package objeto;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Porta extends SuperObjeto{
	
	public OBJ_Porta() {
		nome = "Porta";
		try {
			imagem = ImageIO.read(getClass().getResourceAsStream("/objetos/porta.png"));
		} catch(IOException e) {
			
		}
		
		colisao = true;
	}
	
}
