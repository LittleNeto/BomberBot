package principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import objeto.OBJ_Vida;
import objeto.SuperObjeto;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font retroGame;
	BufferedImage vida_cheia, vida_media, vida_baixa, vida_vazia;
	public boolean mensagemLig;
	public String mensagem = "";
	int mensagemCont;
	public boolean jogoAcabado = false; 
	public int numComando = 0;
	
	double tempoJogo = 200;
	DecimalFormat dFormt = new DecimalFormat("#0"); //para configurar como o tempo é mostrado na tela
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		try {
			InputStream is = getClass().getResourceAsStream("/fontes/RETRO GAMING.TTF");
			retroGame = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//CRIANDO O OBJETO
		SuperObjeto vida = new OBJ_Vida(gp);
		vida_cheia = vida.imagem;
		vida_media = vida.imagem1;
		vida_baixa = vida.imagem2;
		vida_vazia = vida.imagem3;
		
	}
	
	public void mostrarMensagem(String texto) {
		mensagem = texto;
		mensagemLig = true;
	}
	
	public void desenhar(Graphics2D g2) {
		
		this.g2 = g2;
		g2.setFont(retroGame);
		g2.setColor(Color.white);
		
		//Title State
		if(gp.gameState == GameState.TITULO) {
			desenharTelaTitle();
		}
		
		//Play state
		if(gp.gameState == GameState.PLAY) {
			desenharVidaJogador();
		}
		//Pause state
		if(gp.gameState == GameState.PAUSE) {
			desenharVidaJogador();
			desenharTelaPausa();
		}
	}
	
	public void desenharVidaJogador() {

	    int x = gp.getTileSize() / 2;
	    int y = -10;

	    int vida = gp.getJogador().getVida(); // 0, 1, 2 ou 3

	    BufferedImage img;

	    switch (vida) {
	        case 3:
	            img = vida_cheia;
	            break;
	        case 2:
	            img = vida_media;
	            break;
	        case 1:
	            img = vida_baixa;
	            break;
	        default:
	            img = vida_vazia;
	            break;
	    }

	    g2.drawImage(img, x, y, null);
	}



	
	public void desenharTelaTitle() {
		
		//Background
		g2.setColor(new Color(30, 30, 102));
		g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
		
		//Nome do título
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110F));
		String texto = "";
		int x = getXparaTextoCentralizado(texto);
		int y = gp.getTileSize() * 2;
		
		g2.setColor(Color.orange);

		
		
		//imagem
		x = gp.getScreenWidth() / 2 - (gp.getTileSize() * 2) /2;
		y += gp.getTileSize()  * 1;
		
		g2.drawImage(setup("BomberBot_titulo"), (gp.getScreenWidth() - 1000) /2, y, null);
		
		//MENU
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
		
		texto = "Novo jogo";
		x = getXparaTextoCentralizado(texto);
		y += gp.getTileSize() * 4;
		g2.drawString(texto, x, y);
		if (numComando == 0) {
			g2.drawString(">", x = gp.getTileSize() * 6, y);
		}
		
		texto = "Ranking";
		x = getXparaTextoCentralizado(texto);
		y += gp.getTileSize();
		g2.drawString(texto, x, y);
		if (numComando == 1) {
			g2.drawString(">", x = gp.getTileSize() * 6, y);
		}
		
		texto = "Sair";
		x = getXparaTextoCentralizado(texto);
		y += gp.getTileSize();
		g2.drawString(texto, x, y);
		if (numComando == 2) {
			g2.drawString(">", x = gp.getTileSize() * 6, y);
		}
		
	}
	
    public BufferedImage setup(String imagemNome) {
    	BufferedImage imagem = null;
    	
    	try {
    		imagem = ImageIO.read(getClass().getResourceAsStream("/fontes/" + imagemNome + ".png"));
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    	return imagem;
    }
	
	public void desenharTelaPausa() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 100F));
		String texto = "PAUSADO";
		int x = getXparaTextoCentralizado(texto);
		int y = gp.getScreenHeight()/2;
		
		g2.drawString(texto, x, y);
	}
	
	public int getXparaTextoCentralizado(String texto) {
		int length = (int)g2.getFontMetrics().getStringBounds(texto, g2).getWidth();
		int x =  gp.getScreenWidth()/2 - length/2;
		
		return x;
	}
	
}

