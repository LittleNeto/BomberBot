package entidade;

import java.util.Random;

import inimigo.EstadoBot;
import principal.GamePanel;

public abstract class BotPersonagem extends Personagem {

    protected EstadoBot estadoAtual = EstadoBot.ANDANDO;
    protected int tempoDeEspera = 20; // frames de espera após colisão (0,3s)
    protected int contadorDeEspera = 0;

    public BotPersonagem(GamePanel gp) {
        super(gp);
    }
    
    public String escolherNovaDirecao() {
        String[] direcoes = {"cima", "baixo", "esquerda", "direita"};
        return direcoes[new Random().nextInt(direcoes.length)];
    }
    
    public void seguirJogador() {
    	int dx = gp.getJogador().getMundoX() - this.getMundoX();
        int dy = gp.getJogador().getMundoY() - this.getMundoY();

        if (Math.abs(dx) > Math.abs(dy)) {
            direcao = (dx > 0) ? "direita" : "esquerda";
        } else {
            direcao = (dy > 0) ? "baixo" : "cima";
        }
    }

}
