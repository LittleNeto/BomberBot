package principal;

import entidade.Personagem;

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

	//setters
	public void setGp(GamePanel gp) {
		this.gp = gp;
	}
	
	//getters
	public GamePanel getGp() {
		return gp;
	}
 
}
