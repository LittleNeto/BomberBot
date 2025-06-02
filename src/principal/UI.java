package principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font arial_40;
	public boolean mensagemLig;
	public String mensagem = "";
	int mensagemCont;
	public boolean jogoAcabado = false; 
	
	double tempoJogo = 200;
	DecimalFormat dFormt = new DecimalFormat("#0"); //para configurar como o tempo é mostrado na tela
	
	public UI(GamePanel gp) {
		this.gp = gp;
		arial_40 = new Font("Arial", Font.PLAIN, 40);
	}
	
	public void mostrarMensagem(String texto) {
		mensagem = texto;
		mensagemLig = true;
	}
	
	public void desenhar(Graphics2D g2) {
		
		this.g2 = g2;
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		if(gp.gameState == gp.playState) {
			//
		}
		if(gp.gameState == gp.pauseState) {
			desenharTelaPausa();
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
