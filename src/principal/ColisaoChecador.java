package principal;

import entidade.Personagem;
import tile_Interativo.BlocoInterativo;

public class ColisaoChecador {
	
	private GamePanel gp;
	
	public ColisaoChecador(GamePanel gp) {
		this.gp = gp;
	}
	
	
	public void checaTile(Personagem personagem) {

	    int personagemEsqMundoX = personagem.getMundoX() + personagem.getAreaSolida().x;
	    int personagemDirMundoX = personagem.getMundoX() + personagem.getAreaSolida().x + personagem.getAreaSolida().width;
	    int personagemTopoMundoY = personagem.getMundoY() + personagem.getAreaSolida().y;
	    int personagemFundoMundoY = personagem.getMundoY() + personagem.getAreaSolida().y + personagem.getAreaSolida().height;

	    int personagemEsqCol = personagemEsqMundoX / this.getGp().getTileSize();
	    int personagemDirCol = personagemDirMundoX / this.getGp().getTileSize();
	    int personagemTopoLin = personagemTopoMundoY / this.getGp().getTileSize();
	    int personagemFundoLin = personagemFundoMundoY / this.getGp().getTileSize();

	    int tileNum1, tileNum2;
	    
		//tenta "prever" a coordenada que o personagem irá alcançar para restringir através da colisão
	    switch(personagem.getDirecao()) {
	        case "cima": //tenta ver se o próximo bloco que o jogador irá é colidível, para desabilitar o movimento do personagem caso sim
	            personagemTopoLin = (personagemTopoMundoY - personagem.getVelocidade()) / this.getGp().getTileSize();
	            tileNum1 = this.getGp().getTileM().getMapTileNum()[personagemTopoLin][personagemEsqCol];
	            tileNum2 = this.getGp().getTileM().getMapTileNum()[personagemTopoLin][personagemDirCol];
	            break;
	        case "baixo":
	            personagemFundoLin = (personagemFundoMundoY + personagem.getVelocidade()) / this.getGp().getTileSize();
	            tileNum1 = this.getGp().getTileM().getMapTileNum()[personagemFundoLin][personagemEsqCol];
	            tileNum2 = this.getGp().getTileM().getMapTileNum()[personagemFundoLin][personagemDirCol];
	            break;
	        case "esquerda":
	            personagemEsqCol = (personagemEsqMundoX - personagem.getVelocidade()) / this.getGp().getTileSize();
	            tileNum1 = this.getGp().getTileM().getMapTileNum()[personagemTopoLin][personagemEsqCol];
	            tileNum2 = this.getGp().getTileM().getMapTileNum()[personagemFundoLin][personagemEsqCol];
	            break;
	        case "direita":
	            personagemDirCol = (personagemDirMundoX + personagem.getVelocidade()) / this.getGp().getTileSize();
	            tileNum1 = this.getGp().getTileM().getMapTileNum()[personagemTopoLin][personagemDirCol];
	            tileNum2 = this.getGp().getTileM().getMapTileNum()[personagemFundoLin][personagemDirCol];
	            break;
	        default:
	            return;
	    }

	    if (this.getGp().getTileM().getTile()[tileNum1].getColisao() || this.getGp().getTileM().getTile()[tileNum2].getColisao()) {
	        personagem.setColisaoLig(true);
	    }
	}
	
	public int checaObjeto(Personagem personagem, boolean jogador) {
	    int index = 999;

	    for (int i = 0; i < gp.obj.length; i++) {

	        if (gp.obj[i] != null) {
	            // Salva as posições originais das áreas sólidas
	            int personagemAreaX = personagem.getAreaSolida().x;
	            int personagemAreaY = personagem.getAreaSolida().y;

	            int objetoAreaX = gp.obj[i].areaSolida.x;
	            int objetoAreaY = gp.obj[i].areaSolida.y;

	            // Ajusta para a posição no mundo
	            personagem.getAreaSolida().x = personagem.getMundoX() + personagemAreaX;
	            personagem.getAreaSolida().y = personagem.getMundoY() + personagemAreaY;

	            gp.obj[i].areaSolida.x = gp.obj[i].mundoX + objetoAreaX;
	            gp.obj[i].areaSolida.y = gp.obj[i].mundoY + objetoAreaY;

	            // Simula o movimento para ver se colide
	            switch (personagem.getDirecao()) {
	                case "cima":
	                    personagem.getAreaSolida().y -= personagem.getVelocidade();
	                    if(personagem.getAreaSolida().intersects(gp.obj[i].areaSolida)) {
	                    	if(gp.obj[i].colisao == true) {
	                    		personagem.setColisaoLig(true);
	                    	}
	                    	if(jogador == true) {
	                    		index = i;
	                    	}
	                    }
	                    break;
	                case "baixo":
	                    personagem.getAreaSolida().y += personagem.getVelocidade();
	                    if(personagem.getAreaSolida().intersects(gp.obj[i].areaSolida)) {
	                    	if(gp.obj[i].colisao == true) {
	                    		personagem.setColisaoLig(true);
	                    	}
	                    	if(jogador == true) {
	                    		index = i;
	                    	}
	                    }
	                    break;
	                case "esquerda":
	                    personagem.getAreaSolida().x -= personagem.getVelocidade();
	                    if(personagem.getAreaSolida().intersects(gp.obj[i].areaSolida)) {
	                    	if(gp.obj[i].colisao == true) {
	                    		personagem.setColisaoLig(true);
	                    	}
	                    	if(jogador == true) {
	                    		index = i;
	                    	}
	                    }
	                    break;
	                case "direita":
	                    personagem.getAreaSolida().x += personagem.getVelocidade();
	                    if(personagem.getAreaSolida().intersects(gp.obj[i].areaSolida)) {
	                    	if(gp.obj[i].colisao == true) {
	                    		personagem.setColisaoLig(true);
	                    	}
	                    	if(jogador == true) {
	                    		index = i;
	                    	}
	                    }
	                    break;
	            }

	            if (personagem.getAreaSolida().intersects(gp.obj[i].areaSolida) ) {
//	                System.out.println("Colisão com objeto na direção: " + personagem.getDirecao());
	                index = i; // opcional — caso queira saber qual objeto colidiu
	            }
	            if (jogador == true && personagem.getAreaSolida().intersects(gp.obj[i].areaSolida) ) {
//	                System.out.println("Colisão com objeto na direção: " + personagem.getDirecao());
	                index = i; // opcional — caso queira saber qual objeto colidiu
	            }	            
	            

	            // Restaura as posições originais
	            personagem.getAreaSolida().x = personagemAreaX;
	            personagem.getAreaSolida().y = personagemAreaY;

	            gp.obj[i].areaSolida.x = objetoAreaX;
	            gp.obj[i].areaSolida.y = objetoAreaY;
	        }
	    }

	    return index;
	}

	
	public int checaEntidade(Personagem personagem, Personagem[] target) {
		
		int index = 999;
		
		for (int i = 0; i < target.length; i++) {
			
			if (target[i] != null) {
				
				personagem.getAreaSolida().x = personagem.getMundoX() + personagem.getAreaSolida().x;
				personagem.getAreaSolida().y = personagem.getMundoY() + personagem.getAreaSolida().y;
				
				target[i].getAreaSolida().x = target[i].getMundoX() + target[i].getAreaSolida().x;
				target[i].getAreaSolida().y = target[i].getMundoY() + target[i].getAreaSolida().y;
				
				switch(personagem.getDirecao()) {
				case "cima":
					personagem.getAreaSolida().y -= personagem.getVelocidade();
					if(personagem.getAreaSolida().intersects(target[i].getAreaSolida())) {
//						personagem.setColisaoLig(true);
						index = i;
					}
					break;
				case "baixo":
					personagem.getAreaSolida().y += personagem.getVelocidade();
					if(personagem.getAreaSolida().intersects(target[i].getAreaSolida())) {
//						personagem.setColisaoLig(true);
						index = i;
					}
					break;
				case "esquerda":
					personagem.getAreaSolida().x -= personagem.getVelocidade();
					if(personagem.getAreaSolida().intersects(target[i].getAreaSolida())) {
//						personagem.setColisaoLig(true);
						index = i;
					}
					break;
				case "direita":
					personagem.getAreaSolida().x += personagem.getVelocidade();
					if(personagem.getAreaSolida().intersects(target[i].getAreaSolida())) {
//						personagem.setColisaoLig(true);
						index = i;
					}
					break;
				}
				personagem.getAreaSolida().x = personagem.areaSolidaDefaultX;
				personagem.getAreaSolida().y = personagem.areaSolidaDefaultY;				
				target[i].getAreaSolida().x = target[i].areaSolidaDefaultX;
				target[i].getAreaSolida().y = target[i].areaSolidaDefaultY;
			}

		}
		return index;
	}
	
	public boolean checaJogador(Personagem personagem) {
		
		boolean interagiuJogador = false;
		
		personagem.getAreaSolida().x = personagem.getMundoX() + personagem.getAreaSolida().x;
		personagem.getAreaSolida().y = personagem.getMundoY() + personagem.getAreaSolida().y;
		
		gp.getJogador().getAreaSolida().x = gp.getJogador().getMundoX() + gp.getJogador().getAreaSolida().x;
		gp.getJogador().getAreaSolida().y = gp.getJogador().getMundoY() + gp.getJogador().getAreaSolida().y;
		
		switch(personagem.getDirecao()) {
		case "cima":
			personagem.getAreaSolida().y -= personagem.getVelocidade();
			break;
		case "baixo":
			personagem.getAreaSolida().y += personagem.getVelocidade();
			break;
		case "esquerda":
			personagem.getAreaSolida().x -= personagem.getVelocidade();
			break;
		case "direita":
			personagem.getAreaSolida().x += personagem.getVelocidade();
			break;
		}
		
		if(personagem.getAreaSolida().intersects(gp.getJogador().getAreaSolida())) {
			if(personagem.getAreaSolida().intersects(gp.getJogador().getAreaSolida())) {
				interagiuJogador = true;
				System.out.println("Encostou no jogador");
			}
		}
		personagem.getAreaSolida().x = personagem.areaSolidaDefaultX;
		personagem.getAreaSolida().y = personagem.areaSolidaDefaultY;				
		gp.getJogador().getAreaSolida().x = gp.getJogador().areaSolidaDefaultX;
		gp.getJogador().getAreaSolida().y = gp.getJogador().areaSolidaDefaultY;
		
		return interagiuJogador;
	}
	

	//setters
	public void setGp(GamePanel gp) {
		this.gp = gp;
	}
	
	//getters
	public GamePanel getGp() {
		return gp;
	}


	public int checaBlocoInterativo(Personagem personagem, BlocoInterativo[] target) {
		
		int index = 999;
		
		for (int i = 0; i < target.length; i++) {
			
			if (target[i] != null) {
				
				personagem.getAreaSolida().x = personagem.getMundoX() + personagem.getAreaSolida().x;
				personagem.getAreaSolida().y = personagem.getMundoY() + personagem.getAreaSolida().y;
				
				target[i].getAreaSolida().x = target[i].getMundoX() + target[i].getAreaSolida().x;
				target[i].getAreaSolida().y = target[i].getMundoY() + target[i].getAreaSolida().y;
				
				switch(personagem.getDirecao()) {
				case "cima":
					personagem.getAreaSolida().y -= personagem.getVelocidade();
					if(personagem.getAreaSolida().intersects(target[i].getAreaSolida())) {
						personagem.setColisaoLig(true);
						index = i;
					}
					break;
				case "baixo":
					personagem.getAreaSolida().y += personagem.getVelocidade();
					if(personagem.getAreaSolida().intersects(target[i].getAreaSolida())) {
						personagem.setColisaoLig(true);
						index = i;
					}
					break;
				case "esquerda":
					personagem.getAreaSolida().x -= personagem.getVelocidade();
					if(personagem.getAreaSolida().intersects(target[i].getAreaSolida())) {
						personagem.setColisaoLig(true);
						index = i;
					}
					break;
				case "direita":
					personagem.getAreaSolida().x += personagem.getVelocidade();
					if(personagem.getAreaSolida().intersects(target[i].getAreaSolida())) {
						personagem.setColisaoLig(true);
						index = i;
					}
					break;
				}
				personagem.getAreaSolida().x = personagem.areaSolidaDefaultX;
				personagem.getAreaSolida().y = personagem.areaSolidaDefaultY;				
				target[i].getAreaSolida().x = target[i].getAreaSolidaDefaultX();
				target[i].getAreaSolida().y = target[i].getAreaSolidaDefaultY();
			}

		}
		return index;
	}
 
}
