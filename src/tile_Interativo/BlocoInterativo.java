package tile_Interativo;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entidade.Personagem;
import principal.GamePanel;
import principal.UtilityTool;

/**
 * Classe base para todos os blocos interativos do jogo.
 * Representa elementos do mapa que podem interagir com
 * personagens ou objetos, como bombas.
 * Pode ser estendida para blocos específicos como blocos
 * de destruição, obstáculos ou blocos com lógica especial.
 * 
 * @author Mateus
 * @version
 * @since
 */
public class BlocoInterativo{
	
    /**GamePanel que representa o contexto do jogo.  */
	GamePanel gp;
    /**Atributo que indica se o bloco é destrutível ou não*/
	public boolean destruivel = false;
    /*Área usada para detecção de colisão com outros objetos. */
	private Rectangle areaSolida;
    /** Coordenadas X e Y do bloco no jogo (em pixels). */
	private int mundoX, mundoY;
    /** Valor padrão das coordenadas X e Y da área sólida. */
	private int areaSolidaDefaultX, areaSolidaDefaultY;
	private boolean colisaoLig;
    /** Direção relacionada ao estado atual da renderização ou interação. */
	private String direcao;
    /** Imagem que representa o sprite do bloco. */
	protected BufferedImage baixo1;
	
	/**
     * Construtor do BlocoInterativo
     * 
     * @param gp GamePanel que representa o contexto do jogo.
     * @param col Coluna em que ele está loclizado.
     * @param lin Linha em que ele está localizado.
     */
	public BlocoInterativo(GamePanel gp, int col, int lin) {
	    this.gp = gp;

	    this.mundoX = col * gp.getTileSize();
	    this.mundoY = lin * gp.getTileSize();

	    this.areaSolida = new Rectangle(0, 0, gp.getTileSize(), gp.getTileSize());
	    this.areaSolidaDefaultX = 0;
	    this.areaSolidaDefaultY = 0;

	    this.colisaoLig = true;
	}
	
    /**
    * Atualiza o estado do bloco a cada frame.
    */
	public void update() {
		
	}
	
    /**
     * Desenha o bloco na tela, considerando a posição da câmera (jogador).
     * A imagem usada por padrão é 'baixo1', mas pode ser alterada por subclasses.
     * 
     * @param g2 Objeto Graphics2D usado para renderização no painel do jogo.
     */
    public void desenhar(Graphics2D g2) {
        BufferedImage imagem = null;

        int telaX = gp.getJogador().getTelaX();
        int telaY = gp.getJogador().getTelaY();

        // Calcula a posição da câmera no mundo
        int camX = gp.getJogador().getMundoX() - telaX;
        int camY = gp.getJogador().getMundoY() - telaY;

        // Limita a câmera para não passar das bordas do mapa
        if (camX < 0) {
            camX = 0;
        } else if (camX > gp.getTileSize() * gp.getMaxMundoCol() - gp.getScreenWidth()) {
            camX = gp.getTileSize() * gp.getMaxMundoCol() - gp.getScreenWidth();
        }

        if (camY < 0) {
            camY = 0;
        } else if (camY > gp.getTileSize() * gp.getMaxMundoLin() - gp.getScreenHeight()) {
            camY = gp.getTileSize() * gp.getMaxMundoLin() - gp.getScreenHeight();
        }

        // Posição na tela do personagem em relação à câmera
        int drawX = mundoX - camX;
        int drawY = mundoY - camY;

        // Verifica se o personagem está visível na tela
        if (drawX + gp.getTileSize() > 0 &&
            drawX < gp.getScreenWidth() &&
            drawY + gp.getTileSize() > 0 &&
            drawY < gp.getScreenHeight()) {
        	
        	direcao = "cima";
            imagem = baixo1;

            // Desenha o personagem na tela
            g2.drawImage(imagem, drawX, drawY, gp.getTileSize(), gp.getTileSize(), null);
        }
    }
    
    /**
     * Carrega uma imagem da pasta do res e a redimensiona para o tamanho de um bloco.
     * 
     * @param imagemNome Caminho do arquivo da imagem a ser carregada.
     * @return Imagem redimensionada pronta para ser desenhada.
     */
    public BufferedImage setup(String imagemNome) {
    	UtilityTool uTool = new UtilityTool();
    	BufferedImage imagem = null;
    	
    	try {
    		imagem = ImageIO.read(getClass().getResourceAsStream(imagemNome));
    		imagem = uTool.scaleImage(imagem, gp.getTileSize(), gp.getTileSize());
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    	return imagem;
    }
    
    //setters
    /** @param mundoX Nova coordenadas X do bloco no jogo (em pixels). */
    public void setMundoX(int mundoX) {
        this.mundoX = mundoX;
    }
    /** @param mundoX Nova coordenadas X do bloco no jogo (em pixels). */
    public void setMundoY(int mundoY) {
        this.mundoY = mundoY;
    }
    /** @param direcao Nova direção relacionada ao estado atual da renderização ou interação. */
    public void setDirecao(String direcao) {
        this.direcao = direcao;
    }
    /** @param areaSolida Nova área sólida personalizada do bloco. */
    public void setAreaSolida(Rectangle areaSolida) {
    	this.areaSolida = areaSolida;
    }
    /** @param areaSolidaDefaultX Novo valor padrão da coordenada X da área sólida. */
    public void setAreaSolidaDefaultX(int areaSolidaDefaultX) {
    	this.areaSolidaDefaultX = areaSolidaDefaultX;
    }
    /** @param areaSolidaDefaultY Novo valor padrão da coordenada Y da área sólida. */
    public void setAreaSolidaDefaultY(int areaSolidaDefaultY) {
    	this.areaSolidaDefaultY = areaSolidaDefaultY;
    }
    
    //getters
    /** @return Área sólida usada para colisão com personagens ou bombas. */
    public Rectangle getAreaSolida() {
        return this.areaSolida;
    }
    /** @return Coordenada X do bloco no jogo. */
    public int getMundoX() {
        return this.mundoX;
    }
    /** @return Coordenada Y do bloco no jogo. */
    public int getMundoY() {
        return this.mundoY;
    }
    /** @return Valor padrão da coordenada X da área sólida. */
    public int getAreaSolidaDefaultX() {
    	return this.areaSolidaDefaultX;
    }
    /** @return Valor padrão da coordenada X da área sólida. */
    public int getAreaSolidaDefaultY() {
    	return this.areaSolidaDefaultY;
    }
    /** @return direção relacionada ao estado atual da renderização ou interação. */
    public String getDirecao() {
    	return this.direcao;
    }
    /** @return Imagem que representa o sprite do bloco. */
    public BufferedImage getBaixo1() {
    	return this.baixo1;
    }
}
