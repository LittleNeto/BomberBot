package tile;

import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import principal.GamePanel;
import principal.GeradorMapas;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		tile = new Tile[10]; //lista dos tipos diferentes de blocos que terão no jogo
		mapTileNum = new int[gp.maxMundoLin][gp.maxMundoCol]; //matriz que vai guardar os tipos de blocos do mapa
		
		getImagemTile();
		carregarMapa();
	}
	
	public void getImagemTile() {
		
		try {
			
			tile[0] = new Tile();
			tile[0].imagem = ImageIO.read(getClass().getResourceAsStream("/tiles/terra_alfa.png"));
			
			tile[1] = new Tile();
			tile[1].imagem = ImageIO.read(getClass().getResourceAsStream("/tiles/parede_alfa.png"));
			tile[1].colisao = true;
			
			tile[2] = new Tile();
			tile[2].imagem = ImageIO.read(getClass().getResourceAsStream("/tiles/lixo_alfa.png"));
			tile[2].colisao = true;
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void carregarMapa() {
		mapTileNum = GeradorMapas.gerarMapa(10, 31);
	}
	
	public void desenhar(Graphics2D g2) {
	    for (int lin = 0; lin < gp.maxMundoLin; lin++) {
	        for (int col = 0; col < gp.maxMundoCol; col++) {
	            int tileNum = mapTileNum[lin][col];

	            int mundoX = col * gp.tileSize;
	            int mundoY = lin * gp.tileSize;

	            int telaX = mundoX - gp.jogador.mundoX + gp.jogador.telaX;

	            // ajusta a lógica para que a tela para de se mexer perto da borda do início
	            if (gp.jogador.mundoX < gp.jogador.telaX) {
	                telaX = mundoX;
	            }

	            // a mesma lógica mas para a margem do final 
	            int margemDireita = gp.tileSize * gp.maxMundoCol - gp.screenWidth + gp.jogador.telaX;
	            if (gp.jogador.mundoX > margemDireita) {
	                telaX = mundoX - (gp.tileSize * gp.maxMundoCol - gp.screenWidth);
	            }

	            // Desenha apenas se o tile estiver visível na tela
	            if (telaX + gp.tileSize > 0 && telaX < gp.screenWidth) {
	                g2.drawImage(tile[tileNum].imagem, telaX, mundoY, gp.tileSize, gp.tileSize, null);
	            }
	        }
	    }
	}


}
