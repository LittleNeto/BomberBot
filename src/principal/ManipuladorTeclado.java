package principal;

/**
 *
 * @author Mateus
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ManipuladorTeclado implements KeyListener { 
//implementa uma interface para "ler" as teclas pressionadas
    
	GamePanel gp;
    private boolean cimaPress, baixoPress, esqPress, dirPress;
    //DEBUG
    boolean checkDrawTime = false;
    private boolean teclaBombaPressionada;
    private UI ui;
    
    public ManipuladorTeclado(GamePanel gp) {
    	this.gp = gp;
    }
    
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
	public void setCimaPress(boolean cimaPress) {
		this.cimaPress = cimaPress;
	}

	public void setBaixoPress(boolean baixoPress) {
		this.baixoPress = baixoPress;
	}

	public void setEsqPress(boolean esqPress) {
		this.esqPress = esqPress;
	}

	public void setDirPress(boolean dirPress) {
		this.dirPress = dirPress;
	}
	public void setTeclaBombaPressionada(boolean teclaBombaPressionada) {
		this.teclaBombaPressionada = teclaBombaPressionada;
	}
	
	//getters
	public boolean getCimaPress() {
		return cimaPress;
	}
	
	public boolean getBaixoPress() {
		return baixoPress;
	}
	
	public boolean getEsqPress() {
		return esqPress;
	}
	
	public boolean getDirPress() {
		return dirPress;
	}

	public boolean getTeclaBombaPressionada() {
		return teclaBombaPressionada;
	}
	
}
