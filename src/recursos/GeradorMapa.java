package recursos;

/**
*Classe para gerar as configurações do mapa
*Define como será o configurado os blocos do mapa
*Gera as posições dos bots e da porta, com base na análise do formato do mapa
*
* @author Neto
*/
public class GeradorMapa {
	
	/**matriz numérica que servirá como base para posicionar os blocos ao redor do mapa*/
	public int[][] grade = new int[10][31];
	
	/**matriz que representa a posição de cada bot, sendo 6 inimigos com duas coordenadas (X e Y)*/
	public int[][] posicaoInimigos = new int[6][2];
	
	/**Matriz de linha única que identifica a posição da porta, tendo duas coordenadas (X e Y)*/
	public int[][] posicaoPorta = new int[1][2];
	
	
	/**
	 * Construtor
	 */
	public GeradorMapa() {
		this.grade = gerarMapa(this.grade);
		this.posicaoInimigos = this.adicionarPosicao(this.posicaoInimigos, 0);
		this.posicaoPorta = this.adicionarPosicao(this.posicaoPorta, 2);
	}
	
	/**
	 * 
	 * @param grade, referende ao atributo grade pertencente à própria classe
	 * @return Matriz numérica que representa o posicionamento de cada bloco do mapa
	 */
	public int[][] gerarMapa(int[][] grade) {
		for (int i = 0; i < 10; i++) {
            for (int j = 0; j< 31; j++) {
                if (i % 9 == 0 || j % 30 == 0) { // as bordas do mapa devem aparecer sempre com bloco fixo
                    grade[i][j] = 1;
                } else if ((i % 2 == 0) && (j % 2 == 0)) { // posiciona onde os blocos fixos devem aparecer dentro do mapa
                    grade[i][j] = 1;
                } else if (i * j == 1 || i * j == 2) { // garante que a posição inicial do bot e suas posições adjacentes só tenham bloco de terra
                    grade[i][j] = 0;
                } else {
                	// sorteia onde os blocos de terra e os blocos de lixo devem aparecer no mapa, tendo, respectivamente, 70% e 30% de aparecer em dada posição
                    grade[i][j] = Math.random() > 0.6f ? 2 : 0;
                }
            }    
        }
        
        return grade;
	}
	
	/**
	 * Irá adicionar as coordenadas tanto dos bots como da prta 
	 * @param posicao, representado a matriz numérica (seja dos bots ou da porta) que conterá as coordenadas X e Y de cada um de seus elementos
	 * @param bloco, como a porta e os bots se posicionam em blocos diferentes, é importante ter uma referência de que bloco cada um deve aparecer
	 * @return Matriz referente às coordenadas X e Y (servindo tanto para os bots como para a porta)
	 */
	public int[][] adicionarPosicao(int[][] posicao, int bloco) {
		int X = 0, Y = 0;
		for (int[] objeto : posicao) {
			
			//avalia em que posições o objeto analisando não pode aparecer
			while (this.getGrade()[X][Y] != bloco || X * Y == 1 || X * Y == 2) {
				X = (int) Math.floor(Math.random() * 10);
				Y = (int) Math.floor(Math.random() * 31);
			}
			
			//adiciona as coordenadas de cada elemento
			objeto[0] = X;
			objeto[1] = Y;
			
			//se a matriz tiver mais de uma linha, irá garantir que o laço de teste aconteça para o elemento seguinte
			X = 0;
			Y = 0;
		}
		
		
		return posicao;
	}

	//setters
	
	/**
	 * 
	 * @param grade, para modificar as posições dos blocos no mapa
	 */
	public void setGrade(int[][] grade) {
		this.grade = grade;
	}
	
	/**
	 * 
	 * @param posicaoInimigos, para adicionar uma nova lista com as coordenadas dos inimigos
	 */
	public void setPosicaoInimigos(int[][] posicaoInimigos) {
		this.posicaoInimigos = posicaoInimigos;
	}
	
	/**
	 * 
	 * @param posicaoPorta, para colocar novas coordenadas para a porta
	 */
	public void setPosicaoPorta(int[][] posicaoPorta) {
		this.posicaoPorta = posicaoPorta;
	}
	
	//getters
	
	/**
	 * 
	 * @return a configuração atual da grade com os blocos
	 */
	public int[][] getGrade() {
		return grade;
	}
	
	/**
	 * 
	 * @return as coordenadas de cada bot presente no jogo
	 */
	public int[][] getPosicaoInimigos() {
		return this.posicaoInimigos;
	}
	
	/**
	 * 
	 * @return as coordenadas em que a porta se localiza no momento
	 */
	public int[][] getPosicaoPorta() {
		return this.posicaoPorta;
	}
	
	
}
