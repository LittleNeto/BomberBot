package objeto;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import principal.GamePanel;
import principal.GameState;

public class OBJ_Bomba extends SuperObjeto {

    GamePanel gp;

    private int spriteCount = 0;
    private int spriteNum = 0;

    private boolean jogadorAindaDentro = true;
    private boolean explodiu = false;
    private boolean explosaoAtiva = false;

    private long tempoColocada;
    private long tempoExplosao;

    private BufferedImage imagemChama;
    private Rectangle[] zonasExplosao;
    private final int alcance = 1;

    public OBJ_Bomba(GamePanel gp) {
        this.gp = gp;
        nome = "Bomba";
        tempoColocada = System.currentTimeMillis();

        try {
            imagem = ImageIO.read(getClass().getResourceAsStream("/objetos/bomba_1.png"));
            imagem1 = ImageIO.read(getClass().getResourceAsStream("/objetos/bomba_2.png"));
            imagem2 = ImageIO.read(getClass().getResourceAsStream("/objetos/bomba_3.png"));
            imagemChama = ImageIO.read(getClass().getResourceAsStream("/objetos/explosao_1.png"));

            imagem = uTool.scaleImage(imagem, gp.getTileSize(), gp.getTileSize());
            imagem1 = uTool.scaleImage(imagem1, gp.getTileSize(), gp.getTileSize());
            imagem2 = uTool.scaleImage(imagem2, gp.getTileSize(), gp.getTileSize());
            imagemChama = uTool.scaleImage(imagemChama, gp.getTileSize(), gp.getTileSize());

        } catch (IOException e) {
            e.printStackTrace();
        }

        colisao = false;
    }

    public void update() {
        if (explodiu && explosaoAtiva) {
            causarDanoAoJogador();
            if (System.currentTimeMillis() - tempoExplosao > 500) {
                explosaoAtiva = false;
                removerDoJogo();
            }
            return;
        }

        spriteCount++;
        if (spriteCount > 15) {
            spriteNum = (spriteNum + 1) % 3;
            spriteCount = 0;
        }

        checaPresencaJogador(gp);
        if(gp.gameState == GameState.PLAY) {
            long tempoAtual = System.currentTimeMillis();
            if (tempoAtual - tempoColocada >= 5000) {
                explodir();
            }
        }

    }

    private void explodir() {
        explodiu = true;
        explosaoAtiva = true;
        tempoExplosao = System.currentTimeMillis();

        zonasExplosao = new Rectangle[5];

        int tile = gp.getTileSize();

        zonasExplosao[0] = new Rectangle(mundoX, mundoY, tile, tile); // zona da bomba (posição central)
        zonasExplosao[1] = new Rectangle(mundoX, mundoY - alcance * tile, tile, alcance * tile); // cima
        zonasExplosao[2] = new Rectangle(mundoX, mundoY + tile, tile, alcance * tile); // baixo
        zonasExplosao[3] = new Rectangle(mundoX - alcance * tile, mundoY, alcance * tile, tile); // esquerda
        zonasExplosao[4] = new Rectangle(mundoX + tile, mundoY, alcance * tile, tile); // direita

    }

    private void causarDanoAoJogador() {
        Rectangle areaJogador = new Rectangle(
                gp.getJogador().getMundoX() + gp.getJogador().getAreaSolida().x,
                gp.getJogador().getMundoY() + gp.getJogador().getAreaSolida().y,
                gp.getJogador().getAreaSolida().width,
                gp.getJogador().getAreaSolida().height
        );

        for (Rectangle zona : zonasExplosao) {
            if (zona.intersects(areaJogador)) {
                gp.getJogador().interageBot(0); // Aplica dano
                break;
            }
        }
    }

    private void removerDoJogo() {
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] == this) {
                gp.obj[i] = null;
                gp.setBombaAtiva(false); // permite colocar outra bomba
                break;
            }
        }
    }

    @Override
    public void desenhar(Graphics2D g2, GamePanel gp) {
        BufferedImage imagemAtual = getImagemAtual();

        int telaX = gp.getJogador().getTelaX();
        int telaY = gp.getJogador().getTelaY();

        int camX = gp.getJogador().getMundoX() - telaX;
        int camY = gp.getJogador().getMundoY() - telaY;

        if (camX < 0) camX = 0;
        else if (camX > gp.getTileSize() * gp.getMaxMundoCol() - gp.getScreenWidth())
            camX = gp.getTileSize() * gp.getMaxMundoCol() - gp.getScreenWidth();

        if (camY < 0) camY = 0;
        else if (camY > gp.getTileSize() * gp.getMaxMundoLin() - gp.getScreenHeight())
            camY = gp.getTileSize() * gp.getMaxMundoLin() - gp.getScreenHeight();

        int drawX = mundoX - camX;
        int drawY = mundoY - camY;

        if (drawX + gp.getTileSize() > 0 &&
                drawX < gp.getScreenWidth() &&
                drawY + gp.getTileSize() > 0 &&
                drawY < gp.getScreenHeight()) {
            g2.drawImage(imagemAtual, drawX, drawY, gp.getTileSize(), gp.getTileSize(), null);

            if (explosaoAtiva) {
                int tile = gp.getTileSize();

                // chama na posição da bomba (central)
                g2.drawImage(imagemChama, drawX, drawY, tile, tile, null);

                // cima
                for (int i = 1; i <= alcance; i++) {
                    g2.drawImage(imagemChama, drawX, drawY - i * tile, tile, tile, null);
                }
                // baixo
                for (int i = 1; i <= alcance; i++) {
                    g2.drawImage(imagemChama, drawX, drawY + i * tile, tile, tile, null);
                }
                // esquerda
                for (int i = 1; i <= alcance; i++) {
                    g2.drawImage(imagemChama, drawX - i * tile, drawY, tile, tile, null);
                }
                // direita
                for (int i = 1; i <= alcance; i++) {
                    g2.drawImage(imagemChama, drawX + i * tile, drawY, tile, tile, null);
                }
            }

        }
    }

    public BufferedImage getImagemAtual() {
        switch (spriteNum) {
            case 0: return imagem;
            case 1: return imagem1;
            case 2: return imagem2;
            default: return imagem;
        }
    }

    public void checaPresencaJogador(GamePanel gp) {
        Rectangle areaBomba = new Rectangle(
                mundoX + areaSolida.x,
                mundoY + areaSolida.y,
                areaSolida.width,
                areaSolida.height
        );

        Rectangle areaJogador = new Rectangle(
                gp.getJogador().getMundoX() + gp.getJogador().getAreaSolida().x,
                gp.getJogador().getMundoY() + gp.getJogador().getAreaSolida().y,
                gp.getJogador().getAreaSolida().width,
                gp.getJogador().getAreaSolida().height
        );

        if (areaBomba.intersects(areaJogador)) {
            jogadorAindaDentro = true;
            this.colisao = false;
        } else {
            if (jogadorAindaDentro) {
                jogadorAindaDentro = false;
                this.colisao = true;
            }
        }
    }
    
    public Rectangle[] getZonasExplosao() {
        return zonasExplosao;
    }

    public boolean isExplosaoAtiva() {
        return explosaoAtiva;
    }

    
    
}
