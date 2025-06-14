package inimigo;

import entidade.Personagem;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import principal.GamePanel;

public class BotFacil extends Personagem implements Bot_if{
	
	Random random = new Random();
	private EstadoBot estadoAtual = EstadoBot.ANDANDO;
	private int tempoDeEspera = 20; //frames de espera entre uma cosão e a escolha da nova direção (0.3s)
	private int contadorDeEspera = 0;
	
	
    public BotFacil(GamePanel gp) {
        super(gp);
        velocidade = 1;
        direcao = "baixo"; 
        areaSolida = new Rectangle();
        areaSolida.x = 16;
        areaSolida.y = 32;
        areaSolida.width = 64;
        areaSolida.height = 64;
        
        areaSolidaDefaultX = areaSolida.x;
        areaSolidaDefaultY = areaSolida.y;
        
        vidaMax = 1;
        vida = vidaMax;
        
        getImagem();
    }
    public void getImagem() {
        try {

               this.setCima1(ImageIO.read(getClass().getResourceAsStream("/inimigo/botA_cima_1.png"))); //pega cada png da pasta de sprites
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
	    		escolherNovaDirecao();
	    		estadoAtual = EstadoBot.ANDANDO;
	    	}
    	}
    	
    	actionLockCounter++;
    	if (actionLockCounter >= 360 && estadoAtual == EstadoBot.ANDANDO) {
            escolherNovaDirecao();
            actionLockCounter = 0;
        }
    }
    
    private void escolherNovaDirecao() {
        String[] direcoes = {"cima", "baixo", "esquerda", "direita"};
        direcao = direcoes[random.nextInt(direcoes.length)];
    }
}
