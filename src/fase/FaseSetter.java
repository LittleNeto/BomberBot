package fase;

import principal.GamePanel;
import recursos.GeradorMapa;
import tile_Interativo.BlocoLixo;

/**
 * Interface responsável por adicionar elementos
 * no mapa durante o jogo, sendo implementada em
 * 3 fases diferentes.
 * 
 * @author Mateus
 */
public interface FaseSetter {
	
		/**
		 * adiciona os inimigos dentro do mapa da fase.
		 * 
		 * @param gp GamePanel que representa o contexto do jogo.
		 */
		void setInimigos(GamePanel gp);
	
		/**
		 * adiciona o objeto dentro do mapa quando necessário.
		 * 
		 * @param gp GamePanel que representa o contexto do jogo.
		 */
	    void setObject(GamePanel gp);
	    
		/**
		 * função padrão responsável por adicionar os blocos de lixo no mapa do jogo.
		 * 
		 * @param gp GamePanel que representa o contexto do jogo.
		 * @param gMapa GeradorMapa que representa as configurações do mapa.
		 */
	    default void setBlocoInterativo(GamePanel gp, GeradorMapa gMapa) {
	    	int i = 0;
	        for (int lin = 0; lin < gMapa.getGrade().length; lin++) {
	            for (int col = 0; col < gMapa.getGrade()[0].length; col++) {
	                if (gMapa.getGrade()[lin][col] == 2) {
	                    gp.iTiles[i] = new BlocoLixo(gp, col, lin);
	                    i++;
	                }
	            }
	        }
	    }

		GeradorMapa getgMapa();
}
