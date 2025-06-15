package entidade;

import java.awt.AlphaComposite;

/**
*
* @author Mateus
* @version 14.0
* @since 16 maio 2025
*/

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import principal.FaseAtual;
import principal.GamePanel;
import principal.GameState;
import principal.ManipuladorTeclado;
import principal.UtilityTool;


public class Jogador extends Personagem {
    private ManipuladorTeclado keyH;

    private final int telaX;
    private final int telaY;
    public boolean invencivel = false;
    public int invencivelCont = 0;
    
    private int botsMortos = 0; //SERÁ USADO PARA PASSAR DE CADA FASE

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

    /**
     * @param
     */
    public void setDefaultValues() {
        setMundoX(gp.getTileSize()); //define as coordenadas x e y que o jogador aparece na tela, a velocidade e a direção padrão
        setMundoY(gp.getTileSize() * 2);
        setVelocidade(3);
        setDirecao("baixo");
        
        //STATUS DO PLAYER
        vidaMax = 6;
        vida = vidaMax;
        botsMortos = 0; 
        
    }
    
    public void setDefaultPositions() {
        setMundoX(gp.getTileSize()); //define as coordenadas x e y que o jogador aparece na tela, a velocidade e a direção padrão
        setMundoY(gp.getTileSize() * 2);
        setVelocidade(3);
        setDirecao("baixo");
    }
    
    public void resetarVida() {
    	vida = vidaMax;
    	invencivel = false;
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
            gp.getcCheca().checaBlocoInterativo(this, gp.iTiles);
            
            //checa a colisão com o objeto
            int objetoIndex = gp.getcCheca().checaObjeto(this, true);
            pegarObjeto(objetoIndex);  //SERÁ USADA PARA PEGAR BOMBAS, E PARA PASSAR DE FASE
            
            //checa a colisão com o Bot
            int botIndex = gp.getcCheca().checaEntidade(this, gp.monstros);
            interageBot(botIndex);
            
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
        
        if (invencivel == true) {
        	invencivelCont++;
        	if (invencivelCont > 120) { //como atualiza 60 vezes por segundo, seriam 2 segundos de invencibilidade
        		invencivel = false;
        		invencivelCont = 0;
        	}
        }
        
        verificaDanoPorExplosao();
        verificaDanoBotPorExplosao();
        verificaExplosaoBlocosInterativos();

    }
    
    //pegarObjeto poderia ser deixado apenas para Bombas e poderes, e criaríamos outro método parecido específico para a Porta
    public void pegarObjeto(int i) { //a lógica pode ser usada para os powerups, já que após pegar ele some da tela
    	
    	if(i != 999) { //pode ser qualquer número, desde que não apareça no array de objetos
    		
    		String nomeObjeto = gp.obj[i].nome;
    		 
    		switch(nomeObjeto) {
    		case "Porta": //TESTE
    			if (gp.faseAtual == FaseAtual.FASE1) {
    				if (botsMortos >= gp.f1Setter.getQTD_BOTS()) {
    					gp.passarFase();
    				}
    			}
    			if (gp.faseAtual == FaseAtual.FASE2) {
    				if (botsMortos >= gp.f2Setter.getQTD_BOTS()) {
    					gp.passarFase();
    				}
    			}
    			if (gp.faseAtual == FaseAtual.FASE3) {
    				if (botsMortos >= gp.f2Setter.getQTD_BOTS()) {
    					gp.passarFase();
    				}
    			}

    			break;
    		}
    	}
    	
    }
    
    public void interageBot(int i) {
    	if (i != 999 && gp.monstros[i]!= null) {
    		if (gp.monstros[i].tipo == 1) {
        		if (invencivel == false) {
            		this.setVida(this.vida - 1);
            		invencivel = true;
        		}		
    			
    		}

    	}
    }
    
    public void explodirBot(int i) {
        if (i != 999 && gp.monstros[i] != null) {
            gp.monstros[i].vida -= 1;
            if (gp.monstros[i].vida <= 0) {
                gp.monstros[i] = null;
                this.botsMortos++;
                System.out.println("Qtd de bots mortos: " + this.botsMortos);
            }
        }
    }
    
    public void verificaDanoPorExplosao() {
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] instanceof objeto.OBJ_Bomba bomba) {
                if (!bomba.isExplosaoAtiva()) continue;

                Rectangle jogadorArea = new Rectangle(
                    getMundoX() + areaSolida.x,
                    getMundoY() + areaSolida.y,
                    areaSolida.width,
                    areaSolida.height
                );

                for (Rectangle zona : bomba.getZonasExplosao()) {
                    if (zona != null && jogadorArea.intersects(zona)) {
                        if (!invencivel) {
                            vida--;
                            invencivel = true;
                        }
                        return;
                    }
                }
            }
        }
    }

    public void verificaDanoBotPorExplosao() {
        for (int b = 0; b < gp.monstros.length; b++) {
            if (gp.monstros[b] == null) continue;

            // Retângulo de colisão do bot
            Rectangle botArea = new Rectangle(
                gp.monstros[b].getMundoX() + gp.monstros[b].getAreaSolida().x,
                gp.monstros[b].getMundoY() + gp.monstros[b].getAreaSolida().y,
                gp.monstros[b].getAreaSolida().width,
                gp.monstros[b].getAreaSolida().height
            );

            // Para cada bomba no mapa
            for (int i = 0; i < gp.obj.length; i++) {
                if (gp.obj[i] instanceof objeto.OBJ_Bomba bomba) {
                    if (!bomba.isExplosaoAtiva()) continue;

                    // Verifica se o bot está na zona da explosão
                    for (Rectangle zona : bomba.getZonasExplosao()) {
                        if (zona != null && botArea.intersects(zona)) {
                            if (gp.monstros[b] != null) { // evita explodir bot já removido
                                explodirBot(b);
                            }
                            break; // interrompe verificação após primeira explosão
                        }
                    }

                }
            }
        }
    }

    public void verificaExplosaoBlocosInterativos() {
        for (int b = 0; b < gp.iTiles.length; b++) {
            if (gp.iTiles[b] == null) continue;

            // Retângulo de colisão do bloco interativo
            Rectangle blocoArea = new Rectangle(
                gp.iTiles[b].getMundoX() + gp.iTiles[b].getAreaSolida().x,
                gp.iTiles[b].getMundoY() + gp.iTiles[b].getAreaSolida().y,
                gp.iTiles[b].getAreaSolida().width,
                gp.iTiles[b].getAreaSolida().height
            );

            // Para cada bomba no mapa
            for (int i = 0; i < gp.obj.length; i++) {
                if (gp.obj[i] instanceof objeto.OBJ_Bomba bomba) {
                    if (!bomba.isExplosaoAtiva()) continue;

                    // Verifica se o bloco está na zona da explosão
                    for (Rectangle zona : bomba.getZonasExplosao()) {
                        if (zona != null && blocoArea.intersects(zona)) {
                            gp.iTiles[b] = null; // Remove o bloco
                            break; // Sai do loop das zonas para esse bloco
                        }
                    }
                }
            }
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
        
        //PODERÁ SER REMOVIDO DEPOIS
        //aplica o efeito de transparência logo após o personagem sofrer dano
        if (invencivel == true) {
        	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(imagem, drawX, drawY, null);
        
        //para resetar o efeito
    	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }

    // Setters e getters

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

    public void setKeyH(ManipuladorTeclado keyH) {
        this.keyH = keyH;
    }
    
    public void setBotsMortos(int botsMortos) {
        this.botsMortos = botsMortos;
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

    public int getBotsMortos() {
    	return this.botsMortos;
    }

}
