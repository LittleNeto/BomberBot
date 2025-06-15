package tile_Interativo;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entidade.Personagem;
import principal.GamePanel;
import principal.UtilityTool;

public class BlocoInterativo{
	
	GamePanel gp;
	public boolean destruivel = false;
	private Rectangle areaSolida;
	private int mundoX, mundoY;
	private int areaSolidaDefaultX, areaSolidaDefaultY;
	private boolean colisaoLig;
	private String direcao;
	protected BufferedImage baixo1;
	
	
	public BlocoInterativo(GamePanel gp, int col, int lin) {
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
    
    
    public void setMundoX(int mundoX) {
        this.mundoX = mundoX;
    }
    public void setMundoY(int mundoY) {
        this.mundoY = mundoY;
    }
    public void setDirecao(String direcao) {
        this.direcao = direcao;
    }
    public void setAreaSolida(Rectangle areaSolida) {
    	this.areaSolida = areaSolida;
    }
    public void setAreaSolidaDefaultX(int areaSolidaDefaultX) {
    	this.areaSolidaDefaultX = areaSolidaDefaultX;
    }
    public void setAreaSolidaDefaultY(int areaSolidaDefaultY) {
    	this.areaSolidaDefaultY = areaSolidaDefaultY;
    }
    
    
    public Rectangle getAreaSolida() {
        return this.areaSolida;
    }
    
    public int getMundoX() {
        return this.mundoX;
    }

    public int getMundoY() {
        return this.mundoY;
    }
    public int getAreaSolidaDefaultX() {
    	return this.areaSolidaDefaultX;
    }
    public int getAreaSolidaDefaultY() {
    	return this.areaSolidaDefaultY;
    }
}