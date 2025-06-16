package fase;

import objeto.OBJ_Porta;
import principal.GamePanel;
import recursos.GeradorMapa;
import inimigo.BotDificil;
import inimigo.BotMedio;

/**
 * Representa o modo como a fase 3 será inicializada.
 * A fase contém 3 inimigos com dificuldade média
 * e 3 inimigos com dificuldade difícil.
 * 
 * Implementação da interface {@link fase.FaseSetter}.
 * 
 * @author Mateus
 */
public class Fase3Setter implements FaseSetter {
	/**GamePanel que representa o contexto do jogo. */
	GamePanel gp;
	/**GeradorMapa que representa as configurações do mapa. */
	GeradorMapa gMapa;
	/**A quantidade de inimigos presentes na fase.  */
	final int QTD_BOTS = 6;

	/**
	 * Construtor completo da classe Fase3Setter
	 * 
	 * @param gp GamePanel que representa o contexto do jogo.
	 * @param gMapa GeradorMapa que representa as configurações do mapa.
	 */
	public Fase3Setter(GamePanel gp, GeradorMapa gMapa) {
		this.gp = gp;
		this.gMapa = gMapa;
	}
	
	/**
	* adiciona os inimigos dentro do mapa da fase.
	* 
	* @param gp GamePanel que representa o contexto do jogo.
	*/
	@Override
	public void setInimigos(GamePanel gp) {
		for (int i = 0; i < 3; i++) {
			 gp.monstros[i] = new BotMedio(gp);
			 gp.monstros[i].setMundoX(gp.getTileSize() * this.gMapa.posicaoInimigos[i][1]);
			 gp.monstros[i].setMundoY(gp.getTileSize() * this.gMapa.posicaoInimigos[i][0]);
		}
		for (int i = 3; i < 6; i++) {
			 gp.monstros[i] = new BotDificil(gp);
			 gp.monstros[i].setMundoX(gp.getTileSize() * this.gMapa.posicaoInimigos[i][1]);
			 gp.monstros[i].setMundoY(gp.getTileSize() * this.gMapa.posicaoInimigos[i][0]);
		}
		
	}
	
	/**
	* adiciona o objeto dentro do mapa quando necessário.
	* 
	* @param gp GamePanel que representa o contexto do jogo.
	*/
	@Override
    public void setObject(GamePanel gp) {
    	gp.obj[0] = new OBJ_Porta(gp);
    	gp.obj[0].mundoX = gp.getTileSize() * this.gMapa.posicaoPorta[0][1];
    	gp.obj[0].mundoY = gp.getTileSize() * this.gMapa.posicaoPorta[0][0];
    }

	/** @return o GamePanel presente dentro da classe. */
	public GeradorMapa getgMapa() {
		return gMapa;
	}

	/** @return A quantidade de inimigos presentes na fase. */
	public int getQTD_BOTS() {
		return QTD_BOTS;
	}
	

}
