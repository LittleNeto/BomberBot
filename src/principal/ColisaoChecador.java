package principal;

import entidade.Personagem;

public class ColisaoChecador {
	
	GamePanel gp;
	
	public ColisaoChecador(GamePanel gp) {
		this.gp = gp;
	}
	
	
	public void checaTile(Personagem personagem) {

	    int personagemEsqMundoX = personagem.mundoX + personagem.areaSolida.x;
	    int personagemDirMundoX = personagem.mundoX + personagem.areaSolida.x + personagem.areaSolida.width;
	    int personagemTopoMundoY = personagem.y + personagem.areaSolida.y;
	    int personagemFundoMundoY = personagem.y + personagem.areaSolida.y + personagem.areaSolida.height;

	    int personagemEsqCol = personagemEsqMundoX / gp.tileSize;
	    int personagemDirCol = personagemDirMundoX / gp.tileSize;
	    int personagemTopoLin = personagemTopoMundoY / gp.tileSize;
	    int personagemFundoLin = personagemFundoMundoY / gp.tileSize;

	    int tileNum1, tileNum2;
	    
		//tenta "prever" a coordenada que o personagem irá alcançar para restringir através da colisão
	    switch(personagem.direcao) {
	        case "cima": //tenta ver se o próximo bloco que o jogador irá é colidível, para desabilitar o movimento do personagem caso sim
	            personagemTopoLin = (personagemTopoMundoY - personagem.velocidade) / gp.tileSize;
	            tileNum1 = gp.tileM.mapTileNum[personagemTopoLin][personagemEsqCol];
	            tileNum2 = gp.tileM.mapTileNum[personagemTopoLin][personagemDirCol];
	            break;
	        case "baixo":
	            personagemFundoLin = (personagemFundoMundoY + personagem.velocidade) / gp.tileSize;
	            tileNum1 = gp.tileM.mapTileNum[personagemFundoLin][personagemEsqCol];
	            tileNum2 = gp.tileM.mapTileNum[personagemFundoLin][personagemDirCol];
	            break;
	        case "esquerda":
	            personagemEsqCol = (personagemEsqMundoX - personagem.velocidade) / gp.tileSize;
	            tileNum1 = gp.tileM.mapTileNum[personagemTopoLin][personagemEsqCol];
	            tileNum2 = gp.tileM.mapTileNum[personagemFundoLin][personagemEsqCol];
	            break;
	        case "direita":
	            personagemDirCol = (personagemDirMundoX + personagem.velocidade) / gp.tileSize;
	            tileNum1 = gp.tileM.mapTileNum[personagemTopoLin][personagemDirCol];
	            tileNum2 = gp.tileM.mapTileNum[personagemFundoLin][personagemDirCol];
	            break;
	        default:
	            return;
	    }

	    if (gp.tileM.tile[tileNum1].colisao || gp.tileM.tile[tileNum2].colisao) {
	        personagem.colisaoLig = true;
	    }
	}

 
}
