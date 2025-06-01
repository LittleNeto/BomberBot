package principal;

import inimigo.BotFacil;
import objeto.OBJ_Porta;
/**
 * 
 * @author usuario
 */

//ESSA CLASSE PODE SER ADAPTADA PARA CRIAR CADA FASE

public class AssetSetter {
    GamePanel gp;
    
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }
    
    public void setObject() {
    	
    	gp.obj[1] = new OBJ_Porta();
    	gp.obj[1].mundoX = gp.getTileSize() * 2;
    	gp.obj[1].mundoY = gp.getTileSize() * 1;
    }
    public void setBot() {
        gp.monstros[0] = new BotFacil(gp);
        gp.monstros[0].setMundoX(gp.getTileSize() * 5);
        gp.monstros[0].setMundoY(gp.getTileSize() * 2);
        
        gp.monstros[1] = new BotFacil(gp);
        gp.monstros[1].setMundoX(gp.getTileSize() * 28);
        gp.monstros[1].setMundoY(gp.getTileSize() * 7);
    }
        
}
