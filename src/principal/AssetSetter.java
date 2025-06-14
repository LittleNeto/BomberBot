package principal;

import inimigo.BotDificil;
import inimigo.BotFacil;
import inimigo.BotMedio;
import objeto.OBJ_Porta;
import recursos.GeradorMapa;
import tile_Interativo.BlocoLixo;
/**
 * 
 * @author usuario
 */

//ESSA CLASSE PODE SER ADAPTADA PARA CRIAR CADA FASE

public class AssetSetter {
    GamePanel gp;
    GeradorMapa gMapa;
    
    public AssetSetter(GamePanel gp, GeradorMapa gMapa) {
        this.gp = gp;
        this.gMapa = gMapa;
    }
    
    public void setObject() {
    	
    	gp.obj[0] = new OBJ_Porta(gp);
    	gp.obj[0].mundoX = gp.getTileSize() * this.gMapa.posicaoPorta[0][1];
    	gp.obj[0].mundoY = gp.getTileSize() * this.gMapa.posicaoPorta[0][0];
    }
  
    public void setBot() {
<<<<<<< HEAD
        gp.monstros[0] = new BotMedio(gp);
        gp.monstros[0].setMundoX(gp.getTileSize() * this.mapa.posicaoInimigos[0][1]);
        gp.monstros[0].setMundoY(gp.getTileSize() * this.mapa.posicaoInimigos[0][0]);
        
        gp.monstros[1] = new BotMedio(gp);
        gp.monstros[1].setMundoX(gp.getTileSize() * this.mapa.posicaoInimigos[1][1]);
        gp.monstros[1].setMundoY(gp.getTileSize() * this.mapa.posicaoInimigos[1][0]);
=======
        gp.monstros[0] = new BotFacil(gp);
        gp.monstros[0].setMundoX(gp.getTileSize() * this.gMapa.posicaoInimigos[0][1]);
        gp.monstros[0].setMundoY(gp.getTileSize() * this.gMapa.posicaoInimigos[0][0]);
        
        gp.monstros[1] = new BotFacil(gp);
        gp.monstros[1].setMundoX(gp.getTileSize() * this.gMapa.posicaoInimigos[1][1]);
        gp.monstros[1].setMundoY(gp.getTileSize() * this.gMapa.posicaoInimigos[1][0]);
>>>>>>> 32a4624db183a45ba979eff9673f30fb31cb795a
        
    }
    
    //CONSERTAR DEPOIS
    public void setBlocoInterativo() {
        int i = 0;
        for (int lin = 0; lin < this.gMapa.getGrade().length; lin++) {
            for (int col = 0; col < this.gMapa.getGrade()[0].length; col++) {
                if (this.gMapa.getGrade()[lin][col] == 2) {
                    this.gp.iTiles[i] = new BlocoLixo(gp, col, lin);
                    i++;
                }
            }
        }
    }

}
