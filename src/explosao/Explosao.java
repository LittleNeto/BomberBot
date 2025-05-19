package explosao;

import java.util.Scanner;

public class Explosao {

    public static final int LINHAS = 10;
    public static final int COLUNAS = 17;
    public static final int[][] mapa = new int[LINHAS][COLUNAS];
    public static final int ALCANCE = 1;

    public static void main(String[] args) {
        Scanner t = new Scanner(System.in);
        gerarMapa(LINHAS, COLUNAS);
        System.out.println("Mapa inicial:");
        escrevaMapa();
        System.out.print("Aonde colocar a bomba? (linha coluna): ");
        int linha = t.nextInt();
        int coluna = t.nextInt();
        explodir(linha, coluna);
        System.out.println("\nExplosão:");
        escrevaMapa();
        apagaFogo();
        System.out.println("\nFogo apagou! Mapa:");
        escrevaMapa();
        t.close();
    }

    public static void gerarMapa(int numLinhas, int numColunas) {
        for (int i = 0; i < numLinhas; i++) {
            for (int j = 0; j < numColunas; j++) {
                if (i == 0 || i == numLinhas - 1 || j == 0 || j == numColunas - 1) {
                    mapa[i][j] = 1; // Bordas são sempre blocos
                } else if ((i % 2 == 0) && (j % 2 == 0)) {
                    mapa[i][j] = 1; // Blocos fixos internos
                } else if ((i == 1 && j == 1) || (i == 1 && j == 2)) {
                    mapa[i][j] = 0; // Espaços iniciais livres
                } else {
                    mapa[i][j] = Math.random() > 0.7f ? 2 : 0; // Blocos destrutíveis ou vazios
                }
            }
        }
    }

    public static void escrevaMapa() {
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                System.out.print(mapa[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void explodir(int linha, int coluna) {
        if (linha >= 0 && linha < LINHAS && coluna >= 0 && coluna < COLUNAS) {
            mapa[linha][coluna] = 3; // O centro da explosão pega fogo

            // Explode para cima
            for (int i = 1; i <= ALCANCE && linha - i >= 0 && mapa[linha - i][coluna] != 1; i++) {
                mapa[linha - i][coluna] = 3;
                if (mapa[linha - i][coluna] == 2) {
                    // Se atingiu um bloco destrutível, a propagação para aqui (sem break)
                    i = ALCANCE; // Força a saída do loop
                }
            }

            // Explode para baixo
            for (int i = 1; i <= ALCANCE && linha + i < LINHAS && mapa[linha + i][coluna] != 1; i++) {
                mapa[linha + i][coluna] = 3;
                if (mapa[linha + i][coluna] == 2) {
                    i = ALCANCE;
                }
            }

            // Explode para a esquerda
            for (int i = 1; i <= ALCANCE && coluna - i >= 0 && mapa[linha][coluna - i] != 1; i++) {
                mapa[linha][coluna - i] = 3;
                if (mapa[linha][coluna - i] == 2) {
                    i = ALCANCE;
                }
            }

            // Explode para a direita
            for (int i = 1; i <= ALCANCE && coluna + i < COLUNAS && mapa[linha][coluna + i] != 1; i++) {
                mapa[linha][coluna + i] = 3;
                if (mapa[linha][coluna + i] == 2) {
                    i = ALCANCE;
                }
            }
        } else {
            System.out.println("Posição da bomba inválida!");
        }
    }

    public static void apagaFogo() {
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                if (mapa[i][j] == 3) mapa[i][j] = 0;
            }
        }
    }
}
