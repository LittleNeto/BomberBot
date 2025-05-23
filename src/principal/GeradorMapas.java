package principal;

public class GeradorMapas {

	
	public static int[][] gerarMapa(int numLinhas, int numColunas) {
		
		int[][] mapa = new int[numLinhas][numColunas];
        
        for (int i = 0; i < numLinhas; i++) {
            for (int j = 0; j< numColunas; j++) {
                if (i % (numLinhas - 1) == 0 || j % (numColunas - 1) == 0) {
                    mapa[i][j] = 1;
                } else if ((i % 2 == 0) && (j % 2 == 0)) {
                    mapa[i][j] = 1;
                } else if (i * j == 1 || i * j == 2) {
                    mapa[i][j] = 0;
                } else {
                    mapa[i][j] = Math.random() > 0.6f ? 2 : 0;
                }
            }    
        }
        
        return mapa;

	}
	
	
}