package principal;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Classe responsável por capturar e interpretar eventos de teclado durante o jogo.
 * Controla os movimentos do jogador, ações como colocar bombas, navegação nos menus,
 * e interações com o sistema de ranking.
 * 
 * Implementa a interface {@link KeyListener}.
 * 
 * @author Mateus
 * @version 5.0
 * @since 2025-05-04
 */
public class ManipuladorTeclado implements KeyListener { 
    
	GamePanel gp;
	
	/** Atributos para verficar se as teclas de movimento foram pressionadas. */
    private boolean cimaPress, baixoPress, esqPress, dirPress;
    //DEBUG
    
    /** Ativa a exibição do tempo de renderização para debug. */
    boolean checkDrawTime = false;
    
    /**verficar se a tecla de plantar bomba foi pressionadas. */
    private boolean teclaBombaPressionada;
    private UI ui;
    
    /**
     * Construtor que recebe o painel principal do jogo.
     *
     * @param gp Instância do GamePanel para manipulação do estado do jogo.
     */
    public ManipuladorTeclado(GamePanel gp) {
    	this.gp = gp;
    }
    
    /**
     * Evento chamado quando uma tecla que gera caractere é digitada.
     * Usado para digitação do nome no ranking.
     *
     * @param e Evento de teclado.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        if (gp.ui.aguardandoNome) {
            char c = e.getKeyChar();
            if (Character.isLetterOrDigit(c) && gp.ui.nomeDigitado.length() < gp.ui.getLIMITE_CARACTERES()) {
                gp.ui.nomeDigitado += c;
            } else if (c == '\b' && gp.ui.nomeDigitado.length() > 0) {
                gp.ui.nomeDigitado = gp.ui.nomeDigitado.substring(0, gp.ui.nomeDigitado.length() - 1);
            }
        }
    }

    /**
     * Evento chamado quando uma tecla é pressionada.
     * Controla movimentação, comandos no menu, bombas e pausas.
     *
     * @param e Evento de teclado.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); //retorna o int associado ao código da tecla
        
        if (gp.gameState == GameState.TITULO) {
            if (code == KeyEvent.VK_W) { //caso a tecla W seja pressionada
            	gp.ui.numComando--;
            	if (gp.ui.numComando < 0) {
            		gp.ui.numComando = 2;
            	}
            }
            if (code == KeyEvent.VK_S) { //caso a tecla W seja pressionada
            	gp.ui.numComando++;
            	if (gp.ui.numComando > 2) {
            		gp.ui.numComando = 0;
            	}
            }
            
            if(code == KeyEvent.VK_ENTER) {
        		if (gp.ui.numComando == 0){
        			gp.setGameState(GameState.FASE1);
        		}
        		if (gp.ui.numComando == 1) {
        			gp.setGameState(GameState.RANKING);
        		}
        		if (gp.ui.numComando == 2) {
        			System.exit(0);
        		}
            }
        }
        
        if (gp.gameState == GameState.PLAY) {
            //MOVIMENTOS
            if (code == KeyEvent.VK_W) { //caso a tecla W seja pressionada
            	this.setCimaPress(true);
            }
            if (code == KeyEvent.VK_A) { //caso a tecla A seja pressionada
            	this.setEsqPress(true);
            }
            if (code == KeyEvent.VK_S) { //caso a tecla S seja pressionada
            	this.setBaixoPress(true);
            }
            if (code == KeyEvent.VK_D) { //caso a tecla D seja pressionada
            	this.setDirPress(true);
            }
            
            //para colocar a bomba
            if (code == KeyEvent.VK_X) {
            	teclaBombaPressionada = true;
            }
        }
        
        
        if (code == KeyEvent.VK_P) { //caso a tecla P seja pressionada
        	if(gp.gameState == GameState.PLAY) {
        		gp.setGameState(GameState.PAUSE);
        	} else if (gp.gameState == GameState.PAUSE) {
        		gp.setGameState(GameState.PLAY);
        	}
        }
        
        
        if (gp.ui.aguardandoNome && code == KeyEvent.VK_ENTER) {
            if (!gp.ui.nomeDigitado.isBlank()) {
                gp.ui.salvarNoRanking(gp.ui.nomeDigitado.trim(), (int)(gp.getTempoTotalJogo()));
                gp.ui.aguardandoNome = false;
                gp.ui.nomeDigitado = "";
                gp.setGameState(GameState.RANKING);
            }
        }
        
        if(gp.gameState == GameState.RANKING && code == KeyEvent.VK_Z) {
        	gp.setGameState(GameState.TITULO);
        }
    }
    
    /**
     * Evento chamado quando uma tecla é solta.
     * Reseta os estados de movimentação e ações do jogador.
     *
     * @param e Evento de teclado.
     */
    @Override
    public void keyReleased(KeyEvent e) { 
        int code = e.getKeyCode(); //retorna o int associado ao código da tecla
        
        if (code == KeyEvent.VK_W) { //caso a tecla W seja pressionada
        	this.setCimaPress(false);
        }
        if (code == KeyEvent.VK_A) { //caso a tecla W seja pressionada
        	this.setEsqPress(false);
        }
        if (code == KeyEvent.VK_S) { //caso a tecla W seja pressionada
        	this.setBaixoPress(false);
        }
        if (code == KeyEvent.VK_D) { //caso a tecla W seja pressionada
        	this.setDirPress(false);
        }
        if (code == KeyEvent.VK_X) { //caso a tecla W seja pressionada
        	teclaBombaPressionada = false;
        }
    }

    //setters
    
    /**
     * Define se a tecla W (cima) está pressionada.
     */
	public void setCimaPress(boolean cimaPress) {
		this.cimaPress = cimaPress;
	}
	
	/**
     * Define se a tecla S (baixo) está pressionada.
     */
	public void setBaixoPress(boolean baixoPress) {
		this.baixoPress = baixoPress;
	}
	
	/**
     * Define se a tecla A (esquerda) está pressionada.
     */
	public void setEsqPress(boolean esqPress) {
		this.esqPress = esqPress;
	}
	
	/**
     * Define se a tecla D (direita) está pressionada.
     */
	public void setDirPress(boolean dirPress) {
		this.dirPress = dirPress;
	}
	
	/**
     * Define se a tecla de bomba (X) está pressionada.
     */
	public void setTeclaBombaPressionada(boolean teclaBombaPressionada) {
		this.teclaBombaPressionada = teclaBombaPressionada;
	}
	
	//getters
	
	/**
     * @return true se a tecla W está pressionada.
     */
	public boolean getCimaPress() {
		return cimaPress;
	}
	
	/**
     * @return true se a tecla S está pressionada.
     */
	public boolean getBaixoPress() {
		return baixoPress;
	}
	
	/**
     * @return true se a tecla A está pressionada.
     */
	public boolean getEsqPress() {
		return esqPress;
	}
	
	/**
     * @return true se a tecla D está pressionada.
     */
	public boolean getDirPress() {
		return dirPress;
	}
	
	/**
     * @return true se a tecla de bomba (X) está pressionada.
     */
	public boolean getTeclaBombaPressionada() {
		return teclaBombaPressionada;
	}
	
}
