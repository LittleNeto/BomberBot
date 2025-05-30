package entidade;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import principal.GamePanel;

public abstract class Personagem {

    GamePanel gp;

    protected int x, mundoX, mundoY;
    protected int velocidade;

    protected BufferedImage cima1, cima2, baixo1, baixo2, esq1, esq2, dir1, dir2;
    protected String direcao;

    protected int spriteCount = 0;
    protected int spriteNum = 1;

    protected Rectangle areaSolida; // para colisão
    protected boolean colisaoLig = false;

    protected int vidaMax;
    protected int vida;

    public Personagem(GamePanel gp) {
        this.gp = gp;
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



    // Setters
    public void setX(int x) {
        this.x = x;
    }

    public void setMundoX(int mundoX) {
        this.mundoX = mundoX;
    }

    public void setMundoY(int mundoY) {
        this.mundoY = mundoY;
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

    // Getters
    public int getX() {
        return this.x;
    }

    public int getMundoX() {
        return this.mundoX;
    }

    public int getMundoY() {
        return this.mundoY;
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
