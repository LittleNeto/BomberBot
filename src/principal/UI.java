package principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font retroGame;
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
		if(gp.gameState == gp.titleState) {
			desenharTelaTitle();
		}
		
		//Play state
		if(gp.gameState == gp.playState) {
			//
		}
		//Pause state
		if(gp.gameState == gp.pauseState) {
			desenharTelaPausa();
		}
	}
	
	public void desenharTelaTitle() {
		
		//Background
		g2.setColor(new Color(30, 30, 102));
		g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
		
		//Nome do título
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110F));
		String texto = "BomberBot";
		int x = getXparaTextoCentralizado(texto);
		int y = gp.getTileSize() * 2;
		
		g2.setColor(Color.black);
		g2.drawString(texto, x + 7, y + 7);
		
		g2.setColor(Color.orange);
		g2.drawString(texto, x, y);
		
		
		//imagem
		x = gp.getScreenWidth() / 2 - (gp.getTileSize() * 2) /2;
		y += gp.getTileSize()  * 1;
		
		g2.drawImage(gp.getJogador().getBaixo1(), x, y, gp.getTileSize() * 2, gp.getTileSize() * 2, null);
		
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
