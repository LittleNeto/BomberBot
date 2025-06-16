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
import recursos.Ranking;
import recursos.RankingManager;


/**
 * Classe responsável pela renderização da interface gráfica do usuário (UI) do jogo.
 * Exibe informações como vida do jogador, tempo restante, telas de pausa, game over, ranking,
 * transições de fase e tela inicial. Também gerencia entrada de nome para cadastro no ranking.
 *
 * Utiliza recursos como fontes personalizadas, imagens para barras de vida e dados de ranking.
 *
 * @author Mateus
 * @version
 * @since 
 */
public class UI {
	
	GamePanel gp;
	private Graphics2D g2;
	Font retroGame;
	BufferedImage vida_cheia, vida_media, vida_baixa, vida_vazia;
	public boolean mensagemLig;
	public String mensagem = "";
	int mensagemCont;
	public boolean jogoAcabado = false; 
	public int numComando = 0;
	
	private double tempoJogo = 400;
	
	DecimalFormat dFormt = new DecimalFormat("#0"); //para configurar como o tempo é mostrado na tela
	
	private RankingManager rankM;
	public String nomeDigitado = "";
	private final int LIMITE_CARACTERES = 12;
	boolean aguardandoNome = false;


	/**
	 * Construtor que inicializa a UI com as fontes e imagens necessárias.
	 *
	 * @param gp Referência ao painel principal do jogo
	 */
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
	
	/**
	 * Exibe uma mensagem temporária na tela.
	 *
	 * @param texto Mensagem a ser exibida
	 */
	public void mostrarMensagem(String texto) {
		mensagem = texto;
		mensagemLig = true;
	}
	
	/**
	 * Controla a renderização da UI conforme o estado atual do jogo.
	 *
	 * @param g2 Objeto gráfico usado para desenhar os elementos
	 */
	public void desenhar(Graphics2D g2) {
		
		this.g2 = g2;
		g2.setFont(retroGame);
		g2.setColor(Color.white);
		
		//Title State
		if(gp.gameState == GameState.TITULO) {
			desenharTelaTitle();
		}
		
		//Ranking State
		if(gp.gameState == GameState.RANKING) {
			desenharTelaRanking();
		}
		
		//Cadastrar Ranking state
		if (gp.gameState == GameState.CADASTRAR_RANKING) {
			desenharTelaCadastrarRanking();
		}
		
		//Fases state
		if(gp.gameState == GameState.FASE1) {
			desenharTelaFase("Fase 1");
		}
		if(gp.gameState == GameState.FASE2) {
			desenharTelaFase("Fase 2");
		}
		if(gp.gameState == GameState.FASE3) {
			desenharTelaFase("Fase 3");
		}
		
		//Play state
		if(gp.gameState == GameState.PLAY) {
			desenharVidaJogador();
			desenharTempoJogo();
		}
		//Pause state
		if(gp.gameState == GameState.PAUSE) {
			desenharVidaJogador();
			desenharTempoJogo();
			desenharTelaPausa();
		}
		//Game Over state
		if(gp.gameState == GameState.GAME_OVER) {
			desenharVidaJogador();
			desenharTempoJogo();
			desenharTelaGameOver();
		}
	}
	
	/** Desenha a tela de ranking com os 5 melhores tempos. */
	private void desenharTelaRanking() {
	    g2.setColor(new Color(30, 30, 102));
	    g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());

	    g2.setColor(Color.white);
	    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36f));
	    String titulo = "Top 5 Ranking";
	    int y = gp.getTileSize() * 2;
	    g2.drawString(titulo, getXparaTextoCentralizado(titulo), y);

	    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30f));
	    java.util.List<Ranking> top5 = RankingManager.getTop5();

	    for (int i = 0; i < top5.size(); i++) {

	    	Ranking r = top5.get(i);
	        String linha = (i + 1) + " - " + r.getJogador() + " - " + r.getTempo() + "s" + " - " +  r.getDataFormatada();
	        y += gp.getTileSize();
	        g2.drawString(linha, getXparaTextoCentralizado(linha), y);
	    }

	    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 26f));
	    g2.drawString("Pressione Z para voltar", getXparaTextoCentralizado("Pressione Z para voltar"), y + gp.getTileSize() * 2);
		
	}
	
	/** Desenha a tela para digitação do nome no ranking. */
	private void desenharTelaCadastrarRanking() {
	    g2.setColor(new Color(30, 30, 102));
	    g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());

	    g2.setColor(Color.white);
	    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36f));
	    String texto = "Digite seu nome:";
	    int x = getXparaTextoCentralizado(texto);
	    int y = gp.getScreenHeight() / 2 - gp.getTileSize();
	    g2.drawString(texto, x, y);

	    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32f));
	    String nome = nomeDigitado + "_";
	    x = getXparaTextoCentralizado(nome);
	    y += gp.getTileSize();
	    g2.drawString(nome, x, y);
	}

	/** Desenha o tempo restante de jogo na tela. */
	public void desenharTempoJogo() {
	    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
	    g2.setColor(Color.white);

	    String textoTempo = "TEMPO: " + dFormt.format(tempoJogo);
	    int x = gp.getTileSize() * 14;
	    int y = 64;

	    g2.drawString(textoTempo, x, y);
	}
	
	/** Atualiza o tempo restante de jogo 
	 * 
	 * @param delta do loop. 
	 */
	public void atualizarTempoJogo(double delta) {
	    if (gp.gameState == GameState.PLAY && tempoJogo > 0) {
	        tempoJogo -= delta;
	        if (tempoJogo < 0) {
	            tempoJogo = 0;
	        }
	    }
	}


	/** Renderiza as barras de vida do jogador. */
	public void desenharVidaJogador() {
	    int x = gp.getTileSize() / 2;
	    int y = 0;

	    int vida = gp.getJogador().getVida(); //de 0 a 6

	    BufferedImage imgBarra1;
	    BufferedImage imgBarra2;

	    int vida1 = Math.min(vida, 3);
	    switch (vida1) {
	        case 3:
	            imgBarra1 = vida_cheia;
	            break;
	        case 2:
	            imgBarra1 = vida_media;
	            break;
	        case 1:
	            imgBarra1= vida_baixa;
	            break;
	        default:
	            imgBarra1 = vida_vazia;
	            break;
	    }

	    int vida2 = vida - 3;
	    if (vida2 <= 0) {
	        imgBarra2 = vida_vazia;
	    } else {
	        switch (vida2) {
	            case 3:
	                imgBarra2 = vida_cheia;
	                break;
	            case 2:
	                imgBarra2 = vida_media;
	                break;
	            case 1:
	                imgBarra2 = vida_baixa;
	                break;
	            default:
	                imgBarra2 = vida_vazia;
	                break;
	        }
	    }

	    g2.drawImage(imgBarra1, x, y, null);

	    g2.drawImage(imgBarra2, x + gp.getTileSize() + 5, y, null);
	}

	/** Desenha a tela de título com as opções do menu. */
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
	
	/**
	 * Carrega uma imagem de recurso com base no nome.
	 *
	 * @param imagemNome Nome do arquivo de imagem (sem extensão)
	 * @return BufferedImage carregada
	 */
    public BufferedImage setup(String imagemNome) {
    	BufferedImage imagem = null;
    	
    	try {
    		imagem = ImageIO.read(getClass().getResourceAsStream("/fontes/" + imagemNome + ".png"));
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    	return imagem;
    }
	
    /** Desenha a tela de pausa. */
	public void desenharTelaPausa() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 100F));
		String texto = "PAUSADO";
		int x = getXparaTextoCentralizado(texto);
		int y = gp.getScreenHeight()/2;
		
		g2.drawString(texto, x, y);
	}
	
	/** Desenha a tela de Game Over. */
	public void desenharTelaGameOver() {
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
		
		int x;
		int y;
		String texto;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
		
		texto = "GAME OVER";
		
		//sombra
		g2.setColor(Color.black);
		x = getXparaTextoCentralizado(texto);
		y = gp.getTileSize() * 4;
		g2.drawString(texto, x, y);
		
		//main
		g2.setColor(Color.white);
		g2.drawString(texto, x - 4, y - 4);
		
	}
	
	/** Desenha a tela de transição entre fases
	 * @param nome da fase.
	 */
	public void desenharTelaFase(String texto) {
		g2.setColor(new Color(30, 30, 102));
		g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
		
		int x;
		int y;

		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
		
		//sombra
		g2.setColor(Color.black);
		x = getXparaTextoCentralizado(texto);
		y = gp.getTileSize() * 4;
		g2.drawString(texto, x, y);
		
		//main
		g2.setColor(Color.white);
		g2.drawString(texto, x - 4, y - 4);
		

	}
	
	/** Salva um nome e tempo no sistema de ranking. 
	 * @param nome do jogadpr
	 * @param tempo em que terminou o jogo
	 */
	public void salvarNoRanking(String nome, int tempo) {
	    RankingManager.salvarDados(nome, tempo);
	}

	/**
	 * Calcula a posição X para centralizar um texto na tela.
	 *
	 * @param texto Texto a ser centralizado
	 * @return posição X em pixels
	 */
	public int getXparaTextoCentralizado(String texto) {
		int length = (int)g2.getFontMetrics().getStringBounds(texto, g2).getWidth();
		int x =  gp.getScreenWidth()/2 - length/2;
		
		return x;
	}
	
	/** @return tempo restante de jogo. */
	public double getTempoJogo() {
		return tempoJogo;
	}
	
	/** Define o tempo restante do jogo. */
	public void setTempoJogo(double tempoJogo) {
		this.tempoJogo = tempoJogo;
	}
	
	/** @return objeto gráfico atual. */
	public Graphics2D getG2() {
		return this.g2;
	}
	
	/** Define o objeto gráfico da UI. */
	public void setG2(Graphics2D g2) {
		this.g2 = g2;
	}
	
	/** @return número máximo de caracteres permitidos no nome do ranking. */
	public int getLIMITE_CARACTERES() {
		return LIMITE_CARACTERES;
	}
	
	
}
