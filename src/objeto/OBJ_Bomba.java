package objeto;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import principal.GamePanel;

public class OBJ_Bomba extends SuperObjeto{
	
	GamePanel gp;
	private int spriteCount = 0;
	private int spriteNum = 0;
	private boolean jogadorAindaDentro = true;
	private long tempoColocada;
	private boolean explodiu = false;

	
	public OBJ_Bomba(GamePanel gp) {
		
		this.gp = gp;

		nome = "Bomba";
	    this.tempoColocada = System.currentTimeMillis();

		try {
			imagem = ImageIO.read(getClass().getResourceAsStream("/objetos/bomba_1.png"));
			imagem1 = ImageIO.read(getClass().getResourceAsStream("/objetos/bomba_2.png"));
			imagem2 = ImageIO.read(getClass().getResourceAsStream("/objetos/bomba_3.png"));

			imagem = uTool.scaleImage(imagem, gp.getTileSize(), gp.getTileSize());
			imagem1 = uTool.scaleImage(imagem1, gp.getTileSize(), gp.getTileSize());
			imagem2 = uTool.scaleImage(imagem2, gp.getTileSize(), gp.getTileSize());

		} catch(IOException e) {
			e.printStackTrace();
		}
		
		colisao = false; //não se pode passar por cima da bomba
		//É PRECISO MODIFICAR PARA ATIVAR A COLISÃO APENAS DEPOIS
		
	}
	
	public void update() {
	    if (explodiu) return;

	    spriteCount++;
	    if (spriteCount > 15) {
	        spriteNum = (spriteNum + 1) % 3;
	        spriteCount = 0;
	    }

	    checaPresencaJogador(gp);

	    long tempoAtual = System.currentTimeMillis();
	    if (tempoAtual - tempoColocada >= 5000) { // 5 segundos
	        explodir();
	    }
	}

	private void explodir() {
	    explodiu = true;
	    // Aqui você pode adicionar animação ou lógica de explosão.
	    removerDoJogo(); // removê-la do array do GamePanel
	}

	private void removerDoJogo() {
	    for (int i = 0; i < gp.obj.length; i++) {
	        if (gp.obj[i] == this) {
	            gp.obj[i] = null;
	            gp.setBombaAtiva(false); // libera para colocar nova bomba
	            break;
	        }
	    }
	}

	@Override
	public void desenhar(Graphics2D g2, GamePanel gp) {
	    BufferedImage imagemAtual = getImagemAtual();

	    int telaX = gp.getJogador().getTelaX();
	    int telaY = gp.getJogador().getTelaY();

	    // Câmera
	    int camX = gp.getJogador().getMundoX() - telaX;
	    int camY = gp.getJogador().getMundoY() - telaY;

	    // Limita câmera
	    if (camX < 0) camX = 0;
	    else if (camX > gp.getTileSize() * gp.getMaxMundoCol() - gp.getScreenWidth())
	        camX = gp.getTileSize() * gp.getMaxMundoCol() - gp.getScreenWidth();

	    if (camY < 0) camY = 0;
	    else if (camY > gp.getTileSize() * gp.getMaxMundoLin() - gp.getScreenHeight())
	        camY = gp.getTileSize() * gp.getMaxMundoLin() - gp.getScreenHeight();

	    // Posição do desenho em relação à câmera
	    int drawX = mundoX - camX;
	    int drawY = mundoY - camY;

	    // Desenha se estiver visível
	    if (drawX + gp.getTileSize() > 0 &&
	        drawX < gp.getScreenWidth() &&
	        drawY + gp.getTileSize() > 0 &&
	        drawY < gp.getScreenHeight()) {

	        g2.drawImage(imagemAtual, drawX, drawY, gp.getTileSize(), gp.getTileSize(), null);
	    }
	}

	
	public BufferedImage getImagemAtual() {
	    switch (spriteNum) {
	        case 0: return imagem;
	        case 1: return imagem1;
	        case 2: return imagem2;
	        default: return imagem;
	    }
	}
	
	public void checaPresencaJogador(GamePanel gp) {
	    // Área da bomba
	    Rectangle areaBomba = new Rectangle(
	        mundoX + areaSolida.x,
	        mundoY + areaSolida.y,
	        areaSolida.width,
	        areaSolida.height
	    );

	    // Área do jogador
	    Rectangle areaJogador = new Rectangle(
	        gp.getJogador().getMundoX() + gp.getJogador().getAreaSolida().x,
	        gp.getJogador().getMundoY() + gp.getJogador().getAreaSolida().y,
	        gp.getJogador().getAreaSolida().width,
	        gp.getJogador().getAreaSolida().height
	    );

	    // Se ainda estiver dentro, não ativa a colisão
	    if (areaBomba.intersects(areaJogador)) {
	        jogadorAindaDentro = true;
	        this.colisao = false;
	    } else {
	        if (jogadorAindaDentro) {
	            jogadorAindaDentro = false;
	            this.colisao = true; // ativa colisão só quando sair
	        }
	    }
	}



}
