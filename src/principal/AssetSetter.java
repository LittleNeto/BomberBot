package principal;

import inimigo.BotFacil;
import objeto.OBJ_Porta;
import recursos.Mapa;
/**
 * 
 * @author usuario
 */

//ESSA CLASSE PODE SER ADAPTADA PARA CRIAR CADA FASE

public class AssetSetter {
    GamePanel gp;
    Mapa mapa;
    
    public AssetSetter(GamePanel gp, Mapa mapa) {
        this.gp = gp;
        this.mapa = mapa;
    }
    
    public void setObject() {
    	
    	gp.obj[1] = new OBJ_Porta(gp);
    	gp.obj[1].mundoX = gp.getTileSize() * 2;
    	gp.obj[1].mundoY = gp.getTileSize() * 1;
    }
    public void setBot() {
        gp.monstros[0] = new BotFacil(gp);
        gp.monstros[0].setMundoX(gp.getTileSize() * this.mapa.posicaoInimigos[0][1]);
        gp.monstros[0].setMundoY(gp.getTileSize() * this.mapa.posicaoInimigos[0][0]);
        
        gp.monstros[1] = new BotFacil(gp);
        gp.monstros[1].setMundoX(gp.getTileSize() * this.mapa.posicaoInimigos[1][1]);
        gp.monstros[1].setMundoY(gp.getTileSize() * this.mapa.posicaoInimigos[1][0]);
    }
        
}