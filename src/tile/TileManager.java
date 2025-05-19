package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import principal.GamePanel;
import principal.GeradorMapas;

public class TileManager {
	GamePanel gp;
	Tile[] tile;
	int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		tile = new Tile[10]; //lista dos tipos diferentes de blocos que terão no jogo
		mapTileNum = new int[gp.maxScreenLin][gp.maxScreenCol]; //matriz que vai guardar os tipos de blocos do mapa
		
		getImagemTile();
		carregarMapa();
	}
	
	public void getImagemTile() {
		
		try {
			
			tile[0] = new Tile();
			tile[0].imagem = ImageIO.read(getClass().getResourceAsStream("/tiles/terra_alfa.png"));
			
			tile[1] = new Tile();
			tile[1].imagem = ImageIO.read(getClass().getResourceAsStream("/tiles/parede_alfa.png"));
			
			tile[2] = new Tile();
			tile[2].imagem = ImageIO.read(getClass().getResourceAsStream("/tiles/lixo_alfa.png"));
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void carregarMapa() {
		mapTileNum = GeradorMapas.gerarMapa();
	}
	
	public void desenhar(Graphics2D g2) {
		
		//CRIAÇÃO DE MAPA (ADAPTAR PARA POO)
		int col = 0;
		int lin = 0;
		int x = 0;
		int y = 0;
		
		while (col < gp.maxScreenCol && lin < gp.maxScreenLin) {
			
			int tileNum = mapTileNum[lin][col];
			
			g2.drawImage(tile[tileNum].imagem, x, y, gp.tileSize, gp.tileSize, null);
			col++;
			x += gp.tileSize;
			
			if (col == gp.maxScreenCol) {
				col = 0;
				x = 0;
				lin++;
				y += gp.tileSize;
			}
		}

	}
}
