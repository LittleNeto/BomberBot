package entidade;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Mateus
 */ 
public abstract class Personagem {
    protected int x, mundoX, y;
    protected int velocidade;
    
    protected BufferedImage cima1, cima2, baixo1, baixo2, esq1, esq2, dir1, dir2;
    protected String direcao;
    
    protected int spriteCount = 0;
    protected int spriteNum = 1;
    
    protected Rectangle areaSolida; //para definir a colisão do personagem
    protected boolean colisaoLig = false;
    
    public Personagem(int x, int y, int width, int height) {
    	areaSolida = new Rectangle();
        areaSolida.x = x;
        areaSolida.y = y;
        areaSolida.width = width;
        areaSolida.height = height;
    }

    //setters
	public void setX(int x) {
		this.x = x;
	}

	public void setMundoX(int mundoX) {
		this.mundoX = mundoX;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setVelocidade(int velocidade) {
		this.velocidade = velocidade;
	}

	public void setCima1(BufferedImage cima1) {
		this.cima1 = cima1;
	}

	public void setCima2(BufferedImage cima2) {
		this.cima2 = cima2;
	}

	public void setBaixo1(BufferedImage baixo1) {
		this.baixo1 = baixo1;
	}

	public void setBaixo2(BufferedImage baixo2) {
		this.baixo2 = baixo2;
	}

	public void setEsq1(BufferedImage esq1) {
		this.esq1 = esq1;
	}

	public void setEsq2(BufferedImage esq2) {
		this.esq2 = esq2;
	}

	public void setDir1(BufferedImage dir1) {
		this.dir1 = dir1;
	}

	public void setDir2(BufferedImage dir2) {
		this.dir2 = dir2;
	}

	public void setDirecao(String direcao) {
		this.direcao = direcao;
	}

	public void setSpriteCount(int spriteCount) {
		this.spriteCount = spriteCount;
	}

	public void setSpriteNum(int spriteNum) {
		this.spriteNum = spriteNum;
	}

	public void setAreaSolida(Rectangle areaSolida) {
		this.areaSolida = areaSolida;
	}

	public void setColisaoLig(boolean colisaoLig) {
		this.colisaoLig = colisaoLig;
	}
    
    //getters
	public int getX() {
		return this.x;
	}
	
	public int getMundoX() {
		return this.mundoX;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getVelocidade() {
		return this.velocidade;
	}
	
	public BufferedImage getCima1() {
		return this.cima1;
	}
	
	public BufferedImage getCima2() {
		return this.cima2;
	}
	
	public BufferedImage getBaixo1() {
		return this.baixo1;
	}
	
	public BufferedImage getBaixo2() {
		return this.baixo2;
	}
	
	public BufferedImage getEsq1() {
		return this.esq1;
	}
	
	public BufferedImage getEsq2() {
		return this.esq2;
	}
	
	public BufferedImage getDir1() {
		return this.dir1;
	}
	
	public BufferedImage getDir2() {
		return this.dir2;
	}
	
	public String getDirecao() {
		return this.direcao;
	}
	
	public int getSpriteCount() {
		return this.spriteCount;
	}
	
	public int getSpriteNum() {
		return this.spriteNum;
	}
	
	public Rectangle getAreaSolida() {
		return this.areaSolida;
	}
	
	public boolean getColisaoLig() {
		return this.colisaoLig;
	}
    
}