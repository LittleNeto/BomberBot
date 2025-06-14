package principal;

import inimigo.BotFacil;
import objeto.OBJ_Porta;
import recursos.Mapa;
import tile_Interativo.BlocoLixo;
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
    	
    	gp.obj[0] = new OBJ_Porta(gp);
    	gp.obj[0].mundoX = gp.getTileSize() * 2;
    	gp.obj[0].mundoY = gp.getTileSize() * 1;
    }
  
    public void setBot() {
        gp.monstros[0] = new BotFacil(gp);
        gp.monstros[0].setMundoX(gp.getTileSize() * this.mapa.posicaoInimigos[0][1]);
        gp.monstros[0].setMundoY(gp.getTileSize() * this.mapa.posicaoInimigos[0][0]);
        
        gp.monstros[1] = new BotFacil(gp);
        gp.monstros[1].setMundoX(gp.getTileSize() * this.mapa.posicaoInimigos[1][1]);
        gp.monstros[1].setMundoY(gp.getTileSize() * this.mapa.posicaoInimigos[1][0]);
    }
    
    //CONSERTAR DEPOIS
    public void setBlocoInterativo() {
        int i = 0;
        for (int lin = 0; lin < mapa.getGrade().length; lin++) {
            for (int col = 0; col < mapa.getGrade()[0].length; col++) {
                if (mapa.getGrade()[lin][col] == 2) {
                    gp.iTiles[i] = new BlocoLixo(gp, col, lin);
                    i++;
                }
            }
        }
    }

}
