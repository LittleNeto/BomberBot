package recursos;

/**
*
* @author Neto
*/

import java.util.List;
import java.util.ArrayList;

public class Mapa {
	
	public int[][] grade = new int[10][31];
	public List<Bomba> bombas;
	public int[][] posicaoInimigos = new int[6][2];
	
	public Mapa() {
		this.grade = gerarMapa(this.grade);
		this.bombas = new ArrayList<>();
		this.posicaoInimigos = this.adicionarPosicaoInimigos(this.posicaoInimigos);
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
	
	public void apagarFogo() {
		for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 31; j++) {
                if (this.grade[i][j] == 3) this.grade[i][j] = 0;
            }
        }
		
	}
	
	public void adicionarBomba(Bomba bomba) {
		if (this.bombas.size() < 3) {
			this.bombas.add(bomba);
		}
	}
	
	public int[][] adicionarPosicaoInimigos(int[][] posicaoInimigos) {
		int X = 0, Y = 0;
		for (int i = 0; i< 6; i++) {
			while (this.getGrade()[X][Y] != 0 || X * Y == 1 || X * Y == 2) {
				X = (int) Math.floor(Math.random() * 10);
				Y = (int) Math.floor(Math.random() * 31);
			}
			posicaoInimigos[i][0] = X;
			posicaoInimigos[i][1] = Y;
			
			X = 0;
			Y = 0;
		}
		
		
		return posicaoInimigos;
	}

	//setters
	public void setGrade(int[][] grade) {
		this.grade = grade;
	}

	public void setBombas(List<Bomba> bombas) {
		this.bombas = bombas;
	}
	
	//getters
	public int[][] getGrade() {
		return grade;
	}
	
	public List<Bomba> getBombas() {
		return bombas;
	}
	
}