package entidade;

import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import inimigo.EstadoBot;
import objeto.OBJ_Bomba;
import principal.GamePanel;

/**
 * Classe abstrata base para bots controlados por IA no jogo.
 * Define o comportamento geral de movimentação, plantio de bombas,
 * detecção de zonas perigosas e fuga estratégica.
 *
 * Subclasses devem implementar a lógica específica de decisão em setAction().
 *
 * @author júlio
 * @version 1.0
 * @since 2025-06-13
 */

public abstract class BotPersonagem extends Personagem {

    /** Estado atual do bot (ex: ANDANDO, COLIDIU, ESPERANDO_NOVA_DIRECAO) */
    protected EstadoBot estadoAtual = EstadoBot.ANDANDO;

    /** Tempo de espera (em frames) após colisão antes de escolher nova direção */
    protected int tempoDeEspera = 20;  // Aproximadamente 0.3 segundos
    
    /** Contador de frames do tempo de espera */
    protected int contadorDeEspera = 0; // Contador interno desse tempo de espera

    /** Número atual de bombas ativas colocadas por esse bot */
    protected int bombasAtivas = 0;       // Quantidade atual de bombas ativas desse bot
    
    /** Limite máximo de bombas que o bot pode plantar simultaneamente */
    protected final int limiteBombas = 3; // Quantidade máxima de bombas que o bot pode deixar no mapa

    // Guarda a posição onde a última bomba foi plantada
    
    /** Tile X da última bomba plantada (usado para evitar spam de bomba no mesmo lugar) */
    protected int ultimaBombaX = -1;
    
    /** Tile Y da última bomba plantada */
    protected int ultimaBombaY = -1;
    
    /** Tempo de espera entre plantações de bomba (cooldown), em frames */
    protected int cooldownBomba = 60; // 60 frames ≈ 1 segundo
    
    /** Contador de frames do cooldown da bomba */
    protected int contadorCooldownBomba = 0;

    /** Distância máxima (em pixels) para começar a seguir o jogador */
    protected int distanciaMax = gp.getTileSize() * 6;

    /** Gerador de direções aleatórias */
    protected Random random = new Random();

    /**
     * Construtor.
     *
     * @param gp Referência ao GamePanel (contexto do jogo).
     */
    public BotPersonagem(GamePanel gp) {
        super(gp);
    }

    /**
     * Escolhe aleatoriamente uma direção entre cima, baixo, esquerda e direita.
     *
     * @return Direção aleatória como string.
     */
    public String escolherNovaDirecao() {
        String[] direcoes = {"cima", "baixo", "esquerda", "direita"};
        return direcoes[random.nextInt(direcoes.length)];
    }

    /**
     * Define a direção do bot em direção ao jogador, se ele estiver próximo.
     *
     * @param distanciaMax Distância máxima (em pixels) para considerar o jogador "próximo".
     * @return true se o jogador estiver próximo e a direção foi ajustada; false caso contrário.
     */
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

    /**
     * Planta uma bomba na posição atual, respeitando cooldown, limite e proximidade do jogador.
     */
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





    /**
     * Decrementa o contador de bombas ativas após explosão.
     */
    public void decrementarBombas() {
        bombasAtivas = Math.max(0, bombasAtivas - 1);
    }

    /**
     * Verifica se o bot está dentro de uma zona de perigo (explosão ativa ou iminente).
     *
     * @return true se houver perigo na posição atual; false caso contrário.
     */
    public boolean estaNaZonaDePerigo() {
        Rectangle botArea = getAreaSolidaMundo();

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] instanceof OBJ_Bomba bomba) {
                int tempoRestante = bomba.getFramesRestantes();

                if (tempoRestante <= 180 || bomba.isExplosaoAtiva()) {
                    Rectangle[] zonas = bomba.getZonasExplosaoPrevisao();

                    for (Rectangle zona : zonas) {
                        if (zona != null && botArea.intersects(zona)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }




    /**
     * Calcula e aplica a melhor direção para fugir de explosões.
     * Direções colididas ou próximas da explosão são penalizadas.
     *
     * @return Direção segura escolhida ou aleatória se nenhuma for segura.
     */
    public String fugirDaZonaDePerigo() {
        Rectangle botArea = getAreaSolidaMundo();           // Posição atual do bot no mundo
        int tile = gp.getTileSize();                        // Tamanho de um tile

        // Define retângulos de teste para cada direção (cima, baixo, esquerda, direita)
        Map<String, Rectangle> direcoes = new HashMap<>();
        direcoes.put("cima",     new Rectangle(botArea.x, botArea.y - tile, botArea.width, botArea.height));
        direcoes.put("baixo",    new Rectangle(botArea.x, botArea.y + tile, botArea.width, botArea.height));
        direcoes.put("esquerda", new Rectangle(botArea.x - tile, botArea.y, botArea.width, botArea.height));
        direcoes.put("direita",  new Rectangle(botArea.x + tile, botArea.y, botArea.width, botArea.height));

        String melhorDirecao = null;           // Guarda a direção mais segura
        int melhorPontuacao = Integer.MIN_VALUE; // Inicializa a melhor pontuação como negativa

        // Avalia cada direção possível
        for (Map.Entry<String, Rectangle> entrada : direcoes.entrySet()) {
            String direcao = entrada.getKey();
            Rectangle areaTeste = entrada.getValue();

            // Ignora direções que colidem com o mapa (paredes, blocos, etc.)
            if (colideComTile(areaTeste)) continue;

            int pontuacao = 0; // Inicializa pontuação para essa direção

            // Percorre todas as bombas no jogo
            for (int i = 0; i < gp.obj.length; i++) {
                if (gp.obj[i] instanceof OBJ_Bomba bomba) {
                    int tempoRestante = bomba.getFramesRestantes();

                    // Só considera bombas prestes a explodir (até 2 segundos) ou já ativas
                    if (tempoRestante <= 120 || bomba.isExplosaoAtiva()) {
                        Rectangle[] zonas = bomba.getZonasExplosaoPrevisao();

                        // Para cada zona de explosão prevista
                        for (Rectangle zona : zonas) {
                            if (zona != null) {
                                if (areaTeste.intersects(zona)) {
                                    pontuacao -= 1000; // Penalidade alta se a direção cair numa explosão
                                } else {
                                    // Caso contrário, somamos a distância como pontuação positiva
                                    int distancia = (int) areaTeste.getLocation().distance(zona.getLocation());
                                    pontuacao += distancia;
                                }
                            }
                        }
                    }
                }
            }

            // Se a pontuação for melhor do que as anteriores, atualiza a melhor direção
            if (pontuacao > melhorPontuacao) {
                melhorPontuacao = pontuacao;
                melhorDirecao = direcao;
            }
        }

        // Se achou uma direção segura, aplica no bot
        if (melhorDirecao != null) {
            this.direcao = melhorDirecao;
            return melhorDirecao;
        } else {
            // Se todas as direções são ruins, escolhe uma aleatória como último recurso
            this.direcao = escolherNovaDirecao();
            return this.direcao;
        }
    }
    
    
    /**
     * Verifica se uma área simulada colide com algum tile de parede/bloco no mapa.
     *
     * @param areaTeste Área a testar.
     * @return true se houver colisão; false caso contrário.
     */
    private boolean colideComTile(Rectangle areaTeste) {
        int tileSize = gp.getTileSize();

        // Converte coordenadas de mundo para índices de tile (linhas e colunas)
        int col1 = areaTeste.x / tileSize;
        int row1 = areaTeste.y / tileSize;
        int col2 = (areaTeste.x + areaTeste.width - 1) / tileSize;
        int row2 = (areaTeste.y + areaTeste.height - 1) / tileSize;

        int[][] mapa = gp.getTileM().getGMapa().getGrade(); // Matriz de tiles do mapa atual

        // Percorre todos os tiles cobertos pela área simulada
        for (int r = row1; r <= row2; r++) {
            for (int c = col1; c <= col2; c++) {
                // Evita sair dos limites do mapa
                if (r < 0 || c < 0 || r >= mapa.length || c >= mapa[0].length) return true;

                int tileId = mapa[r][c];
                if (gp.getTileM().getTile()[tileId].getColisao()) {
                    return true; // Se o tile tem colisão, a área é inválida
                }
            }
        }
        return false; // Sem colisão
    }


    /**
     * Retorna a área sólida do bot com coordenadas absolutas no mundo.
     *
     * @return Retângulo representando a área sólida no mundo.
     */
    protected Rectangle getAreaSolidaMundo() {
        return new Rectangle(
            this.getMundoX() + areaSolida.x,
            this.getMundoY() + areaSolida.y,
            areaSolida.width,
            areaSolida.height
        );
    }

    
    /**
     * @return A distância máxima que o bot considera para seguir o jogador.
     */
    public int getDistanciaMax() {
        return distanciaMax;
    }
}
