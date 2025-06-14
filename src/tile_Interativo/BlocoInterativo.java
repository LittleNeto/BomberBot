package tile_Interativo;

import java.awt.Rectangle;

import entidade.Personagem;
import principal.GamePanel;

public class BlocoInterativo extends Personagem{
	
	GamePanel gp;
	public boolean destruivel = false;
	
	public BlocoInterativo(GamePanel gp, int col, int lin) {
	    super(gp);
	    this.gp = gp;

	    this.mundoX = col * gp.getTileSize();
	    this.mundoY = lin * gp.getTileSize();

	    this.areaSolida = new Rectangle(0, 0, gp.getTileSize(), gp.getTileSize());
	    this.areaSolidaDefaultX = 0;
	    this.areaSolidaDefaultY = 0;

	    this.colisaoLig = true;
	}

	
	public void update() {
		
	}
}
