package recursos;

/**
*
* @author Júlio
*/

import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class RankingManager {
	private static final String ARQUIVO ="DadosDeJogo.txt";
	
	// Método de salvamento de dados 	
	public static void salvarDados(String jogador, int tempo) {
		LocalDateTime atual = LocalDateTime.now();
		Ranking novoRegistro = new Ranking(atual, jogador, tempo);
		
		try (FileWriter writer = new FileWriter(ARQUIVO, true)){
			writer.write(novoRegistro.formataLinha() + "\n");
		} catch (IOException e) {
			System.err.println("erro ao salvar resultado da partida " + e.getMessage());
		}
	}

	// Método que lê o .txt pega os 5 melhores e joga na lista
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
	
	// Printa os 5 melhores
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