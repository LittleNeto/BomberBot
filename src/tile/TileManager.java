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
			
			tile[2] = new Tile();
			tile[2].imagem = ImageIO.read(getClass().getResourceAsStream("/tiles/lixo_alfa.png"));
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void carregarMapa() {
		mapTileNum = GeradorMapas.gerarMapa(gp.maxMundoLin, gp.maxMundoCol);
	}
	
	public void desenhar(Graphics2D g2) {
		
		//CRIAÇÃO DE MAPA (ADAPTAR PARA POO)
		int mundoCol = 0;
		int mundoLin = 0;

		while (mundoCol < gp.maxMundoCol && mundoLin < gp.maxMundoLin) {
			
			int tileNum = mapTileNum[mundoLin][mundoCol];
			
			int mundoX = mundoLin * gp.tileSize;
			int mundoY = mundoCol * gp.tileSize;
			int telaX = mundoX - gp.jogador.mundoX + gp.jogador.telaX;
			int telaY = mundoY - gp.jogador.mundoY + gp.jogador.telaY;
			
			if(mundoX + gp.tileSize> gp.jogador.mundoX - gp.jogador.telaX && mundoX - gp.tileSize< gp.jogador.mundoX + gp.jogador.telaX &&
					mundoY + gp.tileSize> gp.jogador.mundoY - gp.jogador.telaY && mundoY - gp.tileSize < gp.jogador.mundoY + gp.jogador.telaY) {
				
				g2.drawImage(tile[tileNum].imagem, telaX, telaY, gp.tileSize, gp.tileSize, null);

			} //para só desenhar a parte dela que está na visão do jogador ao invés de desenhar tudo de uma vez

			mundoCol++;
			
			if (mundoCol == gp.maxMundoCol) {				
				mundoCol = 0;
				mundoLin++;
			}
		}

	}
}

