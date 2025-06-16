package entidade;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import principal.GamePanel;
import principal.UtilityTool;

/**
 * Classe abstrata que representa um personagem no jogo, seja jogador ou bot.
 * Define atributos e comportamentos comuns como movimentação, animação,
 * controle de colisão e renderização na tela.
 * 
 * @author Mateus
 * @version 16.0
 * @since 2025-05-10
 */
public abstract class Personagem {

	/** Referência ao painel principal do jogo. */
    GamePanel gp;

    /** Coordenadas do personagem no mapa. */
    protected int mundoX, mundoY;

    /** Sprites do personagem para cada direção. */
    protected BufferedImage cima1, cima2, baixo1, baixo2, esq1, esq2, dir1, dir2;
    
    /** Direção atual do personagem. */
    protected String direcao;
    
    /** Contador e índice de sprites para animação. */
    protected int spriteCount = 0;
    protected int spriteNum = 1;
    
    /** Área sólida usada para colisão. */
    protected Rectangle areaSolida = new Rectangle(16, 32, 64, 64);
    public int areaSolidaDefaultX, areaSolidaDefaultY;
    
    /** Indica se há colisão ativada. */
    protected boolean colisaoLig = false;

    /** Atributos de status do personagem. */
    protected int vidaMax;
    protected int vida;
    protected int velocidade;
    public int tipo;
    
    /** Auxilia controle de movimento automático dos bots. */
    public int actionLockCounter = 0;

    /**
     * Construtor base.
     * 
     * @param gp Referência ao GamePanel do jogo.
     */
    public Personagem(GamePanel gp) {
        this.gp = gp;
    }
    
    /**
     * Método sobrescrito pelas subclasses para definir comportamento de movimento.
     */
    public void setAction() {
    	
    }
    
    /**
     * Atualiza o estado do personagem: ações, colisões e animação.
     */
    public void update() {
    	setAction();
    	
    	colisaoLig = false;
    	gp.getcCheca().checaTile(this);
    	gp.getcCheca().checaObjeto(this, false);
    	gp.getcCheca().checaBlocoInterativo(this, gp.iTiles);
    	boolean interagiuJogador = gp.getcCheca().checaJogador(this);
    	
    	if(this.tipo == 1 && interagiuJogador == true) {
    		if(gp.getJogador().invencivel == false) {
    			//tira a vida
    			int vida = gp.getJogador().getVida();
    			gp.getJogador().setVida(vida - 1);
    			gp.getJogador().invencivel = true;
    		}
    	}
    	
        if (!getColisaoLig()) {
            switch (getDirecao()) {
                case "cima":
                    setMundoY(getMundoY() - getVelocidade());
                    break;
                case "baixo":
                    setMundoY(getMundoY() + getVelocidade());
                    break;
                case "esquerda":
                    setMundoX(getMundoX() - getVelocidade());
                    break;
                case "direita":
                    setMundoX(getMundoX() + getVelocidade());
                    break;
            }
        }

        // Controle do sprite para animação
        setSpriteCount(getSpriteCount() + 1);
        if (getSpriteCount() > 15) {
            setSpriteNum(getSpriteNum() == 1 ? 2 : 1);
            setSpriteCount(0);
        }
    }
    
    /**
     * Renderiza o personagem na tela de acordo com a posição da câmera.
     * 
     * @param g2 Contexto gráfico do jogo.
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

            // Escolhe a imagem com base na direção e no spriteNum
            switch (direcao) {
                case "cima":
                    imagem = (spriteNum == 1) ? cima1 : cima2;
                    break;
                case "baixo":
                    imagem = (spriteNum == 1) ? baixo1 : baixo2;
                    break;
                case "esquerda":
                    imagem = (spriteNum == 1) ? esq1 : esq2;
                    break;
                case "direita":
                    imagem = (spriteNum == 1) ? dir1 : dir2;
                    break;
            }

            // Desenha o personagem na tela
            g2.drawImage(imagem, drawX, drawY, gp.getTileSize(), gp.getTileSize(), null);
        }
    }
    
    /**
     * Carrega uma imagem do caminho informado e redimensiona conforme o tamanho do tile.
     * 
     * @param imagemNome Caminho do arquivo de imagem.
     * @return Imagem redimensionada.
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
    

    // Setters
    
    /** Define a vida atual do personagem. */
    public void setVida(int vida) {
        this.vida = vida;
    }
    
    /** Define a coordenada X no mundo. */
    public void setMundoX(int mundoX) {
        this.mundoX = mundoX;
    }

    /** Define a coordenada Y no mundo. */
    public void setMundoY(int mundoY) {
        this.mundoY = mundoY;
    }

    /** Define a velocidade de movimentação. */
    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }

    /** Define a imagem do sprite cima1. */
    public void setCima1(BufferedImage cima1) {
        this.cima1 = cima1;
    }
    
    /** Define a imagem do sprite cima2. */
    public void setCima2(BufferedImage cima2) {
        this.cima2 = cima2;
    }
    
    /** Define a imagem do sprite baixo1. */
    public void setBaixo1(BufferedImage baixo1) {
        this.baixo1 = baixo1;
    }

    /** Define a imagem do sprite baixo2. */
    public void setBaixo2(BufferedImage baixo2) {
        this.baixo2 = baixo2;
    }

    /** Define a imagem do sprite esquerda1. */
    public void setEsq1(BufferedImage esq1) {
        this.esq1 = esq1;
    }

    /** Define a imagem do sprite esquerda2. */
    public void setEsq2(BufferedImage esq2) {
        this.esq2 = esq2;
    }

    /** Define a imagem do sprite direita1. */
    public void setDir1(BufferedImage dir1) {
        this.dir1 = dir1;
    }

    /** Define a imagem do sprite direita2. */
    public void setDir2(BufferedImage dir2) {
        this.dir2 = dir2;
    }

    /** Define a direção atual. */
    public void setDirecao(String direcao) {
        this.direcao = direcao;
    }

    /** Define o contador de sprite. */
    public void setSpriteCount(int spriteCount) {
        this.spriteCount = spriteCount;
    }

    /** Define o número do sprite. */
    public void setSpriteNum(int spriteNum) {
        this.spriteNum = spriteNum;
    }

    /** Define a área sólida para colisão. */
    public void setAreaSolida(Rectangle areaSolida) {
        this.areaSolida = areaSolida;
    }

    /** Ativa ou desativa a colisão. */
    public void setColisaoLig(boolean colisaoLig) {
        this.colisaoLig = colisaoLig;
    }

    // Getters
    
    /** @return Vida máxima do personagem. */
    public int getVidaMax() {
        return this.vidaMax;
    }
    
    /** @return Vida atual do personagem. */
    public int getVida() {
        return this.vida;
    }
    
    /** @return Posição X no mundo. */
    public int getMundoX() {
        return this.mundoX;
    }

    /** @return Posição Y no mundo. */
    public int getMundoY() {
        return this.mundoY;
    }

    /** @return Velocidade do personagem. */
    public int getVelocidade() {
        return this.velocidade;
    }

    /** @return Sprite cima1. */
    public BufferedImage getCima1() {
        return this.cima1;
    }

    /** @return Sprite cima2. */
    public BufferedImage getCima2() {
        return this.cima2;
    }

    /** @return Sprite baixo1. */
    public BufferedImage getBaixo1() {
        return this.baixo1;
    }

    /** @return Sprite baixo2. */
    public BufferedImage getBaixo2() {
        return this.baixo2;
    }

    /** @return Sprite esquerda1. */
    public BufferedImage getEsq1() {
        return this.esq1;
    }

    /** @return Sprite esquerda2. */
    public BufferedImage getEsq2() {
        return this.esq2;
    }

    /** @return Sprite direita1. */
    public BufferedImage getDir1() {
        return this.dir1;
    }

    /** @return Sprite direita2. */
    public BufferedImage getDir2() {
        return this.dir2;
    }

    /** @return Direção atual. */
    public String getDirecao() {
        return this.direcao;
    }

    /** @return Contador de sprite. */
    public int getSpriteCount() {
        return this.spriteCount;
    }

    /** @return Número do sprite. */
    public int getSpriteNum() {
        return this.spriteNum;
    }

    /** @return Área sólida para colisão. */
    public Rectangle getAreaSolida() {
        return this.areaSolida;
    }

    /** @return true se colisão está ativada, false caso contrário. */
    public boolean getColisaoLig() {
        return this.colisaoLig;
    }
}
