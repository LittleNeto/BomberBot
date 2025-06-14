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
    
    
    protected int cooldownBomba = 60; // 60 frames ≈ 1 segundo
    protected int contadorCooldownBomba = 0;

    // Distância máxima (em pixels) para o bot começar a perseguir o jogador
    protected int distanciaMax = gp.getTileSize() * 6;

    // Gerador de números aleatórios para escolher direções aleatórias
    protected Random random = new Random();

    // Construtor recebe o GamePanel (contexto do jogo)
    public BotPersonagem(GamePanel gp) {
        super(gp);
    }

    // Escolhe aleatoriamente uma direção entre cima, baixo, esquerda e direita
    public String escolherNovaDirecao() {
        String[] direcoes = {"cima", "baixo", "esquerda", "direita"};
        return direcoes[random.nextInt(direcoes.length)];
    }

    // Só segue o jogador SE ele estiver próximo (dentro da distância definida)
    public boolean seguirJogadorSeEstiverPerto(int distanciaMax) {
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
        if (contadorCooldownBomba < cooldownBomba) return;

        int dx = Math.abs(gp.getJogador().getMundoX() - this.getMundoX());
        int dy = Math.abs(gp.getJogador().getMundoY() - this.getMundoY());

        // Apenas se o jogador estiver suficientemente próximo
        if (dx <= gp.getTileSize() && dy <= gp.getTileSize()) {

            // Tile atual do bot (onde ele está)
            int botTileX = (this.getMundoX() + areaSolida.x + areaSolida.width / 2) / gp.getTileSize();
            int botTileY = (this.getMundoY() + areaSolida.y + areaSolida.height / 2) / gp.getTileSize();

            int posX = botTileX * gp.getTileSize();
            int posY = botTileY * gp.getTileSize();

            // Verifica se já tem bomba nesse tile
            for (int i = 0; i < gp.obj.length; i++) {
                if (gp.obj[i] instanceof OBJ_Bomba b) {
                    if (b.mundoX == posX && b.mundoY == posY) {
                        return; // Já tem bomba aqui → não planta
                    }
                }
            }

            // Impede de plantar novamente no mesmo local
            if (ultimaBombaX == posX && ultimaBombaY == posY) {
                return;
            }

            ultimaBombaX = posX;
            ultimaBombaY = posY;

            // Planta a bomba
            gp.colocarBombaBot(posX, posY, 1, this);
            bombasAtivas++;
            contadorCooldownBomba = 0; // reinicia cooldown
        }
    }





    // Decrementa o contador de bombas ativas (chamado quando uma bomba explode e desaparece)
    public void decrementarBombas() {
        bombasAtivas = Math.max(0, bombasAtivas - 1);
    }

    // Verifica se o bot está atualmente dentro de alguma zona de perigo (explosão ativa)
    public boolean estaNaZonaDePerigo() {
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
    public String fugirDaZonaDePerigo() {
        Rectangle botArea = getAreaSolidaMundo();
        String[] direcoes = {"cima", "baixo", "esquerda", "direita"};
        String melhorDirecao = null;
        int maiorDistancia = -1;

        for (String dir : direcoes) {
            int deslocamento = gp.getTileSize();
            Rectangle areaTeste = new Rectangle(botArea);

            switch (dir) {
                case "cima" -> areaTeste.y -= deslocamento;
                case "baixo" -> areaTeste.y += deslocamento;
                case "esquerda" -> areaTeste.x -= deslocamento;
                case "direita" -> areaTeste.x += deslocamento;
            }

            boolean seguro = true;
            int menorDistanciaPerigo = Integer.MAX_VALUE;

            for (int i = 0; i < gp.obj.length; i++) {
                if (gp.obj[i] instanceof OBJ_Bomba bomba && bomba.isExplosaoAtiva()) {
                    for (Rectangle zona : bomba.getZonasExplosao()) {
                        if (zona != null) {
                            if (areaTeste.intersects(zona)) {
                                seguro = false;
                                menorDistanciaPerigo = 0;
                            } else {
                                int distancia = (int) areaTeste.getLocation().distance(zona.getLocation());
                                menorDistanciaPerigo = Math.min(menorDistanciaPerigo, distancia);
                            }
                        }
                    }
                }
            }

            if (seguro || menorDistanciaPerigo > maiorDistancia) {
                melhorDirecao = dir;
                maiorDistancia = menorDistanciaPerigo;
            }
        }

        if (melhorDirecao != null) {
            this.direcao = melhorDirecao;
            return melhorDirecao;
        } else {
            String aleatoria = escolherNovaDirecao();
            this.direcao = aleatoria;
            return aleatoria;
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
