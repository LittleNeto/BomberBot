package tile;

import java.awt.image.BufferedImage;

/**
 * Classe que representa o bloco que se fará presente
 * durante o decorrer da fase.
 * 
 * @author Mateus
 * @version
 * @since
 */
public class Tile {
	
	/**Imagem que representa o bloco */
	private BufferedImage imagem;
	/**Representa se houve colisão com o bloco */
	private boolean colisao = false;
	
	//setters
	/** @param imagem Adiciona a imagem do bloco em questão. */
	public void setImagem(BufferedImage imagem) {
		this.imagem = imagem;
	}
	
	/** @param colisao Registra no atributo se houve colisão. */
	public void setColisao(boolean colisao) {
		this.colisao = colisao;
	}
	
	//getters
	/** @return A imagem do bloco em questão. */
	public BufferedImage getImagem() {
		return imagem;
	}
	
	/** @return Se houve colisão com o bloco. */
	public boolean getColisao() {
		return colisao;
	}
	
}
