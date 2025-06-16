package principal;


import javax.swing.JFrame;

/**
 * Classe Main principal que inicializa o jogo BomberBot.
 * Cria a janela (JFrame), configura o painel principal do jogo
 * e inicia a thread do game loop.
 * 
 * @author Mateus
 * @version
 * @since
 */
public class Main {
	
	/**
     * Método principal responsável por iniciar a aplicação.
     * Cria e configura a janela do jogo, adiciona o painel e inicia a thread de execução.
     *
     * @param args Argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {

        JFrame janela = new JFrame();
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setResizable(false); //para que não seja possível modificar o tamanho da janela
        janela.setTitle("BomberBot");
        
        GamePanel gamePanel = new GamePanel();
        janela.add(gamePanel);
        
        janela.pack(); //redimensiona a janela com base nos seus componentes(no caso, o GamePanel)
        
        janela.setLocationRelativeTo(null); //coloca a janela no centro da tela
        janela.setVisible(true); // Torna a janela visível
        
        gamePanel.setupGame(); // Prepara o jogo
        gamePanel.StartGameThread(); // Inicia a thread do jogo (game loop)
        
    }
    
}
