package principal;

/**
 *
 * @author Mateus
 */

import entidade.Jogador;
import entidade.Personagem;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {
    //configurações da tela
    private final int tileSizeOriginal = 32; //tamanho padrão de cada "bloco" da tela
    private final int escala = 3; //reescala a tela para que 32X32 não fique tão pequeno
    
    private final int tileSize = this.getTileSizeOriginal() * this.getEscala(); //tile de 96X96
    
    private final int maxScreenCol = 17;
    private final int maxScreenLin = 10;
    private final int screenWidth = this.getTileSize() * this.getMaxScreenCol(); //1632 pixels de largura
    private final int screenHeight = this.getTileSize() * this.getMaxScreenLin(); //960 pixels de altura
    
    private final int maxMundoCol = 31; // exemplo: mapa de 50 colunas
    private final int maxMundoLin = 10;
    private final int mundoWidth = this.getTileSize() * this.getMaxMundoCol(); //2976 pixels de largura
    private final int mundoHeight = this.getTileSize() * this.getMaxMundoLin(); //960 pixels de altura
    
    //FPS
    private int fps = 60;
    
    private TileManager tileM = new TileManager(this);
    
    private ManipuladorTeclado keyH = new ManipuladorTeclado();
    private Thread gameThread; //implementado para ajudar a atualizar a tela durante o decorrer do jogo
    private ColisaoChecador cCheca = new ColisaoChecador(this);
    public AssetSetter aSetter = new AssetSetter(this);
    private Jogador jogador = new Jogador(this, this.getKeyH()); //cria uma instância jogador dentro da Tela do jogo
    public Personagem[] monstros = new Personagem[10];
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(this.getScreenWidth(), this.getScreenHeight())); //define a dimensão da tela
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //melhora a renderização do jogo
        this.addKeyListener(this.getKeyH()); //o gamePanel vai reconhecer o input das teclas
        this.setFocusable(true);
    }
    
    public void setupGame() {
        aSetter.setObject();
        aSetter.setBot();
    }

    public void StartGameThread() {
        this.setGameThread(new Thread(this));
        this.getGameThread().start(); //chama o método run
    }

    @Override
    public void run() { //game loop
        
        double intervaloDesenho = 1000000000 / this.getFps();
        double delta = 0;
        long tempoAntes = System.nanoTime();
        long tempoAtual;
        long timer = 0;
        int drawCount = 0;
        
        while (this.getGameThread() != null) {
            
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
        this.getJogador().update();
    }
    
    public void paintComponent(Graphics g) { //está sobrescrevendo um método que já existe em java
        
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g; //tem mais funções que o Graphics
        
        this.getTileM().desenhar(g2); //vem antes para que o cenário seja desenhado antes do personagem
        
        for(int i = 0; i < monstros.length; i++) {
            if (monstros[i] != null) {
                monstros[i].desenhar(g2);
            }
        }
        
        this.getJogador().desenhar(g2);
        
        g2.dispose(); //libera memória do que não está sendo mais usado
    }

    //setters
	public void setFps(int fps) {
		this.fps = fps;
	}

	public void setTileM(TileManager tileM) {
		this.tileM = tileM;
	}

	public void setKeyH(ManipuladorTeclado keyH) {
		this.keyH = keyH;
	}

	public void setGameThread(Thread gameThread) {
		this.gameThread = gameThread;
	}

	public void setcCheca(ColisaoChecador cCheca) {
		this.cCheca = cCheca;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}

	//getters
	public int getFps() {
		return fps;
	}
	
	public TileManager getTileM() {
		return tileM;
	}
	
	public ManipuladorTeclado getKeyH() {
		return keyH;
	}
	
	public Thread getGameThread() {
		return gameThread;
	}
	
	public ColisaoChecador getcCheca() {
		return cCheca;
	}
	
	public Jogador getJogador() {
		return jogador;
	}
	
	public int getTileSizeOriginal() {
		return tileSizeOriginal;
	}

	public int getEscala() {
		return escala;
	}

	public int getTileSize() {
		return tileSize;
	}

	public int getMaxScreenCol() {
		return maxScreenCol;
	}

	public int getMaxScreenLin() {
		return maxScreenLin;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public int getMaxMundoCol() {
		return maxMundoCol;
	}

	public int getMaxMundoLin() {
		return maxMundoLin;
	}

	public int getMundoWidth() {
		return mundoWidth;
	}

	public int getMundoHeight() {
		return mundoHeight;
	}
    
}
