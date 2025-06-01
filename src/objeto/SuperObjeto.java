package objeto;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import principal.GamePanel;

public class SuperObjeto {
	public BufferedImage imagem, imagem1, imagem2;
	public String nome;
	public boolean colisao = false;
	public int mundoX, mundoY;
	public Rectangle areaSolida = new Rectangle(32, 32, 32, 32); //centraliza a 'hitbox' do objeto
	public int areaSolidaDefaultX = 0;
	public int areaSolidaDefaultY = 0;
	
	public void desenhar(Graphics2D g2, GamePanel gp) {
	    BufferedImage imagem = null;

	    int telaX = gp.getJogador().getTelaX();
	    int telaY = gp.getJogador().getTelaY();

	    //Calcula a posição da câmera no mundo
	    int camX = gp.getJogador().getMundoX() - telaX;
	    int camY = gp.getJogador().getMundoY() - telaY;

	    //Limita a câmera para não passar das bordas do mapa
	    if (camX < 0) {
	        camX = 0;
	    } else if (camX > gp.getTileSize() * gp.getMaxMundoCol() - gp.getScreenWidth()) {
	        camX = gp.getTileSize() * gp.getMaxMundoCol() - gp.getScreenWidth();
	    }

	    if (camY < 0) {
	        camY = 0;
	    } else if (camY > gp.getTileSize() * gp.getMaxMundoLin() - gp.getScreenHeight()) {
	        camY = gp.getTileSize() * gp.getMaxMundoLin() - gp.getScreenHeight();
	    }

	    //Posição do objeto na tela em relação à câmera
	    int drawX = mundoX - camX;
	    int drawY = mundoY - camY;

	    //Verifica se o objeto está visível na tela
	    if (drawX + gp.getTileSize() > 0 &&
	        drawX < gp.getScreenWidth() &&
	        drawY + gp.getTileSize() > 0 &&
	        drawY < gp.getScreenHeight()) {

	        imagem = this.imagem;

	        g2.drawImage(imagem, drawX, drawY, gp.getTileSize(), gp.getTileSize(), null);
	    }
	}

}
