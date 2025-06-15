package fase;

import objeto.OBJ_Porta;
import principal.GamePanel;
import recursos.GeradorMapa;
import inimigo.BotDificil;
import inimigo.BotFacil;
import inimigo.BotMedio;

public class Fase3Setter implements FaseSetter {
	GamePanel gp;
	GeradorMapa gMapa;
	final int QTD_BOTS = 6;

	public Fase3Setter(GamePanel gp, GeradorMapa gMapa) {
		this.gp = gp;
		this.gMapa = gMapa;
	}
	
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
