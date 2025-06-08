package entidade;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import principal.GamePanel;
import principal.ManipuladorTeclado;
import principal.UtilityTool;


public class Jogador extends Personagem {
    private ManipuladorTeclado keyH;

    private final int telaX;
    private final int telaY;
    int botsMortos; //SERÁ USADO PARA PASSAR DE CADA FASE

    public Jogador(GamePanel gp, ManipuladorTeclado keyH) {
        super(gp);

        areaSolida = new java.awt.Rectangle(16, 32, 64, 64); //x, y, width, height - RESPECTIVAMENTE
        areaSolidaDefaultX = areaSolida.x;
        areaSolidaDefaultY = areaSolida.y;

        this.keyH = keyH;

        telaX = gp.getScreenWidth() / 2 - (gp.getTileSize() / 2);
        telaY = gp.getScreenHeight() / 2 - (gp.getTileSize() / 2);

        setDefaultValues();
        carregarImagens();
    }

    public void setDefaultValues() {
        setMundoX(gp.getTileSize()); //define as coordenadas x e y que o jogador aparece na tela, a velocidade e a direção padrão
        setMundoY(gp.getTileSize() * 2);
        setVelocidade(3);
        setDirecao("baixo");
        
        //STATUS DO PLAYER
        vidaMax = 3;
        vida = vidaMax;
        botsMortos = 0; 
        
    }

    public void carregarImagens() {
        
        setCima1(setup("player_cima_1"));
        setCima2(setup("player_cima_2"));
        setBaixo1(setup("player_baixo_1"));
        setBaixo2(setup("player_baixo_2"));
        setEsq1(setup("player_esquerda_1"));
        setEsq2(setup("player_esquerda_2"));
        setDir1(setup("player_direita_1"));
        setDir2(setup("player_direita_2"));
        
    }
    
    public BufferedImage setup(String imagemNome) {
    	UtilityTool uTool = new UtilityTool();
    	BufferedImage imagem = null;
    	
    	try {
    		imagem = ImageIO.read(getClass().getResourceAsStream("/jogador/" + imagemNome + ".png"));
    		imagem = uTool.scaleImage(imagem, gp.getTileSize(), gp.getTileSize());
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    	return imagem;
    }

    public void update() {
        if (keyH.getBaixoPress() || keyH.getCimaPress() || keyH.getDirPress() || keyH.getEsqPress()) {
            if (keyH.getCimaPress()) {
                setDirecao("cima");
            } else if (keyH.getBaixoPress()) {
                setDirecao("baixo");
            } else if (keyH.getEsqPress()) {
                setDirecao("esquerda");
            } else if (keyH.getDirPress()) {
                setDirecao("direita");
            }
            
            
            //checa a colisão com o tile
            setColisaoLig(false);
            gp.getcCheca().checaTile(this);
            
            //checa a colisão com o objeto
            int objetoIndex = gp.getcCheca().checaObjeto(this, true);
            pegarObjeto(objetoIndex);  //SERÁ USADA PARA PEGAR BOMBAS, E PARA PASSAR DE FASE
            
            //checa a colisão com o Bot
            int botIndex = gp.getcCheca().checaEntidade(this, gp.monstros);
            interageBot(botIndex);

            if (!getColisaoLig()) {
                switch (getDirecao()) {
                    case "cima":
                        setMundoY(getMundoY() - getVelocidade());
                        break;
                    case "baixo":
                        setMundoY(getMundoY() + getVelocidade());
                        break;
                    case "esquerda":
                        setMundoX(getMundoX() - getVelocidade());
                        break;
                    case "direita":
                        setMundoX(getMundoX() + getVelocidade());
                        break;
                }
            }

            // Controle do sprite para animação
            setSpriteCount(getSpriteCount() + 1);
            if (getSpriteCount() > 15) {
                setSpriteNum(getSpriteNum() == 1 ? 2 : 1);
                setSpriteCount(0);
            }
        }
    }
    
    //pegarObjeto poderia ser deixado apenas para Bombas e poderes, e criaríamos outro método parecido específico para a Porta
    public void pegarObjeto(int i) { //a lógica pode ser usada para os powerups, já que após pegar ele some da tela
    	
    	if(i != 999) { //pode ser qualquer número, desde que não apareça no array de objetos
    		
    		String nomeObjeto = gp.obj[i].nome;
    		
    		switch(nomeObjeto) {
    		case "Porta": //TESTE
//    			if(botsMortos > 10) {
//    				gp.ui.jogoAcabado = true;
//    			}
    			break;
    		}
    	}
    	
    }
    
    public void interageBot(int i) {
    	if (i != 999) {
//    		gp.ui.mostrarMensagem("Você encostou no bot");
//    		this.botsMortos++; //TESTE
    		
    	}
    }

    public void desenhar(Graphics2D g2) {
        BufferedImage imagem = null;

        switch (getDirecao()) {
            case "cima": imagem = (getSpriteNum() == 1) ? getCima1() : getCima2(); break;
            case "baixo": imagem = (getSpriteNum() == 1) ? getBaixo1() : getBaixo2(); break;
            case "esquerda": imagem = (getSpriteNum() == 1) ? getEsq1() : getEsq2(); break;
            case "direita": imagem = (getSpriteNum() == 1) ? getDir1() : getDir2(); break;
        }

        int drawX = telaX;
        int drawY = telaY;

        // Ajuste para bordas do mapa
        if (getMundoX() < telaX) {
            drawX = getMundoX();
        } else if (getMundoX() > gp.getTileSize() * gp.getMaxMundoCol() - (gp.getScreenWidth() - telaX)) {
            drawX = getMundoX() - (gp.getTileSize() * gp.getMaxMundoCol() - gp.getScreenWidth());
        }

        if (getMundoY() < telaY) {
            drawY = getMundoY();
        } else if (getMundoY() > gp.getTileSize() * gp.getMaxMundoLin() - (gp.getScreenHeight() - telaY)) {
            drawY = getMundoY() - (gp.getTileSize() * gp.getMaxMundoLin() - gp.getScreenHeight());
        }

        g2.drawImage(imagem, drawX, drawY, null);
    }

    // Setters e getters

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

    public void setKeyH(ManipuladorTeclado keyH) {
        this.keyH = keyH;
    }

    public GamePanel getGp() {
        return gp;
    }

    public ManipuladorTeclado getKeyH() {
        return keyH;
    }

    public int getTelaX() {
        return telaX;
    }

    public int getTelaY() {
        return telaY;
    }
    
}
