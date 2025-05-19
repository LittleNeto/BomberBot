package entidade;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import principal.GamePanel;
import principal.ManipuladorTeclado;

/**
 *
 * @author Mateus
 */
public class Jogador extends Personagem{
     GamePanel gp;
     ManipuladorTeclado keyH;
     public final int telaX;
     public final int telaY;

     
     public Jogador(GamePanel gp, ManipuladorTeclado keyH) {
         this.gp = gp;
         this.keyH = keyH;
         
         telaX = gp.screenWidth / 2 - (gp.tileSize / 2); //para o jogador aparecer na metade da tela
         telaY = gp.screenHeight / 2 - (gp.tileSize / 2);
         
         setDefaultValues();
         getImagemJogador();
     }
     
     public void setDefaultValues() {
         mundoX = gp.tileSize * 1; //posição do jogador no começo do jogo
         mundoY = gp.tileSize * 1;
         velocidade = 3;
         direcao = "baixo"; //a string será usada em um switch para determinar a imagem correspondente
     }
     public void getImagemJogador() {
    	 
    	 try {
    		 
    		 cima1 = ImageIO.read(getClass().getResourceAsStream("/jogador/player_cima_1.png")); //pega cada png da pasta de sprites
    		 cima2 = ImageIO.read(getClass().getResourceAsStream("/jogador/player_cima_2.png")); 
    		 baixo1 = ImageIO.read(getClass().getResourceAsStream("/jogador/player_baixo_1.png")); 
    		 baixo2 = ImageIO.read(getClass().getResourceAsStream("/jogador/player_baixo_2.png")); 
    		 esq1 = ImageIO.read(getClass().getResourceAsStream("/jogador/player_esquerda_1.png")); 
    		 esq2 = ImageIO.read(getClass().getResourceAsStream("/jogador/player_esquerda_2.png")); 
    		 dir1 = ImageIO.read(getClass().getResourceAsStream("/jogador/player_direita_1.png")); 
    		 dir2 = ImageIO.read(getClass().getResourceAsStream("/jogador/player_direita_2.png")); 

    	 } catch (IOException e) {
    		 e.printStackTrace();
    	 }
     }
     
     public void update() { //o método update é chamado 60 vezes por segundo
    	if(keyH.baixoPress == true || keyH.cimaPress == true || keyH.dirPress == true || keyH.esqPress == true) { 
    		//a lógica está dentro desse if para que o spriteCount só aumente quando uma das teclas está sendo pressionada
    		//assim o personagem só se move quando está andando
    		
            if (keyH.cimaPress == true) {
            	direcao = "cima";
            	mundoY -= velocidade; //através da velocidade determinamos quantos pixels o player se move
            } else if (keyH.baixoPress == true) {
            	direcao = "baixo";
            	mundoY += velocidade;
            } else if (keyH.esqPress == true) {
            	direcao = "esquerda";
            	mundoX -= velocidade;
            } else if (keyH.dirPress == true) {
            	direcao = "direita";
            	mundoX += velocidade;
            }
            
            spriteCount++; //lógica para que a img do jogador mude à cada 10 frames
            if(spriteCount == 20) {
            	if(spriteNum == 1) {
            		spriteNum = 2;
            	} else if (spriteNum == 2) {
            		spriteNum = 1;
            	}
            	spriteCount = 0;
            }
    		
    	}
    	
     }
     public void desenhar(Graphics2D g2) {
//        g2.setColor(Color.white);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
    	 
    	 BufferedImage imagem = null;
    	 
    	 switch(direcao) {
    	 case "cima":
    		 if (spriteNum == 1) {
    			 imagem = cima1;
    		 }
    		 if (spriteNum == 2) {
    			 imagem = cima2;
    		 }
    		 break;
    	 case "baixo":
    		 if (spriteNum == 1) {
    			 imagem = baixo1;
    		 }
    		 if (spriteNum == 2) {
    			 imagem = baixo2;
    		 }
    		 break;
    	 case "esquerda":
    		 if (spriteNum == 1) {
    			 imagem = esq1;
    		 }
    		 if (spriteNum == 2) {
    			 imagem = esq2;
    		 }
    		 break;
    	 case "direita":
    		 if (spriteNum == 1) {
    			 imagem = dir1;
    		 }
    		 if (spriteNum == 2) {
    			 imagem = dir2;
    		 }
    		 break;			  
    	 }
    	 
    	 g2.drawImage(imagem, telaX, telaY, gp.tileSize, gp.tileSize, null);
    	 
     }
}
