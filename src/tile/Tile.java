package tile;

import java.awt.image.BufferedImage;

public class Tile {
	
	private BufferedImage imagem;
	private boolean colisao = false;
	
	//setters
	public void setImagem(BufferedImage imagem) {
		this.imagem = imagem;
	}
	
	public void setColisao(boolean colisao) {
		this.colisao = colisao;
	}
	
	//getters
	public BufferedImage getImagem() {
		return imagem;
	}
	
	public boolean getColisao() {
		return colisao;
	}
	
}
