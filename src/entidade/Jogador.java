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
public class Jogador extends Personagem {
     private GamePanel gp;
     private ManipuladorTeclado keyH;
     
     private final int telaX;
     
     public Jogador(GamePanel gp, ManipuladorTeclado keyH) {
    	 super(16, 32, 64, 64);
         this.gp = gp;
         this.keyH = keyH;
         
         telaX = this.gp.getScreenWidth() / 2 - (this.gp.getTileSize() / 2);
         
         this.setDefaultValues();
         this.getImagemJogador();
     }
     
     public void setDefaultValues() {
    	 this.setX(this.getGp().getTileSize());
         this.setMundoX(this.getGp().getTileSize());
         this.setY(this.getGp().getTileSize());
         this.setVelocidade(3);
         this.setDirecao("baixo"); //a string será usada em um switch para determinar a imagem correspondente
     }
     public void getImagemJogador() {
    	 
    	 try {
    		 
    		 this.setCima1(ImageIO.read(getClass().getResourceAsStream("/jogador/player_cima_1.png"))); //pega cada png da pasta de sprites
    		 this.setCima2(ImageIO.read(getClass().getResourceAsStream("/jogador/player_cima_2.png"))); 
    		 this.setBaixo1(ImageIO.read(getClass().getResourceAsStream("/jogador/player_baixo_1.png"))); 
    		 this.setBaixo2(ImageIO.read(getClass().getResourceAsStream("/jogador/player_baixo_2.png"))); 
    		 this.setEsq1(ImageIO.read(getClass().getResourceAsStream("/jogador/player_esquerda_1.png"))); 
    		 this.setEsq2(ImageIO.read(getClass().getResourceAsStream("/jogador/player_esquerda_2.png"))); 
    		 this.setDir1(ImageIO.read(getClass().getResourceAsStream("/jogador/player_direita_1.png"))); 
    		 this.setDir2(ImageIO.read(getClass().getResourceAsStream("/jogador/player_direita_2.png"))); 

    	 } catch (IOException e) {
    		 e.printStackTrace();
    	 }
     }
     
     public void update() { //o método update é chamado 60 vezes por segundo
    	if(this.getKeyH().getBaixoPress() || this.getKeyH().getCimaPress() || this.getKeyH().getDirPress() || this.getKeyH().getEsqPress()) { 
    		//a lógica está dentro desse if para que o spriteCount só aumente quando uma das teclas está sendo pressionada
    		//assim o personagem só se move quando está andando
    		
            if (this.getKeyH().getCimaPress()) {
            	setDirecao("cima");
            } else if (this.getKeyH().getBaixoPress()) {
            	this.setDirecao("baixo");
            } else if (this.getKeyH().getEsqPress()) {
            	this.setDirecao("esquerda");
            } else if (this.getKeyH().getDirPress()) {
            	this.setDirecao("direita");
            }
            
            //CHECA A COLISÃO DO TILE
            this.setColisaoLig(false);
            this.getGp().getcCheca().checaTile(this);
            
            //SE A COLISÃO FRO FALSA, O JOGADOR PODE SE MOVER
            if (!this.getColisaoLig()) { //para que o player só possa se mover quando o bloco não é sólido
            	
            	switch (this.getDirecao()) {
            	case "cima":
            		this.setY(this.getY() - this.getVelocidade()); //através da velocidade determinamos quantos pixels o player se move
            		break;
            	case "baixo":
            		this.setY(this.getY() + this.getVelocidade());
            		break;
            	case "esquerda":
            		this.setX(this.getX() - this.getVelocidade());
            		this.setMundoX(this.getMundoX() - this.getVelocidade());
            		break;
            	case "direita":
            		this.setX(this.getX() + this.getVelocidade());
            		this.setMundoX(this.getMundoX() + this.getVelocidade());
            		break;
            	}
            	
            }
            
            this.setSpriteCount(this.getSpriteCount() + 1); //lógica para que a imagem do jogador mude a cada 15 frames

            if (this.getSpriteCount() > 15) {
                if (this.getSpriteNum() == 1) {
                    this.setSpriteNum(2);
                } else {
                    this.setSpriteNum(1);
                }
                this.setSpriteCount(0);
            }

    		
    	}
    	
     }
     public void desenhar(Graphics2D g2) {
	    BufferedImage imagem = null;

	    switch (this.getDirecao()) {
	    	case "cima": imagem = (this.getSpriteNum() == 1) ? this.getCima1() : this.getCima2(); break;
	    	case "baixo": imagem = (this.getSpriteNum() == 1) ? this.getBaixo1() : this.getBaixo2(); break;
	    	case "esquerda": imagem = (this.getSpriteNum() == 1) ? this.getEsq1() : this.getEsq2(); break;
	    	case "direita": imagem = (this.getSpriteNum() == 1) ? this.getDir1() : this.getDir2(); break;
	    }


	    int drawX = this.getGp().getJogador().getTelaX();

	    // Bordas do mapa
	    if (this.getMundoX() < this.getTelaX()) {
	        drawX = this.getMundoX(); // início do mapa
	    } else if (this.getMundoX() > this.getGp().getTileSize() * this.getGp().getMaxMundoCol() - (this.getGp().getScreenWidth() - this.getTelaX())) {
	        drawX = this.getMundoX() - (this.getGp().getTileSize() * this.getGp().getMaxMundoCol() - this.getGp().getScreenWidth()); // final do mapa
	    }

	    g2.drawImage(imagem, drawX, this.getY(), this.getGp().getTileSize(), this.getGp().getTileSize(), null);
	}

    //setters
	public void setGp(GamePanel gp) {
		this.gp = gp;
	}

	public void setKeyH(ManipuladorTeclado keyH) {
		this.keyH = keyH;
	}
     
    //getters
	public GamePanel getGp() {
		return gp;
	}
	
	public ManipuladorTeclado getKeyH() {
		return keyH;
	}
	
	public int getTelaX() {
		return telaX;
	}

}
