package principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

public class UI {
	
	GamePanel gp;
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
		
		//podemos adaptar um pouco essa lógica para passar de fase
		if(jogoAcabado == true) {
			
//			g2.setFont(arial_40);
//			g2.setColor(Color.white);
//			
//			String texto;
//			int textoLength;
//			int x;
//			int y;
//			
//			texto = "Você ganhou!";
//			textoLength = (int) g2.getFontMetrics().getStringBounds(texto, g2).getWidth(); //retorna a largura do texto
//			
//			x = gp.getScreenWidth()/2 - textoLength/2;
//			y = gp.getScreenHeight()/2 - gp.getTileSize() * 3;
//			g2.drawString(texto, x, y);
//			
//			gp.setGameThread(null);
			
		} else {
			g2.setFont(arial_40);
			g2.setColor(Color.white);
//			g2.drawString("VL = " + gp.getJogador().getVelocidade(), 50, 50);
			
			//TEMPO
			tempoJogo -= (double) 1/60;
			g2.drawString("TEMPO: " + dFormt.format(tempoJogo), gp.getTileSize() * 13, 50);
			
			//MENSAGEM
//			if (mensagemLig == true) {
//				g2.setFont(g2.getFont().deriveFont(30F));
//				g2.drawString(mensagem, gp.getTileSize()/2, gp.getTileSize() * 5);
//				
//				mensagemCont++;
//				
//				if (mensagemCont > 120) { //60 fps, então seriam 2 segundos
//					mensagemCont = 0;
//					mensagemLig = false;
//				}
//			}
		}
		

	}
	
	
}
