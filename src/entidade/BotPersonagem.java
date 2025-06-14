package entidade;

import java.awt.Rectangle;
import java.util.Random;
import inimigo.EstadoBot;
import objeto.OBJ_Bomba;
import principal.GamePanel;

public abstract class BotPersonagem extends Personagem {

    // Estado atual do bot (ANDANDO, COLIDIU, ESPERANDO_NOVA_DIRECAO)
    protected EstadoBot estadoAtual = EstadoBot.ANDANDO;

    // Tempo de espera após colisão antes de escolher nova direção
    protected int tempoDeEspera = 20;  // Aproximadamente 0.3 segundos
    protected int contadorDeEspera = 0; // Contador interno desse tempo de espera

    // Controle de bombas ativas por bot
    protected int bombasAtivas = 0;       // Quantidade atual de bombas ativas desse bot
    protected final int limiteBombas = 3; // Quantidade máxima de bombas que o bot pode deixar no mapa

    // Guarda a posição onde a última bomba foi plantada
    protected int ultimaBombaX = -1;
    protected int ultimaBombaY = -1;

    // Distância máxima (em pixels) para o bot começar a perseguir o jogador
    protected int distanciaMax = gp.getTileSize() * 6;

    // Gerador de números aleatórios para escolher direções aleatórias
    protected Random random = new Random();

    // Construtor recebe o GamePanel (contexto do jogo)
    public BotPersonagem(GamePanel gp) {
        super(gp);
    }

    // Escolhe aleatoriamente uma direção entre cima, baixo, esquerda e direita
    protected String escolherNovaDirecao() {
        String[] direcoes = {"cima", "baixo", "esquerda", "direita"};
        return direcoes[random.nextInt(direcoes.length)];
    }

    // Só segue o jogador SE ele estiver próximo (dentro da distância definida)
    protected boolean seguirJogadorSeEstiverPerto(int distanciaMax) {
        int dx = gp.getJogador().getMundoX() - this.getMundoX();
        int dy = gp.getJogador().getMundoY() - this.getMundoY();

        if (Math.abs(dx) <= distanciaMax && Math.abs(dy) <= distanciaMax) {
            if (Math.abs(dx) > Math.abs(dy)) {
                direcao = (dx > 0) ? "direita" : "esquerda";
            } else {
                direcao = (dy > 0) ? "baixo" : "cima";
            }
            return true;
        }
        return false;
    }

    // Planta uma bomba na posição atual SE ainda puder (respeitando limite) E se o jogador estiver próximo
    public void plantarBomba() {
        if (bombasAtivas >= limiteBombas) return;

        int dx = Math.abs(gp.getJogador().getMundoX() - this.getMundoX());
        int dy = Math.abs(gp.getJogador().getMundoY() - this.getMundoY());

        if (dx <= gp.getTileSize() && dy <= gp.getTileSize()) {
            ultimaBombaX = this.getMundoX();
            ultimaBombaY = this.getMundoY();
            gp.colocarBombaBot(ultimaBombaX, ultimaBombaY, 1, this);
            bombasAtivas++;
        }
    }

    // Decrementa o contador de bombas ativas (chamado quando uma bomba explode e desaparece)
    public void decrementarBombas() {
        bombasAtivas = Math.max(0, bombasAtivas - 1);
    }

    // Verifica se o bot está atualmente dentro de alguma zona de perigo (explosão ativa)
    protected boolean estaNaZonaDePerigo() {
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] instanceof OBJ_Bomba bomba) {
                if (!bomba.isExplosaoAtiva()) continue;

                for (Rectangle zona : bomba.getZonasExplosao()) {
                    if (zona != null && getAreaSolidaMundo().intersects(zona)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Função para fuga da zona de perigo → escolhe a direção mais segura ou a mais distante da explosão
    protected void fugirDaZonaDePerigo() {
        Rectangle botArea = getAreaSolidaMundo();
        String[] direcoes = {"cima", "baixo", "esquerda", "direita"};
        String melhorDirecao = null;
        int maiorDistancia = -1;

        for (String dir : direcoes) {
            int deslocamento = gp.getTileSize();
            Rectangle areaTeste = new Rectangle(botArea);

            // Calcula onde o bot estaria se fosse nessa direção
            switch (dir) {
                case "cima" -> areaTeste.y -= deslocamento;
                case "baixo" -> areaTeste.y += deslocamento;
                case "esquerda" -> areaTeste.x -= deslocamento;
                case "direita" -> areaTeste.x += deslocamento;
            }

            boolean seguro = true;
            int menorDistanciaPerigo = Integer.MAX_VALUE;

            // Verifica colisão dessa área teste com todas as explosões ativas
            for (int i = 0; i < gp.obj.length; i++) {
                if (gp.obj[i] instanceof OBJ_Bomba bomba && bomba.isExplosaoAtiva()) {
                    for (Rectangle zona : bomba.getZonasExplosao()) {
                        if (zona != null && areaTeste.intersects(zona)) {
                            seguro = false;
                            menorDistanciaPerigo = 0;
                            break;
                        }
                        if (zona != null) {
                            int distancia = (int) areaTeste.getLocation().distance(zona.getLocation());
                            menorDistanciaPerigo = Math.min(menorDistanciaPerigo, distancia);
                        }
                    }
                }
            }

            // Escolhe a direção mais segura ou mais distante do perigo
            if (seguro || menorDistanciaPerigo > maiorDistancia) {
                melhorDirecao = dir;
                maiorDistancia = menorDistanciaPerigo;
            }
        }

        // Atualiza a direção do bot
        if (melhorDirecao != null) {
            this.direcao = melhorDirecao;
        } else {
            this.direcao = escolherNovaDirecao();  // Se não houver melhor direção → escolhe aleatoriamente
        }
    }

    // Cria um retângulo representando a posição e área sólida do bot no mundo
    protected Rectangle getAreaSolidaMundo() {
        return new Rectangle(
            this.getMundoX() + areaSolida.x,
            this.getMundoY() + areaSolida.y,
            areaSolida.width,
            areaSolida.height
        );
    }

    public int getDistanciaMax() {
        return distanciaMax;
    }
}
