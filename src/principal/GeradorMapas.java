package principal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GeradorMapas {
	
	private static final int numLinhas = 10; //número de linhas do mapa
	private static final int numColunas = 17; //número de colunas do mapa
	private static final String caminho = "res/mapas/mapa01.txt"; //indica o nome do arquivo em que o mapa será guardado
	
	public static void gerarMapa() {
		
		int[][] mapa = new int[numLinhas][numColunas]; //matriz para representar o mapa
        
        for (int i = 0; i < numLinhas; i++) {
            for (int j = 0; j< numColunas; j++) {
                if (i % (numLinhas - 1) == 0 || j % (numColunas - 1) == 0) { //condição para gerar os bolcos da borda do mapa
                    mapa[i][j] = 1;
                } else if ((i % 2 == 0) && (j % 2 == 0)) { //condição para as posições adjacentes ao jogador não serem blocos
                    mapa[i][j] = 1;
                } else if (i * j == 1 || i * j == 2) { //condição para posicionar os blocos fixo no mapa
                    mapa[i][j] = 0;
                } else {
                    mapa[i][j] = Math.random() > 0.7f ? 2 : 0; //escolhe aleatoriamente onde é bloco destrutível (30% de chance de escolha) e onde é só terra (70% de chance de escolha)
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
                    if (j != numColunas - 1) writer.write(" "); //dá espaçamento entre os elementos da linha
                }
                if (i != numLinhas - 1) writer.write("\n"); //para que os elementos não fiquem em uma única linha
            }
            writer.close();
            System.out.println("Arquivo gerado com sucesso em: " + caminho);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}

