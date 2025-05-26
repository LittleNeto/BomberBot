package recursos;

import java.util.List;
import java.util.ArrayList;

public class Mapa {
	
	public int[][] grade = new int[10][31];
	public List<Bomba> bombas;
	
	public Mapa() {
		this.grade = gerarMapa(this.grade);
		this.bombas = new ArrayList<>();
	}
	
	public static int[][] gerarMapa(int[][] grade) {
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
	
	public void escreveMapa() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 31; j++) {
				System.out.print(this.grade[i][j] + " ");
			}
			System.out.println();
		}
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

        public int[][] getGrade() {
            return grade;
        }
        
        
	
}
