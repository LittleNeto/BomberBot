package inimigo;


import entidade.Personagem;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import principal.GamePanel;

public class BotMedio extends Personagem implements Bot_if {
	
	public BotMedio(GamePanel gp) {
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

	tipo = 1;
        
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

}
