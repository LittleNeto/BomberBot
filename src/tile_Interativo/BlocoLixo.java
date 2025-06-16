package tile_Interativo;

import java.awt.Rectangle;

import principal.GamePanel;

/**
 * Classe que representa a presença do bloco de lixo
 * dentro da fase, um tipo especial de bloco que pode
 * ser destruído quando estiver no alcance de uma bomba.
 * 
 * Subclasse de {@link tile_Interativo.BlocoInterativo}
 * 
 * @author Mateus
 * @version
 * @since
 */
public class BlocoLixo extends BlocoInterativo{
	
	/**GamePanel que representa o contexto do jogo.  */
	GamePanel gp;
	
	/**
	 * Construtor do BlocoLixo
	 * 
	 * @param gp GamePanel que representa o contexto do jogo. 
	 * @param col Coluna em que ele está loclizado.
	 * @param lin Linha em que ele está localizado.
	 */
	public BlocoLixo(GamePanel gp, int col, int lin) {
		super(gp, col, lin);
		this.gp = gp;
		
		this.setMundoX(gp.getTileSize() * col);
		this.setMundoY(gp.getTileSize() * lin);
		
		this.setDirecao("baixo");
		baixo1 = setup("/tiles_Interativos/lixo_alfa.png");
		destruivel = true;
	}
}
