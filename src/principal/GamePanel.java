package principal;

import entidade.Jogador;
import entidade.Personagem;
import fase.Fase1Setter;
import fase.Fase2Setter;
import fase.Fase3Setter;
import fase.FaseSetter;
import objeto.OBJ_Bomba;
import objeto.SuperObjeto;
import tile.TileManager;
import tile_Interativo.BlocoInterativo;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Classe principal do painel do jogo. Gerencia a lógica, renderização, controle de fases,
 * input do jogador e entidades do jogo.
 * Estende {@link JPanel} e implementa {@link Runnable} para permitir a execução do loop de jogo.
 *
 * @author Mateus
 * @version 20.0
 * @since 2025-05-04
 */

public class GamePanel extends JPanel implements Runnable {
    //configurações da tela
	
	 /** Tamanho base do tile (em pixels) antes da escala. */
    private final int tileSizeOriginal = 32;
    
    /** Fator de escala aplicado ao tile. (reescala a tela para que 32X32 não fique tão pequeno)*/
    private final int escala = 3; 
    
    /** Tamanho real do tile após aplicação da escala. */
    private final int tileSize = this.getTileSizeOriginal() * this.getEscala(); //tile de 96X96
    
    /** Número máximo de colunas visíveis na tela. */
    private final int maxScreenCol = 17;
    
    /** Número máximo de linhas visíveis na tela. */
    private final int maxScreenLin = 10;
    
    /** Largura total da tela em pixels. (1632 pixels de largura) */
    private final int screenWidth = this.getTileSize() * this.getMaxScreenCol();
    
    /** Altura total da tela em pixels. (960 pixels de altura) */
    private final int screenHeight = this.getTileSize() * this.getMaxScreenLin(); 
    
    /** Número máximo de colunas do mundo. */
    private final int maxMundoCol = 31; 
    
    /** Número máximo de linhas do mundo. */
    private final int maxMundoLin = 10;
    

    /** Taxa de frames por segundo. (FPS) */
    private int fps = 60;
    
    //SISTEMA
    
    /** Gerenciador de tiles. */
    private TileManager tileM = new TileManager(this);
    
    /** Teclado - manipula as teclas pressionadas. */
    private ManipuladorTeclado keyH = new ManipuladorTeclado(this);
    
    /** Reprodutor de sons e músicas. */
    Sound sound = new Sound();
    
    /** Thread principal do jogo, implementado para ajudar a atualizar a tela durante o decorrer do jogo*/
    private Thread gameThread;
    
    /** Checador de colisões. */
    private ColisaoChecador cCheca = new ColisaoChecador(this);
    
    /** Interface do jogo. */
    public UI ui = new UI(this);
    
    //FASES
    
    /** Setters para cada fase. */
    public Fase1Setter f1Setter = new Fase1Setter(this, this.getTileM().getGMapa());
    public Fase2Setter f2Setter = new Fase2Setter(this, this.getTileM().getGMapa());
    public Fase3Setter f3Setter = new Fase3Setter(this, this.getTileM().getGMapa());
    
    /** Fase atual do jogo. */
    public FaseAtual faseAtual;
    
    //ENTIDADES E OBJETOS
    
    /** Jogador principal do jogo. */
    private Jogador jogador = new Jogador(this, this.getKeyH());
    
    /** Objetos disponíveis no jogo. (não significa que só podem existir 10 objetos no jogo, mas que pode ter 10 objetos ao mesmo tempo) */
    public SuperObjeto[] obj = new SuperObjeto[10]; 
    
    /** Lista de bots. */
    public Personagem[] monstros = new Personagem[10];
    
    /** Blocos interativos no jogo. */
    public BlocoInterativo iTiles[] = new BlocoInterativo[100];

    //GAME STATE
    
    /** Estado atual do jogo. */
    public GameState gameState;
    
    /** Timer para controle do game over. */
	private long tempoGameOver = 0;
	
	/** Timer para controle da transição de fases. */
	private long tempoTelaFase = 0;
	
	/** Flag que controla se uma bomba já está ativa. */
    private boolean bombaAtiva = false;
    
    /** Tempo total acumulado nas fases. */
    private int tempoTotalFase = 0;
    private int tempoTotalJogo = 0;
    
    /**
     * Construtor do GamePanel, inicializa e configura o painel.
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(this.getScreenWidth(), this.getScreenHeight())); //define a dimensão da tela
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //melhora a renderização do jogo
        this.addKeyListener(this.getKeyH()); //o gamePanel vai reconhecer o input das teclas
        this.setFocusable(true);
        setGameState(GameState.TITULO);
    }
    
    
    
    public void setupGame() {
    	
        setGameState(GameState.TITULO);

    }
    
    /**
     * Inicializa a thread do jogo e inicia o loop principal.
     */
    public void StartGameThread() {
        this.setGameThread(new Thread(this));
        this.getGameThread().start(); //chama o método run
    }
    
    /**
     * Loop principal do jogo, responsável por chamar {@code update()} e {@code repaint()}.
     */
    @Override
    public void run() { 
        
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
//                System.out.println("Fps: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    
    /**
     * Atualiza o estado do jogo com base no estado atual (fase, play, game over, etc).
     */
    public void update() {
    	if (gameState == GameState.FASE1) {
    		sound.parar();
    	    if (tempoTelaFase == 0) {
    	        tempoTelaFase = System.currentTimeMillis(); //inicio da contagem
    	    } else {
    	        long tempoAtual = System.currentTimeMillis();
    	        if (tempoAtual - tempoTelaFase >= 5000) { // 5 segundos
    	        	this.tempoTotalJogo = 0;  
    	        	carregaFase();

    	            FaseSetter fase = new Fase1Setter(this, tileM.getGMapa());//recria tudo
    	            fase.setObject(this);
    	            fase.setInimigos(this);
    	            fase.setBlocoInterativo(this, fase.getgMapa());
    	            
    	        	faseAtual = FaseAtual.FASE1;
    	            setGameState(GameState.PLAY);
    	            tempoTelaFase = 0; // reinicia o contador
    	        }
    	    }
    	}
    	
    	if (gameState == GameState.FASE2) {
    		sound.parar();
    	    if (tempoTelaFase == 0) {
    	        tempoTelaFase = System.currentTimeMillis(); //inicio da contagem
    	    } else {
    	        long tempoAtual = System.currentTimeMillis();
    	        if (tempoAtual - tempoTelaFase >= 5000) { // 5 segundos
    	        	carregaFase();

    	            FaseSetter fase = new Fase2Setter(this, tileM.getGMapa());//recria tudo
    	            fase.setObject(this);
    	            fase.setInimigos(this);
    	            fase.setBlocoInterativo(this, fase.getgMapa());
    	            
    	        	faseAtual = FaseAtual.FASE2;
    	            setGameState(GameState.PLAY);
    	            tempoTelaFase = 0; // reinicia o contador
    	        }
    	    }
    	}
    	
    	if (gameState == GameState.FASE3) {
    		sound.parar();
    	    if (tempoTelaFase == 0) {
    	        tempoTelaFase = System.currentTimeMillis(); //inicio da contagem
    	    } else {
    	        long tempoAtual = System.currentTimeMillis();
    	        if (tempoAtual - tempoTelaFase >= 5000) { // 5 segundos
    	        	carregaFase();

    	            FaseSetter fase = new Fase3Setter(this, tileM.getGMapa());//recria tudo
    	            fase.setObject(this);
    	            fase.setInimigos(this);
    	            fase.setBlocoInterativo(this, fase.getgMapa());
    	            
    	        	faseAtual = FaseAtual.FASE3;
    	            setGameState(GameState.PLAY);
    	            tempoTelaFase = 0; // reinicia o contador
    	        }
    	    }
    	}

    	
    	if (gameState == GameState.PLAY) {
    		tempoGameOver = 0; // Garante que não resta tempo residual ao iniciar um novo jogo
    		tempoTelaFase = 0;
//    		tempoTotal = 0;
            this.getJogador().update();
            
            if (keyH.getTeclaBombaPressionada()) {
                int posicaoX = getJogador().getMundoX() + getJogador().getAreaSolida().x;
                int posicaoY = getJogador().getMundoY() + getJogador().getAreaSolida().y;
                colocarBomba(posicaoX, posicaoY); // coloca bomba no centro do jogador
                keyH.setTeclaBombaPressionada(false); // evita que a tecla apertada continue colocando bombas

            }

            
            for (int i = 0; i < monstros.length; i++) {
            	if (monstros[i] != null) {
            		monstros[i].update();
            	}
            }
            for (int i = 0; i < iTiles.length; i++) {
            	if(iTiles[i] != null) {
            		iTiles[i].update();
            	}
            }
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] instanceof OBJ_Bomba) {
                    ((OBJ_Bomba) obj[i]).update();
                }
            }
            
            if(this.getJogador().getVida() <= 0 || ui.getTempoJogo() <= 0) {
            	gameState = GameState.GAME_OVER;
            }
    	}
    	if (gameState == GameState.PAUSE) {
    		//nada
    	}
    	if (gameState == GameState.GAME_OVER) {
    	    if (tempoGameOver == 0) {
    	        tempoGameOver = System.currentTimeMillis(); // Marca o início da contagem
    	    } else {
    	        long tempoAtual = System.currentTimeMillis();
    	        if (tempoAtual - tempoGameOver >= 10000) { // 10 segundos
    	            restart();
    	            gameState = GameState.TITULO;
    	            tempoGameOver = 0; // Reinicia o contador
    	        }
    	    }
    	}

    	ui.atualizarTempoJogo(1.0 / 60.0); // ou passe o delta se estiver calculando dinamicamente

    	
    }

    /**
     * Desenha todos os elementos visuais do jogo no painel.
     * @param g objeto Graphics usado para renderização.
     */
    public void paintComponent(Graphics g) { //está sobrescrevendo um método que já existe em java
        
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g; //tem mais funções que o Graphics
        
        //DEBUG
        long drawStart = 0;
        if(keyH.checkDrawTime == true) {
            drawStart = System.nanoTime();     	
        }  
        
        //TITLE State
        if (gameState == GameState.TITULO) {
        	ui.desenhar(g2);
        } else if (gameState == GameState.RANKING) { 
        	ui.desenhar(g2);
        } else if (gameState == GameState.CADASTRAR_RANKING) { 
        	ui.desenhar(g2);
        } else { 	
  
            //TILE
            this.getTileM().desenhar(g2); //vem antes para que o cenário seja desenhado antes do personagem
            
            //OBJETOS
            for (int i = 0; i < obj.length; i++) {
            	if (obj[i] != null) {
            		obj[i].desenhar(g2, this);
            	}
            }
            
            for(int i = 0; i < iTiles.length; i++) {
            	if(iTiles[i] != null) {
            		iTiles[i].desenhar(g2);
            	}
            }
            
            //BOTS
            for(int i = 0; i < monstros.length; i++) {
                if (monstros[i] != null) {
                    monstros[i].desenhar(g2);
                }
            }
            
            //JOGADOR
            this.getJogador().desenhar(g2);
            
            //UI
            ui.desenhar(g2);
            
            
        }
        
        
        //DEBUG
        if (keyH.checkDrawTime == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: " + passed);	
        }
        
        g2.dispose(); //libera memória do que não está sendo mais usado
    }
    
    /**
     * Coloca uma bomba no local do jogador, se nenhuma já estiver ativa.
     * @param x coordenada X
     * @param y coordenada Y
     */
    public void colocarBomba(int x, int y) {
        if (bombaAtiva) return; // já existe uma bomba no mapa

        int posicaoBombaX = (x) / getTileSize() * getTileSize();
        int posicaoBombaY = (y) / getTileSize() * getTileSize();

        for (int i = 0; i < obj.length; i++) {
            if (obj[i] == null) {
                obj[i] = new OBJ_Bomba(this, null);
                obj[i].mundoX = posicaoBombaX;
                obj[i].mundoY = posicaoBombaY;
                bombaAtiva = true;
                break;
            }
        }
    }
	
    /**
     * Reinicia o jogo após o fim da partida.
     */
    public void restart() {
        // 1. Parar música do jogo
        sound.parar(); //para o som que estava tocando
        
        ui.setTempoJogo(400);
        this.setTempoTotalJogo(0);

        // 2. Zerar estados e arrays
        jogador = new Jogador(this, this.getKeyH());
        obj = new SuperObjeto[10];
        monstros = new Personagem[6];
        iTiles = new BlocoInterativo[100];

        // 3. Resetar mapa (recarregar tile manager ou criar novo)
        tileM = new TileManager(this);

        // 4. Resetar dados do jogador
        jogador.setDefaultPositions();
        jogador.resetarVida();
        this.setBombaAtiva(false);

        // 5. Resetar tempo do Game Over
        tempoGameOver = 0;

        // 6. Recriar elementos como no início
        f1Setter = new Fase1Setter(this, tileM.getGMapa());//recria tudo
        f1Setter.setObject(this);
        f1Setter.setInimigos(this);
        f1Setter.setBlocoInterativo(this, f1Setter.getgMapa());

        // 7. Tocar música do título
        tocarMusica(GameState.TITULO.getMusicaIndex()); //toca a música de título
    }
    
    /**
     * Carrega uma nova fase, redefinindo os dados do jogador e do mapa.
     */
    public void carregaFase() {
        // 1. Parar música do jogo
        sound.parar(); //para o som que estava tocando

        ui.setTempoJogo(400);
        
        // 2. Zerar estados e arrays
        jogador = new Jogador(this, this.getKeyH());
        obj = new SuperObjeto[10];
        monstros = new Personagem[6];
        iTiles = new BlocoInterativo[100];

        // 3. Resetar mapa (recarregar tile manager ou criar novo)
        tileM = new TileManager(this);

        // 4. Resetar dados do jogador
        jogador.setDefaultPositions();
        jogador.resetarVida();
        this.setBombaAtiva(false);

        // 5. Resetar tempo do Game Over
        tempoGameOver = 0;

    }

    /**
     * Coloca uma bomba de bot no mapa.
     * @param x coordenada X
     * @param y coordenada Y
     * @param alcance alcance da bomba
     * @param dono referência ao bot que plantou a bomba (permite que o bot plante mais de uma bomba)
     */
    public void colocarBombaBot(int x, int y, int alcance, entidade.BotPersonagem dono) {
        int posicaoBombaX = (x) / getTileSize() * getTileSize();
        int posicaoBombaY = (y) / getTileSize() * getTileSize();

        for (int i = 0; i < obj.length; i++) {
            if (obj[i] == null) {
                obj[i] = new objeto.OBJ_Bomba(this, dono); // Passa o bot dono
                obj[i].mundoX = posicaoBombaX;
                obj[i].mundoY = posicaoBombaY;
                break;
            }
        }
    }

    /**
     * Retorna se há uma bomba ativa no momento.
     * @return true se houver bomba ativa
     */
    public boolean isBombaAtiva() {
        return bombaAtiva;
    }
    
    /**
     * Define se uma bomba está ativa no jogo.
     * @param ativa true se a bomba estiver ativa
     */
    public void setBombaAtiva(boolean ativa) {
        this.bombaAtiva = ativa;
    }
    
    public void tocarMusica(int i) {
    	sound.setFile(i);
    	sound.play();
    	sound.loop(); //a msuica se repete após acabar
    }
    
    public void pausarMusica() {
    	sound.pausar();
    }
    
    public void retomarMusica() {
    	sound.retomar();
    }
    
    public void tocarSom(int i) {
    	sound.setFile(i);
    	sound.play();
    }
    
    /**
     * Define o estado atual do jogo.
     * @param novoEstado novo estado a ser aplicado
     */
    public void setGameState(GameState novoEstado) {
        //se estiver indo do play para o pause, pausa
        if (this.gameState == GameState.PLAY && novoEstado == GameState.PAUSE) {
            pausarMusica();
        }
        //Se voltar do Ppause para o play retoma a música de onde ela parou
        else if (this.gameState == GameState.PAUSE && novoEstado == GameState.PLAY) {
            retomarMusica();
        }
        //se mudar para um outro estado com outra musica
        else if (novoEstado.getMusicaIndex() != -1 && novoEstado != this.gameState) {
            sound.parar();
            tocarMusica(novoEstado.getMusicaIndex());
        }

        this.gameState = novoEstado;
    }
    
    /**
     * Avança para a próxima fase e acumula o tempo total de jogo.
     */
    public void passarFase() {
    	 if(this.faseAtual == FaseAtual.FASE1) {
    		 int tempoAtual = (int) ui.getTempoJogo();
    		 this.setTempoTotalJogo(this.getTempoTotalJogo() + tempoAtual);
    		 System.out.println("Tempo Total = " + this.getTempoTotalJogo());
    		 this.gameState = GameState.FASE2;
    	 }
    	 if(this.faseAtual == FaseAtual.FASE2) {
    		 int tempoAtual = (int) ui.getTempoJogo();
    		 this.setTempoTotalJogo(this.getTempoTotalJogo() + tempoAtual);
    		 System.out.println("Tempo Total = " + this.getTempoTotalJogo());
    		 this.gameState = GameState.FASE3;
    	 }
    	 if(this.faseAtual == FaseAtual.FASE3) {
    		 int tempoAtual = (int) ui.getTempoJogo();
    		 this.setTempoTotalJogo(this.getTempoTotalJogo() + tempoAtual);
    		 System.out.println("Tempo Total = " + this.getTempoTotalJogo());
    		 this.gameState = GameState.CADASTRAR_RANKING;
    		 ui.aguardandoNome = true;
    		 ui.nomeDigitado = "";
    	 }
    }



  
    //GETTERS E SETTERS
    
    /**
     * Define os frames por segundo (FPS).
     */
	public void setFps(int fps) {
		this.fps = fps;
	}
	
	/**
     * Define o gerenciador de tiles.
     */
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
	
	/**
     * Define o tempo total acumulado nas fases.
     */
	public void setTempoTotalJogo(int tempoTotalJogo) {
		this.tempoTotalJogo = tempoTotalJogo;
	}
	
	/**
     * Retorna o FPS atual.
     */
	public int getFps() {
		return fps;
	}
	
	/**
     * Retorna o gerenciador de tiles.
     */
	public TileManager getTileM() {
		return tileM;
	}
	
	/**
     * Retorna o manipulador de teclado.
     */
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

	/**
     * Retorna o tempo total acumulado nas fases.
     */
	public int getTempoTotalJogo() {
		return tempoTotalJogo;
	}


	

}
