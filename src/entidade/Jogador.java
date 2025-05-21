package entidade;

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
     
     public Jogador(GamePanel gp, ManipuladorTeclado keyH) {
         this.gp = gp;
         this.keyH = keyH;
         
         telaX = gp.screenWidth / 2 - (gp.tileSize / 2);
         
         setDefaultValues();
         getImagemJogador();
     }
     
     public void setDefaultValues() {
         x = gp.tileSize * 1;
         mundoX = gp.tileSize * 1;
         y = gp.tileSize * 1;
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
                y -= velocidade; //através da velocidade determinamos quantos pixels o player se move
            } else if (keyH.baixoPress == true) {
            	direcao = "baixo";
                y += velocidade;
            } else if (keyH.esqPress == true) {
            	direcao = "esquerda";
                x -= velocidade;
                mundoX -= velocidade;
            } else if (keyH.dirPress == true) {
            	direcao = "direita";
                x += velocidade;
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
    	    BufferedImage imagem = null;

    	    switch (direcao) {
    	        case "cima": imagem = (spriteNum == 1) ? cima1 : cima2; break;
    	        case "baixo": imagem = (spriteNum == 1) ? baixo1 : baixo2; break;
    	        case "esquerda": imagem = (spriteNum == 1) ? esq1 : esq2; break;
    	        case "direita": imagem = (spriteNum == 1) ? dir1 : dir2; break;
    	    }

    	    int drawX = gp.jogador.telaX;

    	    // Bordas do mapa
    	    if (mundoX < telaX) {
    	        drawX = mundoX; // início do mapa
    	    } else if (mundoX > gp.tileSize * gp.maxMundoCol - (gp.screenWidth - telaX)) {
    	        drawX = mundoX - (gp.tileSize * gp.maxMundoCol - gp.screenWidth); // final do mapa
    	    }

    	    g2.drawImage(imagem, drawX, y, gp.tileSize, gp.tileSize, null);
	}


}
