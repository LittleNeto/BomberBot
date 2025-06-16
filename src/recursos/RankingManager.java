package recursos;

import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe responsável por gerenciar os rankings dos jogadores.
 * Ela realiza operações de leitura, gravação e exibição dos registros salvos em arquivo.
 *
 * Os dados são armazenados no arquivo {@code DadosDeJogo.txt}, com cada linha representando:
 * <pre>
 * yyyy-MM-ddTHH:mm:ss - jogador - tempo
 * </pre>
 * 
 * Essa classe utiliza a classe {@link Ranking} para representar cada entrada.
 * 
 * @author Júlio
 * @version 1.0
 * @since 2025-05-27
 */
public class RankingManager {
	
    /** Nome do arquivo onde os dados do ranking são armazenados. */
	private static final String ARQUIVO ="DadosDeJogo.txt";
	
    /**
     * Salva os dados de um jogador (nome e tempo) no arquivo de ranking.
     * A data/hora é obtida automaticamente do sistema no momento da chamada.
     *
     * @param jogador Nome do jogador.
     * @param tempo Tempo da partida (em segundos, por exemplo).
     */	
	public static void salvarDados(String jogador, int tempo) {
		LocalDateTime atual = LocalDateTime.now();
		Ranking novoRegistro = new Ranking(atual, jogador, tempo);
		
		try (FileWriter writer = new FileWriter(ARQUIVO, true)){
			writer.write(novoRegistro.formataLinha() + "\n");
		} catch (IOException e) {
			System.err.println("erro ao salvar resultado da partida " + e.getMessage());
		}
	}

    /**
     * Lê o arquivo de ranking, ordena os registros e retorna os 5 melhores.
     * O critério de ordenação é definido pela classe {@link Ranking}:
     * primeiro pelo menor tempo, e em caso de empate, pela data mais antiga.
     *
     * @return Lista com até 5 melhores registros do ranking.
     */
	public static List<Ranking> getTop5(){
		List<Ranking> registros = new ArrayList<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))){
			String linha;
			while ((linha = reader.readLine()) != null) {
				try {
					registros.add(Ranking.puxaLinha(linha));
				}catch(Exception e) {
					
				}
			}
		} catch(IOException e) {
			System.err.println("erro ao ler ranking " + e.getMessage());
		}
		
		Collections.sort(registros);
		return registros.subList(0, Math.min(5,  registros.size()));
	}
	
    /**
     * Exibe no console os 5 melhores jogadores com seus tempos e datas formatadas.
     * Caso o ranking esteja vazio, exibe uma mensagem apropriada.
     */
	public static void exibir() {
		List<Ranking> top5 = getTop5();

	    if (top5.isEmpty()) {
	        System.out.println("Nenhum dado disponível no ranking.");
	        return;
	    }
	    
	    System.out.println("=== TOP 5 RANKING DOS MAIS BRABOS===");

	    for (int i = 0; i < top5.size(); i++) {
	        Ranking r = top5.get(i);
	        System.out.printf("%dº - %s - %ds - %s\n",
	            i + 1,
	            r.getJogador(),
	            r.getTempo(),
	            r.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
	    }
	}
}
