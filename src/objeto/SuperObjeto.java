package objeto;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import principal.GamePanel;
import principal.UtilityTool;

/**
 * Classe que serve de referência para criar objetos específicos
 * que serão utilizados durante o funcionamento do jogo.
 * 
 * @author Mateus
 * @version
 * @since
 */
public class SuperObjeto {
	/**As imagens que representam o objeto, que será constantemente atualizado */
	public BufferedImage imagem, imagem1, imagem2, imagem3;
	/**O nome do objeto que será criado com base nesta classe. */
	public String nome;
	/**Registra se houve colisão com o objeto em questão. */
	public boolean colisao = false;
	/** A posição X e Y do objeto no mundo (coordenada global). */
	public int mundoX, mundoY;
	/**Área retangular que representa a 'hitbox' do objeto, usada para detecção de colisão.*/
	public Rectangle areaSolida = new Rectangle(32, 32, 32, 32); //centraliza a 'hitbox' do objeto
	
	/** Valor padrão da coordenada X da área sólida, útil para restaurar a posição original após ajustes temporários
	 * na hitbox.
	 */
	public int areaSolidaDefaultX = 0;
	/** Valor padrão da coordenada Y da área sólida, útil para restaurar a posição original após ajustes temporários
	 * na hitbox.
	 */
	public int areaSolidaDefaultY = 0;
	/**Objeto utilizado para operações gráficas, como redimensionamento de imagens. */
	UtilityTool uTool = new UtilityTool();
	
	/**
     * Função que vai adicionando as imagens no mapa de acordo
     * com a situação atual do objeto.
     */
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
