package tile_Interativo;

import java.awt.Rectangle;

import principal.GamePanel;

public class BlocoLixo extends BlocoInterativo{
	
	GamePanel gp;
	
	public BlocoLixo(GamePanel gp, int col, int lin) {
		super(gp, col, lin);
		this.gp = gp;
		
		this.setMundoX(gp.getTileSize() * col);
		this.setMundoY(gp.getTileSize() * lin);
		
		this.setDirecao("baixo");
		baixo1 = setup("/tiles_Interativos/lixo_alfa.png");
		destruivel = true;
	}
}
