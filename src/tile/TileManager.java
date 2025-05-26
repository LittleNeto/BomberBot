package tile;

import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import principal.GamePanel;

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
		
		try {
			
			this.getTile()[0] = new Tile();
			this.getTile()[0].setImagem(ImageIO.read(getClass().getResourceAsStream("/tiles/terra_alfa.png")));
			
			this.getTile()[1] = new Tile();
			this.getTile()[1].setImagem(ImageIO.read(getClass().getResourceAsStream("/tiles/parede_alfa.png")));
			this.getTile()[1].setColisao(true);
			
			this.getTile()[2] = new Tile();
			this.getTile()[2].setImagem(ImageIO.read(getClass().getResourceAsStream("/tiles/lixo_alfa.png")));
			this.getTile()[2].setColisao(true);
			
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
	                g2.drawImage(this.getTile()[tileNum].getImagem(), telaX, mundoY, this.getGp().getTileSize(), this.getGp().getTileSize(), null);
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

}
