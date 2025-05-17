package principal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GeradorMapas {
	
	private static final int numLinhas = 10;
	private static final int numColunas = 17;
	private static final String caminho = "res/mapas/mapa01.txt";
	
	public static void gerarMapa() {
		
		int[][] mapa = new int[numLinhas][numColunas];
        
        for (int i = 0; i < numLinhas; i++) {
            for (int j = 0; j< numColunas; j++) {
                if (i == 0 || i == numLinhas - 1 || j == 0 || j == numColunas - 1) {
                    mapa[i][j] = 1;
                } else if ((i % 2 == 0) && (j % 2 == 0)) {
                    mapa[i][j] = 1;
                } else if (i * j == 1 || i * j == 2) {
                    mapa[i][j] = 0;
                } else {
                    mapa[i][j] = Math.random() > 0.7f ? 2 : 0;
                }
            }    
        }
        
        salvarMapa(mapa);
		
	}
	
	public static void salvarMapa(int[][] mapa) {
		try {
            // Garante que a pasta existe
            File diretorio = new File("res/mapas");
            if (!diretorio.exists()) {
                diretorio.mkdirs();
            }

            // Cria e escreve no arquivo
            BufferedWriter writer = new BufferedWriter(new FileWriter(caminho));
            for (int i = 0; i < numLinhas; i++) {
                for (int j = 0; j < numColunas; j++) {
                    writer.write(Integer.toString(mapa[i][j]));
                    if (j != numColunas - 1) writer.write(" ");
                }
                if (i != numLinhas - 1) writer.write("\n");
            }
            writer.close();
            System.out.println("Arquivo gerado com sucesso em: " + caminho);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}

