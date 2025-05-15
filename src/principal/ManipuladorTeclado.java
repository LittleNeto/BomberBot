package principal;

/**
 *
 * @author Mateus
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ManipuladorTeclado implements KeyListener { 
//implementa uma interface para "ler" as teclas pressionadas
    
    public boolean cimaPress, baixoPress, esqPress, dirPress;
    
    @Override
    public void keyTyped(KeyEvent e) {
    } //não será utilizado

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); //retorna o int associado ao código da tecla
        
        if (code == KeyEvent.VK_W) { //caso a tecla W seja pressionada
            cimaPress = true;
        }
        if (code == KeyEvent.VK_A) { //caso a tecla W seja pressionada
            esqPress = true;
        }
        if (code == KeyEvent.VK_S) { //caso a tecla W seja pressionada
            baixoPress = true;
        }
        if (code == KeyEvent.VK_D) { //caso a tecla W seja pressionada
            dirPress = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { 
        int code = e.getKeyCode(); //retorna o int associado ao código da tecla
        
        if (code == KeyEvent.VK_W) { //caso a tecla W seja pressionada
            cimaPress = false;
        }
        if (code == KeyEvent.VK_A) { //caso a tecla W seja pressionada
            esqPress = false;
        }
        if (code == KeyEvent.VK_S) { //caso a tecla W seja pressionada
            baixoPress = false;
        }
        if (code == KeyEvent.VK_D) { //caso a tecla W seja pressionada
            dirPress = false;
        }
    }
    
}
