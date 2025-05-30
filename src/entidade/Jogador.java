package entidade;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import principal.GamePanel;
import principal.ManipuladorTeclado;

public class Jogador extends Personagem {
    private ManipuladorTeclado keyH;

    private final int telaX;
    private final int telaY;

    public Jogador(GamePanel gp, ManipuladorTeclado keyH) {
        super(gp);

        areaSolida = new java.awt.Rectangle(16, 32, 64, 64);
        vidaMax = 3;
        vida = vidaMax;

        this.keyH = keyH;

        telaX = gp.getScreenWidth() / 2 - (gp.getTileSize() / 2);
        telaY = gp.getScreenHeight() / 2 - (gp.getTileSize() / 2);

        setDefaultValues();
        carregarImagens();
    }

    public void setDefaultValues() {
        setX(gp.getTileSize());
        setMundoX(gp.getTileSize());
        setMundoY(gp.getTileSize());
        setVelocidade(3);
        setDirecao("baixo");
    }

    public void carregarImagens() {
        try {
            setCima1(ImageIO.read(getClass().getResourceAsStream("/jogador/player_cima_1.png")));
            setCima2(ImageIO.read(getClass().getResourceAsStream("/jogador/player_cima_2.png")));
            setBaixo1(ImageIO.read(getClass().getResourceAsStream("/jogador/player_baixo_1.png")));
            setBaixo2(ImageIO.read(getClass().getResourceAsStream("/jogador/player_baixo_2.png")));
            setEsq1(ImageIO.read(getClass().getResourceAsStream("/jogador/player_esquerda_1.png")));
            setEsq2(ImageIO.read(getClass().getResourceAsStream("/jogador/player_esquerda_2.png")));
            setDir1(ImageIO.read(getClass().getResourceAsStream("/jogador/player_direita_1.png")));
            setDir2(ImageIO.read(getClass().getResourceAsStream("/jogador/player_direita_2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.getBaixoPress() || keyH.getCimaPress() || keyH.getDirPress() || keyH.getEsqPress()) {
            if (keyH.getCimaPress()) {
                setDirecao("cima");
            } else if (keyH.getBaixoPress()) {
                setDirecao("baixo");
            } else if (keyH.getEsqPress()) {
                setDirecao("esquerda");
            } else if (keyH.getDirPress()) {
                setDirecao("direita");
            }

            setColisaoLig(false);
            gp.getcCheca().checaTile(this);

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
    }

    public void desenhar(Graphics2D g2) {
        BufferedImage imagem = null;

        switch (getDirecao()) {
            case "cima": imagem = (getSpriteNum() == 1) ? getCima1() : getCima2(); break;
            case "baixo": imagem = (getSpriteNum() == 1) ? getBaixo1() : getBaixo2(); break;
            case "esquerda": imagem = (getSpriteNum() == 1) ? getEsq1() : getEsq2(); break;
            case "direita": imagem = (getSpriteNum() == 1) ? getDir1() : getDir2(); break;
        }

        int drawX = telaX;
        int drawY = telaY;

        // Ajuste para bordas do mapa
        if (getMundoX() < telaX) {
            drawX = getMundoX();
        } else if (getMundoX() > gp.getTileSize() * gp.getMaxMundoCol() - (gp.getScreenWidth() - telaX)) {
            drawX = getMundoX() - (gp.getTileSize() * gp.getMaxMundoCol() - gp.getScreenWidth());
        }

        if (getMundoY() < telaY) {
            drawY = getMundoY();
        } else if (getMundoY() > gp.getTileSize() * gp.getMaxMundoLin() - (gp.getScreenHeight() - telaY)) {
            drawY = getMundoY() - (gp.getTileSize() * gp.getMaxMundoLin() - gp.getScreenHeight());
        }

        g2.drawImage(imagem, drawX, drawY, gp.getTileSize(), gp.getTileSize(), null);
    }

    // Setters e getters

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

    public void setKeyH(ManipuladorTeclado keyH) {
        this.keyH = keyH;
    }

    public GamePanel getGp() {
        return gp;
    }

    public ManipuladorTeclado getKeyH() {
        return keyH;
    }

    public int getTelaX() {
        return telaX;
    }

    public int getTelaY() {
        return telaY;
    }
}
