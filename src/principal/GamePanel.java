package principal;

/**
 *
 * @author Mateu
 */

import entidade.Jogador;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable{
    //configurações da tela
    final int tileSizeOriginal = 32; //tamanho padrão de cada "bloco" da tela
    final int escala = 3; //reescala a tela para que 32X32 não fique tão pequeno
    
    public final int tileSize = tileSizeOriginal * escala; //tile de 64X64
    public final int maxScreenCol = 17;
    public final int maxScreenLin = 10;
    public final int screenWidth = tileSize * maxScreenCol; //1280 pixels de largura
    public final int screenHeight = tileSize * maxScreenLin; //960 pixels de altura
    
    //FPS
    int fps = 60;
    
    TileManager tileM = new TileManager(this);
    
    ManipuladorTeclado keyH = new ManipuladorTeclado();
    Thread gameThread; //implementado para ajudar a atualizar a tela durante o decorrer do jogo
    Jogador jogador = new Jogador(this, keyH); //cria uma instância jogador dentro da Tela do jogo
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); //define a dimensão da tela
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //melhora a renderização do jogo
        this.addKeyListener(keyH); //o gamePanel vai reconhecer o input das teclas
        this.setFocusable(true);
    }

    public void StartGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); //chama o método run
    }

    @Override
    public void run() { //game loop
        
        double intervaloDesenho = 1000000000 / fps;
        double delta = 0;
        long tempoAntes = System.nanoTime();
        long tempoAtual;
        long timer = 0;
        int drawCount = 0;
        
        while (gameThread != null) {
            
            tempoAtual = System.nanoTime();
            
            delta += (tempoAtual - tempoAntes) / intervaloDesenho;
            timer += (tempoAtual - tempoAntes);
            tempoAntes = tempoAtual;
            
            if (delta >= 1) {
                // 1 - UPDATE: atualizar as informações (como posição do jogador)
                update();
                // 2 - DESENHAR: desenhar a tela com as informações atualizadas
                repaint(); // chama o método paintComponent
                delta--;
                drawCount++;
            }
            
            if (timer >= 1000000000) {
                System.out.println("Fps: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    
    public void update() {
        jogador.update();
    }
    
    public void paintComponent(Graphics g) { //está sobrescrevendo um método que já existe em java
        
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g; //tem mais funções que o Graphics
        
        tileM.desenhar(g2); //vem antes para que o cenário seja desenhado antes do personagem
        jogador.desenhar(g2);
        
        g2.dispose(); //libera memória do que não está sendo mais usado
    }
}
