package principal;

/**
 *
 * @author Mateus
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ManipuladorTeclado implements KeyListener { 
//implementa uma interface para "ler" as teclas pressionadas
    
    private boolean cimaPress, baixoPress, esqPress, dirPress;
    
    @Override
    public void keyTyped(KeyEvent e) {
    } //não será utilizado

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); //retorna o int associado ao código da tecla
        
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