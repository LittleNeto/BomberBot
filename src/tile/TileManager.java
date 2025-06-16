package tile;

import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import principal.GamePanel;
import principal.UtilityTool;
import recursos.GeradorMapa;

/**
 * Classe responsável por administrar o gerenciamento dos blocos ao redor
 * do mapa, gerando assim a configuração visual da fase.
 * @author Mateus
 * @version 7.0
 * @since 2025-05-10
 */
public class TileManager {
	/**GamePanel que representa o contexto do jogo. */
	private GamePanel gp;
	/**Conjunto de blocos que se faz presente no mapa da fase. */
	private Tile[] tile;
	/**Matriz numérica que representa como os blocos devem ser ditribuídos. */
	private int mapTileNum[][];
	/**Objeto que gera randomicamente onde os objetos devem aparecer no mapa. */
	private GeradorMapa gMapa;
	
	/**
	 * Construtor do TileManager
	 * 
	 * @param gp GamePanel que representa o contexto do jogo.
	 */
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		this.tile = new Tile[10]; //lista dos tipos diferentes de blocos que terão no jogo
		this.mapTileNum = new int[gp.getMaxMundoLin()][gp.getMaxMundoCol()]; //matriz que vai guardar os tipos de blocos do mapa
		
		this.gMapa = new GeradorMapa();
		
		this.getImagemTile();
		this.carregarMapa();
	}
	
	/**
	 * Carrega as imagens dos tipos de blocos utilizados no jogo,
	 * configurando se eles terão colisão ou não.
	 */
	public void getImagemTile() {
		
		setup(0, "terra_alfa", false);
		setup(1, "parede_alfa", true);
		setup(2, "terra_alfa", false);

	}
	
	/**
	 * Configura um tipo de bloco específico, associando a imagem e definindo se possui colisão.
	 * 
	 * @param index Índice do array de tiles onde o novo tile será armazenado.
	 * @param imagePath Caminho relativo da imagem do tile (sem a extensão).
	 * @param colisao Define se o tile terá colisão ou não.
	 */
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
	
	/**
	 * Carrega o mapa atual usando a matriz gerada pelo GeradorMapa.
	 */
	public void carregarMapa() {
		this.setMapTileNum(this.gMapa.getGrade());
	}
	
	/**
	 * Responsável por desenhar os blocos do mapa na tela,
	 * levando em consideração o deslocamento da câmera baseado
	 * na posição do jogador.
	 * 
	 * @param g2 Objeto utilizado para desenhar os tiles.
	 */
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
	/** @param gp Adiciona o GamePanel no objeto. */
	public void setGp(GamePanel gp) {
		this.gp = gp;
	}

	/** @param tile Adiciona o conjunto de blocos que será administrado. */
	public void setTile(Tile[] tile) {
		this.tile = tile;
	}

	/** @param mapTileNum Adiciona uma nova matriz numérica para o TileMnager. */
	public void setMapTileNum(int[][] mapTileNum) {
		this.mapTileNum = mapTileNum;
	}
	
	/** @param gMapa Adiciona um novo gerador de mapas para o TileManager. */
	public void setMapa(GeradorMapa gMapa) {
		this.gMapa = gMapa;
	}
	
	//getters
	/** @return GamePanel que representa o contexto do jogo. */
	public GamePanel getGp() {
		return gp;
	}
	
	/** @return Conjunto de blocos que se faz presente no mapa da fase. */
	public Tile[] getTile() {
		return tile;
	}
	
	/** @return Matriz numérica que configura o mapa. */
	public int[][] getMapTileNum() {
		return mapTileNum;
	}
	
	/** @return Gerador de mapas presente do TileManager. */
	public GeradorMapa getGMapa() {
		return this.gMapa;
	}

}
