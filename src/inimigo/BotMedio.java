package inimigo;

import entidade.BotPersonagem;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import principal.GamePanel;

public class BotMedio extends BotPersonagem{

    Random random = new Random();

    public BotMedio(GamePanel gp) {
        super(gp);
        velocidade = 3;
        direcao = "baixo";

        areaSolida = new Rectangle(16, 32, 64, 64);
        areaSolidaDefaultX = areaSolida.x;
        areaSolidaDefaultY = areaSolida.y;

        vidaMax = 2;
        vida = vidaMax;

        tipo = 1;
        getImagem();
    }

    public void getImagem() {
        try {
            this.setCima1(ImageIO.read(getClass().getResourceAsStream("/inimigo/botB_cima_1.png")));
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

    @Override
    public void setAction() {
    	switch(estadoAtual) {
    	case ANDANDO -> {
    		if(getColisaoLig()) {
    			estadoAtual = EstadoBot.COLIDIU;
    			contadorDeEspera = 0;
    		}
    	}
    	case COLIDIU ->{
    		contadorDeEspera++;
    		if(contadorDeEspera >= tempoDeEspera) {
    			estadoAtual = EstadoBot.ESPERANDO_NOVA_DIRECAO;
    		}
    	}
    	
    	case ESPERANDO_NOVA_DIRECAO ->{
    		direcao = escolherNovaDirecao();
    		estadoAtual = EstadoBot.ANDANDO;
    		seguirJogador();
    	}
	}
	
		actionLockCounter++;
		if (actionLockCounter >= 360 && estadoAtual == EstadoBot.ANDANDO) {
	        direcao = escolherNovaDirecao();
	        actionLockCounter = 0;
	    }
    }


    

    public void plantarBomba() {
        
    }
}
