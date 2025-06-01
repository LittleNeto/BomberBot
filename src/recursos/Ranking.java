package recursos;

/**
*
* @author Júlio
*/

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ranking implements Comparable<Ranking>{
	private LocalDateTime dataHora;
	private String jogador;
	private int tempo;
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	
	public Ranking(LocalDateTime dataHora, String jogador, int tempo) {
		super();
		this.dataHora = dataHora;
		this.jogador = jogador;
		this.tempo = tempo;
	}
	
	// Método pra puxar a linha do .txt
	public static Ranking puxaLinha(String linha) {
        String[] partes = linha.split(" - ");
        LocalDateTime dataHora = LocalDateTime.parse(partes[0], FORMATTER);
        String nome = partes[1];
        int tempo = Integer.parseInt(partes[2]);
        return new Ranking(dataHora, nome, tempo);
    }
	
	// junta os dados em uma String na forma que vai ser inserido no .txt
	public String formataLinha() {
		return dataHora.format(FORMATTER) + " - " + jogador + " - " + tempo;
	}
	
	public int getTempo(){
		return tempo;
	}
	
	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public String getJogador() {
		return jogador;
	}
	
	@Override
	public int compareTo(Ranking outro) {
		if (this.tempo != outro.tempo) {
			return Integer.compare(this.tempo, outro.tempo); // Menor tempo prevalece
		}
		return this.dataHora.compareTo(outro.dataHora); // Mais antigo prevalece
	}
	
	@Override
	public String toString() {
		return formataLinha();
	}
}