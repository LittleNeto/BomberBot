package fase;

import principal.GamePanel;
import objeto.OBJ_Porta;
import recursos.GeradorMapa;
import tile_Interativo.BlocoLixo;

public interface FaseSetter {
	
		void setInimigos(GamePanel gp);
	
	    default void setObject(GamePanel gp) {
	    	gp.obj[0] = new OBJ_Porta(gp);
	    	gp.obj[0].mundoX = gp.getTileSize() * 2;
	    	gp.obj[0].mundoY = gp.getTileSize() * 1;
	    }
	    
	    default void setBlocoInterativo(GamePanel gp,GeradorMapa gMapa) {
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
}
