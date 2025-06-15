package inimigo;

import entidade.BotPersonagem;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import principal.GamePanel;


/**
 * Representa um bot com comportamento mais difícil/inteligente.
 * Esse bot é mais rápido e segue o jogador de forma mais eficiente,
 * planta bombas estrategicamente e tenta fugir de zonas de perigo.
 *
 * Subclasse de {@link entidade.BotPersonagem}.
 * Usado como inimigo de nível difícil no jogo.
 *
 * @author júlio
 */
public class BotDificil extends BotPersonagem {

    /** Gerador de aleatoriedade para decisões de movimento */
    Random random = new Random();

    
    /**
     * Construtor do bot difícil.
     *
     * @param gp GamePanel que representa o contexto do jogo.
     */
    public BotDificil(GamePanel gp) {
        super(gp);
        velocidade = 4;
        direcao = "baixo";

        // Define a área sólida (hitbox) do bot
        areaSolida = new Rectangle();
        areaSolida.x = 16;
        areaSolida.y = 32;
        areaSolida.width = 64;
        areaSolida.height = 64;
        areaSolidaDefaultX = areaSolida.x;
        areaSolidaDefaultY = areaSolida.y;

        // Define a vida do bot
        vidaMax = 1;
        vida = vidaMax;
        
        // Define tipo (pode ser usado para diferenciação visual ou lógica)
        tipo = 0;

        // Carrega sprites do bot
        getImagem();
    }

    /**
     * Carrega as imagens de animação do bot a partir dos recursos.
     * As imagens são divididas por direção e quadro (frame).
     */
    public void getImagem() {
        try {
        	this.setCima1(ImageIO.read(getClass().getResourceAsStream("/inimigo/botC_cima_1.png"))); //pega cada png da pasta de sprites
            this.setCima2(ImageIO.read(getClass().getResourceAsStream("/inimigo/botC_cima_2.png"))); 
            this.setBaixo1(ImageIO.read(getClass().getResourceAsStream("/inimigo/botC_baixo_1.png"))); 
            this.setBaixo2(ImageIO.read(getClass().getResourceAsStream("/inimigo/botC_baixo_2.png"))); 
            this.setEsq1(ImageIO.read(getClass().getResourceAsStream("/inimigo/botC_esquerda_1.png"))); 
            this.setEsq2(ImageIO.read(getClass().getResourceAsStream("/inimigo/botC_esquerda_2.png"))); 
            this.setDir1(ImageIO.read(getClass().getResourceAsStream("/inimigo/botC_direita_1.png"))); 
            this.setDir2(ImageIO.read(getClass().getResourceAsStream("/inimigo/botC_direita_2.png"))); 

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Define o comportamento do bot a cada atualização (frame).
     * Inclui lógica de movimentação, perseguição ao jogador,
     * desvio de colisões, fuga de zonas de perigo e plantio de bomba.
     */
    @Override
    public void setAction() {
    	
        // Atualiza cooldown para plantar bomba
    	if (contadorCooldownBomba < cooldownBomba) {
    	    contadorCooldownBomba++;
    	}
    	
        // Se estiver em zona de perigo, foge imediatamente
    	if (estaNaZonaDePerigo()) {
        	fugirDaZonaDePerigo();
            return;
        }

        // Comportamento baseado no estado atual do bot
        switch (estadoAtual) {
            case ANDANDO -> {
                // Se colidiu, muda para estado de colisão
                if (colisaoLig) {
                    estadoAtual = EstadoBot.COLIDIU;
                    contadorDeEspera = 0;
                }
            }
            case COLIDIU -> {
                // Espera um tempo antes de trocar direção
                contadorDeEspera++;
                if (contadorDeEspera >= tempoDeEspera) {
                    estadoAtual = EstadoBot.ESPERANDO_NOVA_DIRECAO;
                }
            }
            case ESPERANDO_NOVA_DIRECAO -> {
                // Se o jogador estiver por perto, segue-o
            	if(!seguirJogadorSeEstiverPerto(getDistanciaMax())) {
                    // Caso contrário, anda em direção aleatória
            		direcao = escolherNovaDirecao();
            	}
                estadoAtual = EstadoBot.ANDANDO;
            }
        }

        // Após certo tempo andando, verifica novamente se deve mudar de direção
        actionLockCounter++;
        if (actionLockCounter >= 360 && estadoAtual == EstadoBot.ANDANDO) {
        	if(!seguirJogadorSeEstiverPerto(getDistanciaMax())) {
        		direcao = escolherNovaDirecao();
        	}
            actionLockCounter = 0;
        }

        // Tenta plantar bomba se possível
        plantarBomba();
    	
    }

    
}
