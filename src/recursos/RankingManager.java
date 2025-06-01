package recursos;

/**
*
* @author Júlio
*/

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RankingManager {
	private static final String ARQUIVO ="DadosDeJogo.txt";
	
	// Método de salvamento de dados 	
	public static void salvarDados(String jogador, int tempo) {
		LocalDateTime atual = LocalDateTime.now();
		Ranking novoRegistro = new Ranking(atual, jogador, tempo);
	}

}
