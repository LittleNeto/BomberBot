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
    
    public ManipuladorTeclado(GamePanel gp) {
    	this.gp = gp;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    } //não será utilizado

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
        			gp.setGameState(GameState.PLAY);
        		}
        		if (gp.ui.numComando == 1) {
        			//IMPLEMENTAR O RANKING
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
            if (code == KeyEvent.VK_A) { //caso a tecla W seja pressionada
            	this.setEsqPress(true);
            }
            if (code == KeyEvent.VK_S) { //caso a tecla W seja pressionada
            	this.setBaixoPress(true);
            }
            if (code == KeyEvent.VK_D) { //caso a tecla W seja pressionada
            	this.setDirPress(true);
            }
        }
        
        
        if (code == KeyEvent.VK_P) { //caso a tecla P seja pressionada
        	if(gp.gameState == GameState.PLAY) {
        		gp.setGameState(GameState.PAUSE);
        	} else if (gp.gameState == GameState.PAUSE) {
        		gp.setGameState(GameState.PLAY);
        	}
        }
        
        //DEBUG
        if (code == KeyEvent.VK_T) { //caso a tecla T seja pressionada
        	if (checkDrawTime == false) {
        		checkDrawTime = true;
        	} else if (checkDrawTime == true) {
        		checkDrawTime = false;
        	}
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
	
}
