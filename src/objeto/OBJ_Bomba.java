package objeto;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import entidade.BotPersonagem;
import entidade.Personagem;
import principal.GamePanel;
import principal.GameState;
import java.util.HashSet; 
import java.util.Set;

/**
 * Representa a bomba que será utilizada durante o jogo. Exlpode os blocos de
 * lixo e ainda causa dano ao personagem que estiver no seu raio de alcance.
 * 
 * Subclasse de {@link objeto.SuperObjeto}
 * 
 * @author Neto
 * @version 10.0
 * @since 2025-05-18
 */
public class OBJ_Bomba extends SuperObjeto {

    /**GamePanel que representa o contexto do jogo. */
    GamePanel gp;
    /**Personagem responsável por aplicar a bomba na fase. */
    private BotPersonagem dono;

    /**Contador de sprites para registro da explosão. */
    private int spriteCount = 0;
    /**Conta o número do sprite para atualizar a situação da bomba pré-explosão. */
    private int spriteNum = 0;

    private boolean jogadorAindaDentro = true;
    /**Estado atual da bomba. Identifica se ela explodiu ou não. */
    private boolean explodiu = false;
    /**Verifica se a explosão está ativada. */
    private boolean explosaoAtiva = false;

    /** Contador de frames para controlar o tempo da bomba.*/
    private int contadorFramesDesdeColocada = 0;
    /** Quantos frames para explodir (5 segundos * fps). */
    private final int framesParaExplodir;

    /**Conta o números de frames para que a explosão seja realizada com sucesso. */
    private int contadorFramesExplosao = 0;
    /**Duração da explosão em frames. */
    private final int framesDuracaoExplosao;

    /**Armazena a imagem da chama resultante da explosão. */
    private BufferedImage imagemChama;
    /**Armazena quais regiões estão no alcance de explosão da bomba.*/
    private Rectangle[] zonasExplosao;
    /**Representa qual o raio de alcance da bomba */
    private final int alcance = 1;
    
    /**Armazena os personagens que estão no alcance da bomba durante a explosão */
    private Set<Personagem> personagensDentroDaBomba = new HashSet<>();


    /**
     * Construtor do OBJ_Bomba
     * 
     * @param gp GamePanel que representa o contexto do jogo. 
     * @param dono Bot que irá implantar a bomba dentro do mapa.
     */
    public OBJ_Bomba(GamePanel gp, BotPersonagem dono) {
        this.gp = gp;
        nome = "Bomba";
        this.dono = dono;

        framesParaExplodir = gp.getFps() * 5; // 5 segundos
        framesDuracaoExplosao = gp.getFps() / 2; // 0.5 segundos de explosão (ajustável)

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

    /**
     * Função que atualiza a situação da bomba em tempo real, verificando se ela
     * explodiu, se algum personagem está a seu alcance e se o jogo está pausado
     * (condição na qual a bomba deve estar paralizada).
     */
    public void update() {
        if (gp.gameState == GameState.PLAY) {
            if (explodiu && explosaoAtiva) {
                causarDanoAoJogador();

                contadorFramesExplosao++;
                if (contadorFramesExplosao > framesDuracaoExplosao) {
                    explosaoAtiva = false;
                    removerDoJogo();
                }
                return;
            }

            // Animação da bomba antes de explodir
            spriteCount++;
            if (spriteCount > 15) {
                spriteNum = (spriteNum + 1) % 3;
                spriteCount = 0;
            }

            checaPresencaPersonagem(gp.getJogador());
            
            for (Personagem p : gp.monstros) {
                if (p != null) {
                    checaPresencaPersonagem(p);
                }
            }

            // Contador de frames para explodir
            contadorFramesDesdeColocada++;
            if (contadorFramesDesdeColocada >= framesParaExplodir) {
                explodir();
            }
        }
        // Se estiver pausado, simplesmente não incrementa os contadores
    }

    /**
     * Função responsável por explodir a bomba, já registrando quais
     * regiões foram afetadas por sua explosão.
     */
    private void explodir() {
        explodiu = true;
        explosaoAtiva = true;
        contadorFramesExplosao = 0;

        zonasExplosao = new Rectangle[5];

        int tile = gp.getTileSize();

        zonasExplosao[0] = new Rectangle(mundoX, mundoY, tile, tile); // zona da bomba (posição central)
        zonasExplosao[1] = new Rectangle(mundoX, mundoY - alcance * tile, tile, alcance * tile); // cima
        zonasExplosao[2] = new Rectangle(mundoX, mundoY + tile, tile, alcance * tile); // baixo
        zonasExplosao[3] = new Rectangle(mundoX - alcance * tile, mundoY, alcance * tile, tile); // esquerda
        zonasExplosao[4] = new Rectangle(mundoX + tile, mundoY, alcance * tile, tile); // direita	
    }

    /**
     * Função responsável por causar dano ao jogador, caso
     * ele esteja no raio de explosão da bomba.
     */
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

    /**
     * Função que remove a presença da bomba no mapa, caso
     * ela já tenha explodido.
     */
    private void removerDoJogo() {
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] == this) {
                gp.obj[i] = null;
                gp.setBombaAtiva(false); // permite colocar outra bomba
                
                if (dono != null) {
                    dono.decrementarBombas();
                }
                
                break;
            }
        }
    }

    /**
     * Função que vai adicionando as imagens no mapa de acordo
     * com a situação atual da bomba.
     * 
     * Sobrescrita da função presente em {@link objeto.SuperObjeto}
     */
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

    /**
     * 
     * @return a imagem da bomba referente ao número de sprites
     * contados. Serve para dar a impressão de que a bomba está
     * prestes a explodir
     */
    public BufferedImage getImagemAtual() {
        switch (spriteNum) {
            case 0: return imagem;
            case 1: return imagem1;
            case 2: return imagem2;
            default: return imagem;
        }
    }

    /**
     * Função que checa se tem um personagem perto da da bomba.
     * @param p Personagem que terá sua presença verificada.
     */
    public void checaPresencaPersonagem(Personagem p) {
        Rectangle areaBomba = new Rectangle(
            mundoX + areaSolida.x,
            mundoY + areaSolida.y,
            areaSolida.width,
            areaSolida.height
        );

        Rectangle areaPersonagem = new Rectangle(
            p.getMundoX() + p.getAreaSolida().x,
            p.getMundoY() + p.getAreaSolida().y,
            p.getAreaSolida().width,
            p.getAreaSolida().height
        );

        if (areaBomba.intersects(areaPersonagem)) {
            personagensDentroDaBomba.add(p);
        } else {
            personagensDentroDaBomba.remove(p);
        }

        // Ativa colisão só quando ninguém mais estiver dentro
        this.colisao = personagensDentroDaBomba.isEmpty();
    }
    
    /**
     * 
     * @return as regiões que serão afetadas pela explosão da bomba.
     */
    public Rectangle[] getZonasExplosaoPrevisao() {
        Rectangle[] zonas = new Rectangle[5];

        int tile = gp.getTileSize();
        int bx = mundoX;
        int by = mundoY;

        zonas[0] = new Rectangle(bx, by, tile, tile); // centro

        zonas[1] = new Rectangle(bx, by - alcance * tile, tile, alcance * tile); // cima
        zonas[2] = new Rectangle(bx, by + tile, tile, alcance * tile);           // baixo
        zonas[3] = new Rectangle(bx - alcance * tile, by, alcance * tile, tile); // esquerda
        zonas[4] = new Rectangle(bx + tile, by, alcance * tile, tile);           // direita

        return zonas;
    }


    /**
     * 
     * @return A área a ser afetada pela explosão.
     */
    public Rectangle[] getZonasExplosao() {
        return zonasExplosao;
    }
    /**
     * 
     * @return a verificação se a bomba está ativada ou não.
     */
    public boolean isExplosaoAtiva() {
        return explosaoAtiva;
    }
    
    /**
     * 
     * @return Quantos frames faltam até a bomba explodir de fato.
     */
    public int getFramesRestantes() {
        return framesParaExplodir - contadorFramesDesdeColocada;
    }
    
}
