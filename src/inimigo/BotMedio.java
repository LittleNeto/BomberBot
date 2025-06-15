package inimigo;

import entidade.BotPersonagem;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import principal.GamePanel;

/**
 * Representa um bot inimigo de dificuldade média.
 * Esse bot anda aleatoriamente, tenta fugir de zonas de perigo
 * e planta bombas quando o jogador estiver próximo.
 * 
 * Subclasse de {@link entidade.BotPersonagem}.
 * Usado como inimigo intermediário no jogo.
 * 
 * @author júlio
 */
public class BotMedio extends BotPersonagem{

    /** Gerador de números aleatórios para escolha de direção */
    Random random = new Random();

    /**
     * Construtor do BotMedio.
     * Inicializa as propriedades como velocidade, vida e área de colisão.
     *
     * @param gp GamePanel com o contexto do jogo.
     */
    public BotMedio(GamePanel gp) {
        super(gp);
        velocidade = 3;
        direcao = "baixo";

        // Define hitbox (área sólida) do bot
        areaSolida = new Rectangle();
        areaSolida.x = 16;
        areaSolida.y = 32;
        areaSolida.width = 64;
        areaSolida.height = 64;
        areaSolidaDefaultX = areaSolida.x;
        areaSolidaDefaultY = areaSolida.y;

        // Define atributos de vida
        vidaMax = 1;
        vida = vidaMax;

        // Define tipo de bot (pode ser usado para lógica extra)
        tipo = 1;
        
        // Carrega imagens do bot
        getImagem();
    }

    /**
     * Carrega as sprites (imagens) do bot para cada direção e animação.
     * As imagens devem estar localizadas na pasta /inimigo.
     */
    public void getImagem() {
        try {
        	this.setCima1(ImageIO.read(getClass().getResourceAsStream("/inimigo/botB_cima_1.png"))); //pega cada png da pasta de sprites
            this.setCima2(ImageIO.read(getClass().getResourceAsStream("/inimigo/botB_cima_2.png"))); 
            this.setBaixo1(ImageIO.read(getClass().getResourceAsStream("/inimigo/botB_baixo_1.png"))); 
            this.setBaixo2(ImageIO.read(getClass().getResourceAsStream("/inimigo/botB_baixo_2.png"))); 
            this.setEsq1(ImageIO.read(getClass().getResourceAsStream("/inimigo/botB_esquerda_1.png"))); 
            this.setEsq2(ImageIO.read(getClass().getResourceAsStream("/inimigo/botB_esquerda_2.png"))); 
            this.setDir1(ImageIO.read(getClass().getResourceAsStream("/inimigo/botB_direita_1.png"))); 
            this.setDir2(ImageIO.read(getClass().getResourceAsStream("/inimigo/botB_direita_2.png"))); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Define o comportamento do bot em cada atualização do jogo.
     * Inclui movimentação baseada em estados, desvio de colisões,
     * fuga de zonas de explosão e plantio de bombas.
     */
    @Override
    public void setAction() {
    	
        // Atualiza o cooldown de bomba
    	if (contadorCooldownBomba < cooldownBomba) {
    	    contadorCooldownBomba++;
    	}
    	
        // Se estiver em zona de perigo, tenta fugir
    	if (estaNaZonaDePerigo()) {
    		 System.out.println("⚠️ Bot detectou perigo em: " + getMundoX() + "," + getMundoY());
    		    direcao = fugirDaZonaDePerigo();
    		    System.out.println("🟡 Tentando fugir para: " + direcao);
            return;
        }

        // Lógica de movimentação baseada em estado
        switch (estadoAtual) {
            case ANDANDO -> {
                // Se bateu em algum obstáculo, entra no estado de colisão
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
                // Escolhe nova direção aleatória e volta a andar
                direcao = escolherNovaDirecao();
                estadoAtual = EstadoBot.ANDANDO;
            }
        }

        // Após certo tempo andando, reconsidera a direção
        actionLockCounter++;
        if (actionLockCounter >= 360 && estadoAtual == EstadoBot.ANDANDO) {
            direcao = escolherNovaDirecao();
            actionLockCounter = 0;
        }
        
        // Tenta plantar bomba se possível
        plantarBomba();
        
    }
}
