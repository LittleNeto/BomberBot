package fase;

import principal.GamePanel;
import recursos.GeradorMapa;
import tile_Interativo.BlocoLixo;

public interface FaseSetter {
	
		void setInimigos(GamePanel gp);
	
	    void setObject(GamePanel gp);
	    
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
