package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import principal.GamePanel;

public class TileManager {
	GamePanel gp;
	Tile[] tile;
	int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		tile = new Tile[10]; //lista dos tipos diferentes de blocos que terão no jogo
		mapTileNum = new int[gp.maxScreenCol][gp.maxScreenLin]; //matriz que vai guardar os tipos de blocos do mapa
		//a lógica de gerar o mapa vai ser através de um arquivo .txt
		//SUGESTÃO: CRIAR UM MÉTODO QUE GERE MAPAS ALEATORIAMENTE A PARTIR DE UM .TXT COM REGRAS PRÉ-ESTABELECIDAS
		
		getImagemTile();
		carregarMapa("/mapas/mapa01.txt");
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
	
	public void carregarMapa(String filePath) {
		try {
			
			InputStream is = getClass().getResourceAsStream(filePath); //importa o txt
			BufferedReader br = new BufferedReader(new InputStreamReader(is)); //lê o txt
			
			int col = 0;
			int lin = 0;
			
			while(col < gp.maxScreenCol && lin < gp.maxScreenLin) { //para ler o txt
				String linha = br.readLine(); //lê a linha
				
				while (col < gp.maxScreenCol) {
					
					String numeros[] = linha.split(" "); //cria uma lista com os números separando com o " " (espaço)
					
					int num = Integer.parseInt(numeros[col]);//usando 'col' como um index
					
					mapTileNum[col][lin] = num;
					col++;
				}
				if (col == gp.maxScreenCol) {
					col = 0;
					lin++;
				}
				
			}
			br.close();
			
		} catch (IOException e) {
			
		}
	}
	
	public void desenhar(Graphics2D g2) {
		
		//CRIAÇÃO DE MAPA (ADAPTAR PARA POO)
		int col = 0;
		int lin = 0;
		int x = 0;
		int y = 0;
		
		while (col < gp.maxScreenCol && lin < gp.maxScreenLin) {
			
			int tileNum = mapTileNum[col][lin];
			
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
