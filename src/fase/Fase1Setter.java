package fase;

import objeto.OBJ_Porta;
import principal.GamePanel;
import recursos.GeradorMapa;
import inimigo.BotDificil;
import inimigo.BotFacil;
import inimigo.BotMedio;

public class Fase1Setter implements FaseSetter {
	GamePanel gp;
	GeradorMapa gMapa;

	public Fase1Setter(GamePanel gp, GeradorMapa gMapa) {
		this.gp = gp;
		this.gMapa = gMapa;
	}
	
	@Override
	public void setInimigos(GamePanel gp) {
		 gp.monstros[0] = new BotFacil(gp);
		 gp.monstros[0].setMundoX(gp.getTileSize() * this.gMapa.posicaoInimigos[0][1]);
		 gp.monstros[0].setMundoY(gp.getTileSize() * this.gMapa.posicaoInimigos[0][0]);
 
		 gp.monstros[1] = new BotFacil(gp);
		 gp.monstros[1].setMundoX(gp.getTileSize() * this.gMapa.posicaoInimigos[1][1]);
		 gp.monstros[1].setMundoY(gp.getTileSize() * this.gMapa.posicaoInimigos[1][0]);
		 
		 gp.monstros[2] = new BotFacil(gp);
		 gp.monstros[2].setMundoX(gp.getTileSize() * this.gMapa.posicaoInimigos[2][1]);
		 gp.monstros[2].setMundoY(gp.getTileSize() * this.gMapa.posicaoInimigos[2][0]);
 
		 gp.monstros[3] = new BotFacil(gp);
		 gp.monstros[3].setMundoX(gp.getTileSize() * this.gMapa.posicaoInimigos[3][1]);
		 gp.monstros[3].setMundoY(gp.getTileSize() * this.gMapa.posicaoInimigos[3][0]);
		 
		 gp.monstros[4] = new BotFacil(gp);
		 gp.monstros[4].setMundoX(gp.getTileSize() * this.gMapa.posicaoInimigos[4][1]);
		 gp.monstros[4].setMundoY(gp.getTileSize() * this.gMapa.posicaoInimigos[4][0]);
 
		 gp.monstros[5] = new BotFacil(gp);
		 gp.monstros[5].setMundoX(gp.getTileSize() * this.gMapa.posicaoInimigos[5][1]);
		 gp.monstros[5].setMundoY(gp.getTileSize() * this.gMapa.posicaoInimigos[5][0]);
		
	}
	
	@Override
    public void setObject(GamePanel gp) {
    	gp.obj[0] = new OBJ_Porta(gp);
    	gp.obj[0].mundoX = gp.getTileSize() * this.gMapa.posicaoPorta[0][1];
    	gp.obj[0].mundoY = gp.getTileSize() * this.gMapa.posicaoPorta[0][0];
    }

	public GeradorMapa getgMapa() {
		return gMapa;
	}
	

}
