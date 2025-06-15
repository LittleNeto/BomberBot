package inimigo;

import entidade.BotPersonagem;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import principal.GamePanel;

/**
 * Representa um bot com comportamento simples.
 * O BotFácil anda de forma aleatória, reage minimamente a obstáculos
 * e tenta fugir de zonas de perigo (explosões).
 * 
 * Subclasse de {@link entidade.BotPersonagem}.
 * Usado como inimigo de nível fácil no jogo.
 * 
 * @author júlio
 */
public class BotFacil extends BotPersonagem {

    /**
     * Construtor do bot fácil.
     *
     * @param gp Referência ao GamePanel (contexto do jogo).
     */
    public BotFacil(GamePanel gp) {
        super(gp);
        velocidade = 3;
        direcao = "baixo"; 

        // Define a área de colisão (hitbox)
        areaSolida = new Rectangle();
        areaSolida.x = 16;
        areaSolida.y = 32;
        areaSolida.width = 64;
        areaSolida.height = 64;

        areaSolidaDefaultX = areaSolida.x;
        areaSolidaDefaultY = areaSolida.y;

        // Define vida
        vidaMax = 1;
        vida = vidaMax;

        // Tipo de bot (pode ser usado para identificação)
        tipo = 1;

        // Carrega sprites do bot
        getImagem();
    }

    /**
     * Carrega as imagens de animação do bot a partir dos recursos do projeto.
     * As imagens são divididas por direção e quadro.
     */
    public void getImagem() {
        try {
            this.setCima1(ImageIO.read(getClass().getResourceAsStream("/inimigo/botA_cima_1.png")));
            this.setCima2(ImageIO.read(getClass().getResourceAsStream("/inimigo/botA_cima_2.png")));
            this.setBaixo1(ImageIO.read(getClass().getResourceAsStream("/inimigo/botA_baixo_1.png")));
            this.setBaixo2(ImageIO.read(getClass().getResourceAsStream("/inimigo/botA_baixo_2.png")));
            this.setEsq1(ImageIO.read(getClass().getResourceAsStream("/inimigo/botA_esquerda_1.png")));
            this.setEsq2(ImageIO.read(getClass().getResourceAsStream("/inimigo/botA_esquerda_2.png")));
            this.setDir1(ImageIO.read(getClass().getResourceAsStream("/inimigo/botA_direita_1.png")));
            this.setDir2(ImageIO.read(getClass().getResourceAsStream("/inimigo/botA_direita_2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Define o comportamento do bot fácil a cada atualização (frame).
     * 
     * Comportamentos:
     * - Reage a colisões simples.
     * - Anda aleatoriamente após esperar um tempo.
     * - Tenta fugir de zonas de explosão.
     */
    @Override
    public void setAction() {
        // Atualiza cooldown de bomba
        if (contadorCooldownBomba < cooldownBomba) {
            contadorCooldownBomba++;
        }

        // Se estiver em perigo, tenta fugir
        if (estaNaZonaDePerigo()) {
            fugirDaZonaDePerigo();
            return;
        }

        // Controla comportamento baseado em estados
        switch (estadoAtual) {
            case ANDANDO -> {
                if (colisaoLig) {
                    estadoAtual = EstadoBot.COLIDIU;
                    contadorDeEspera = 0;
                }
            }

            case COLIDIU -> {
                contadorDeEspera++;
                if (contadorDeEspera >= tempoDeEspera) {
                    estadoAtual = EstadoBot.ESPERANDO_NOVA_DIRECAO;
                }
            }

            case ESPERANDO_NOVA_DIRECAO -> {
                direcao = escolherNovaDirecao();
                estadoAtual = EstadoBot.ANDANDO;
            }
        }

        // Troca aleatoriamente de direção após algum tempo andando
        actionLockCounter++;
        if (actionLockCounter >= 360 && estadoAtual == EstadoBot.ANDANDO) {
            direcao = escolherNovaDirecao();
            actionLockCounter = 0;
        }
    }
}
