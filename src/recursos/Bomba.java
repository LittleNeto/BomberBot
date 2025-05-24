package recursos;

import java.util.List;

public class Bomba {

	private int x;
	private int y;
	private int alcance;
	
	public Bomba(int x, int y) {
		this.x = x;
		this.y = y;
		this.alcance = 2;
	}
	
	public void explodir(Mapa mapa, List<Bomba> bombas) {
		if (this.getX() >= 0 && this.getX() < 10 && this.getY() >= 0 && this.getY() < 31) {
            mapa.grade[this.getX()][this.getY()] = 3; // O centro da explosão pega fogo

            // Explode para cima
            for (int i = 1; i <= this.getAlcance() && mapa.grade[this.getX() - i][this.getY()] != 1; i++) {
                if (mapa.grade[this.getX() - i][this.getY()] == 2) {
                    // Se atingiu um bloco destrutível, a propagação para aqui
                	mapa.grade[this.getX() - i][this.getY()] = 3;
                    i = this.getAlcance(); // Força a saída do loop
                } else if (mapa.grade[this.getX() - i][this.getY()] == 0) {
                	mapa.grade[this.getX() - i][this.getY()] = 3;
                }
                for (int j = 0; j < mapa.bombas.size(); j++) {
                	if (mapa.bombas.get(j).getX() == this.getX() - i && mapa.bombas.get(j).getY() == this.getY()) {
                		mapa.bombas.remove(j).explodir(mapa, mapa.bombas);
                	}
                }
            }

            // Explode para baixo
            for (int i = 1; i <= this.getAlcance() && mapa.grade[this.getX() + i][this.getY()] != 1; i++) {
                if (mapa.grade[this.getX() + i][this.getY()] == 2) {
                	mapa.grade[this.getX() + i][this.getY()] = 3;
                    i = this.getAlcance();
                } else if (mapa.grade[this.getX() + i][this.getY()] == 0) {
                	mapa.grade[this.getX() + i][this.getY()] = 3;
                }
                for (int j = 0; j < mapa.bombas.size(); j++) {
                	if (mapa.bombas.get(j).getX() == this.getX() + i && mapa.bombas.get(j).getY() == this.getY()) {
                		mapa.bombas.remove(j).explodir(mapa, mapa.bombas);
                	}
                }
            }

            // Explode para a esquerda
            for (int i = 1; i <= this.getAlcance() && mapa.grade[this.getX()][this.getY() - i] != 1; i++) {
                if (mapa.grade[this.getX()][this.getY() - i] == 2) {
                	mapa.grade[this.getX()][this.getY() - i] = 3;
                    i = this.getAlcance();
                } else if (mapa.grade[this.getX()][this.getY() - i] == 0) {
                	mapa.grade[this.getX()][this.getY() - i] = 3;
                }
                for (int j = 0; j < mapa.bombas.size(); j++) {
                	if (mapa.bombas.get(j).getX() == this.getX() && mapa.bombas.get(j).getY() == this.getY() - i) {
                		mapa.bombas.remove(j).explodir(mapa, mapa.bombas);
                	}
                }
            }

            // Explode para a direita
            for (int i = 1; i <= this.getAlcance() && mapa.grade[this.getX()][this.getY() + i] != 1; i++) {
                if (mapa.grade[this.getX()][this.getY() + i] == 2) {
                	mapa.grade[this.getX()][this.getY() + i] = 3;
                    i = this.getAlcance();
                } else if (mapa.grade[this.getX()][this.getY() + i] == 0) {
                	mapa.grade[this.getX()][this.getY() + i] = 3;
                }
                for (int j = 0; j < mapa.bombas.size(); j++) {
                	if (mapa.bombas.get(j).getX() == this.getX() && mapa.bombas.get(j).getY() == this.getY() + i) {
                		mapa.bombas.remove(j).explodir(mapa, mapa.bombas);
                	}
                }
            }
        } else {
            System.out.println("Posição da bomba inválida!");
        }
    }

	//setters
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setAlcance(int alcance) {
		this.alcance = alcance;
	}
	
	//getters
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getAlcance() {
		return alcance;
	}
	
}