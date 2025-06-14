package tile;

import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import principal.GamePanel;
import principal.UtilityTool;
import recursos.Mapa;

public class TileManager {
	private GamePanel gp;
	private Tile[] tile;
	private int mapTileNum[][];
	private Mapa mapa;
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		this.tile = new Tile[10]; //lista dos tipos diferentes de blocos que terão no jogo
		this.mapTileNum = new int[gp.getMaxMundoLin()][gp.getMaxMundoCol()]; //matriz que vai guardar os tipos de blocos do mapa
		
		this.mapa = new Mapa();
		
		this.getImagemTile();
		this.carregarMapa();
	}
	
	public void getImagemTile() {
		
		setup(0, "terra_alfa", false);
		setup(1, "parede_alfa", true);
		setup(2, "terra_alfa", false);

	}
	
	public void setup(int index, String imagePath, boolean colisao) {
		UtilityTool uTool = new UtilityTool();
		try {
			
			tile[index] = new Tile();
			tile[index].setImagem(ImageIO.read(getClass().getResourceAsStream("/tiles/"+ imagePath + ".png")));
			tile[index].setImagem(uTool.scaleImage(tile[index].getImagem(), gp.getTileSize(), gp.getTileSize()));
			tile[index].setColisao(colisao);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void carregarMapa() {
		this.setMapTileNum(mapa.getGrade());
	}
	
	public void desenhar(Graphics2D g2) {
	    for (int lin = 0; lin < this.getGp().getMaxMundoLin(); lin++) {
	        for (int col = 0; col < this.getGp().getMaxMundoCol(); col++) {
	            int tileNum = this.getMapTileNum()[lin][col];

	            int mundoX = col * this.getGp().getTileSize();
	            int mundoY = lin * this.getGp().getTileSize();

	            int telaX = mundoX - this.getGp().getJogador().getMundoX() + this.getGp().getJogador().getTelaX();

	            // ajusta a lógica para que a tela para de se mexer perto da borda do início
	            if (this.getGp().getJogador().getMundoX() < this.getGp().getJogador().getTelaX()) {
	                telaX = mundoX;
	            }

	            // a mesma lógica mas para a margem do final 
	            int margemDireita = this.getGp().getTileSize() * this.getGp().getMaxMundoCol() - this.getGp().getScreenWidth() + this.getGp().getJogador().getTelaX();
	            if (this.getGp().getJogador().getMundoX() > margemDireita) {
	                telaX = mundoX - (this.getGp().getTileSize() * this.getGp().getMaxMundoCol() - this.getGp().getScreenWidth());
	            }

	            // Desenha apenas se o tile estiver visível na tela
	            if (telaX + this.getGp().getTileSize() > 0 && telaX < this.getGp().getScreenWidth()) {
	                g2.drawImage(this.getTile()[tileNum].getImagem(), telaX, mundoY, null);
	            }
	        }
	    }
	}

	//setters
	public void setGp(GamePanel gp) {
		this.gp = gp;
	}

	public void setTile(Tile[] tile) {
		this.tile = tile;
	}

	public void setMapTileNum(int[][] mapTileNum) {
		this.mapTileNum = mapTileNum;
	}
	
	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}
	
	//getters
	public GamePanel getGp() {
		return gp;
	}
	
	public Tile[] getTile() {
		return tile;
	}
	
	public int[][] getMapTileNum() {
		return mapTileNum;
	}
	
	public Mapa getMapa() {
		return this.mapa;
	}

}
