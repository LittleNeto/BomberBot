package inimigo;

import entidade.Personagem;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import principal.GamePanel;

public class BotFacil extends Personagem{
    public BotFacil(GamePanel gp) {
        super(gp);
        velocidade = 3;
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
    
    public void setAction() {
    	
    	actionLockCounter++;
    	if (actionLockCounter == 120) { //estabelecer um intervalo para o bot mudar de direção, ele não muda por 120 frames (2 segundos aproximadamente)
        	Random random = new Random();
        	int i = random.nextInt(100) + 1; //pega um número aleatório de 0 à 99, o +1 impede que seja 0
        	 //25% de chance de ir para qualquer direção
        	if (i <= 25) {
        		direcao = "cima";
        	}
        	if (i > 25 && i <= 50) {
        		direcao = "baixo";
        	}
        	if (i > 50 && i <= 75) {
        		direcao = "esquerda";
        	}
        	if (i > 75 && i <= 100) {
        		direcao = "direita";
        	}
        	
        	actionLockCounter = 0;
    	}
    	
    }
}
