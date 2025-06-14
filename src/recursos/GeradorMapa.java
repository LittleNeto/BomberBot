package recursos;

/**
*
* @author Neto
*/

public class GeradorMapa {
	
	public int[][] grade = new int[10][31];
	public int[][] posicaoInimigos = new int[6][2];
	public int[][] posicaoPorta = new int[1][2];
	
	public GeradorMapa() {
		this.grade = gerarMapa(this.grade);
		this.posicaoInimigos = this.adicionarPosicao(this.posicaoInimigos, 0);
		this.posicaoPorta = this.adicionarPosicao(this.posicaoPorta, 2);
	}
	
	public int[][] gerarMapa(int[][] grade) {
		for (int i = 0; i < 10; i++) {
            for (int j = 0; j< 31; j++) {
                if (i % 9 == 0 || j % 30 == 0) {
                    grade[i][j] = 1;
                } else if ((i % 2 == 0) && (j % 2 == 0)) {
                    grade[i][j] = 1;
                } else if (i * j == 1 || i * j == 2) {
                    grade[i][j] = 0;
                } else {
                    grade[i][j] = Math.random() > 0.6f ? 2 : 0;
                }
            }    
        }
        
        return grade;
	}
	
	public int[][] adicionarPosicao(int[][] posicao, int bloco) {
		int X = 0, Y = 0;
		for (int[] objeto : posicao) {
			while (this.getGrade()[X][Y] != bloco || X * Y == 1 || X * Y == 2) {
				X = (int) Math.floor(Math.random() * 10);
				Y = (int) Math.floor(Math.random() * 31);
			}
			objeto[0] = X;
			objeto[1] = Y;
			
			X = 0;
			Y = 0;
		}
		
		
		return posicao;
	}

	//setters
	public void setGrade(int[][] grade) {
		this.grade = grade;
	}
	
	//getters
	public int[][] getGrade() {
		return grade;
	}
	
}
