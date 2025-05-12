package principal;

/**
 *
 * @author Mateu
 */

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        JFrame janela = new JFrame();
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setResizable(false); //para que não seja possível modificar o tamanho da janela
        janela.setTitle("BomberBot");
        
        GamePanel gamePanel = new GamePanel();
        janela.add(gamePanel);
        
        janela.pack(); //redimensiona a janela com base nos seus componentes(no caso, o GamePanel)
        
        janela.setLocationRelativeTo(null); //coloca a janela no centro da tela
        janela.setVisible(true);
        
        gamePanel.StartGameThread();
        
    }
    
}
