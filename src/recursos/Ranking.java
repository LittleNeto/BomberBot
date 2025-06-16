package recursos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa um registro de ranking contendo o nome do jogador, 
 * o tempo de conclusão e a data/hora do resultado.
 * 
 * Essa classe implementa {@link Comparable} para que possa ser ordenada com base no tempo.
 * Se dois jogadores tiverem o mesmo tempo, o critério de desempate é a data (mais antigo vem primeiro).
 * 
 * Utilizada para leitura e escrita de rankings armazenados em arquivos .txt.
 * 
 * Exemplo de linha no .txt:
 * <pre>
 * 2025-06-14T16:45:30 - João - 120
 * </pre>
 * 
 * @author Júlio
 */

public class Ranking implements Comparable<Ranking>{
	
    /** Data e hora em que o jogador completou a partida. */
	private LocalDateTime dataHora;
	
    /** Nome do jogador. */
	private String jogador;
	
    /** Tempo de conclusão. */
	private int tempo;
	
    /** Formatador de data/hora para leitura e escrita em arquivo. */
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	
    /** Formatador de data para exibir no formato tradicional (DD_MM_AAAA)*/
	private static final DateTimeFormatter DDMMYYYY_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	
    /**
     * Construtor completo da classe Ranking.
     *
     * @param dataHora Data e hora do registro.
     * @param jogador Nome do jogador.
     * @param tempo Tempo de conclusão.
     */
	public Ranking(LocalDateTime dataHora, String jogador, int tempo) {
		super();
		this.dataHora = dataHora;
		this.jogador = jogador;
		this.tempo = tempo;
	} 
	
    /**
     * Cria um objeto Ranking a partir de uma linha de texto formatada.
     * A linha deve estar no formato: <code>yyyy-MM-ddTHH:mm:ss - jogador - tempo</code>
     *
     * @param linha Linha lida do arquivo.
     * @return Um novo objeto Ranking com os dados extraídos.
     */
	public static Ranking puxaLinha(String linha) {
        String[] partes = linha.split(" - ");
        LocalDateTime dataHora = LocalDateTime.parse(partes[0], FORMATTER);
        String nome = partes[1];
        int tempo = Integer.parseInt(partes[2]);
        return new Ranking(dataHora, nome, tempo);
    }
	
    /**
     * Formata os dados do ranking como uma linha de texto para salvar em arquivo.
     *
     * @return String formatada com data/hora, nome e tempo.
     */
	public String formataLinha() {
		return dataHora.format(FORMATTER) + " - " + jogador + " - " + tempo;
	}
	
    /**
     * @return Tempo de conclusão.
     */
	public int getTempo(){
		return tempo;
	}
	
    /**
     * @return Data e hora do registro.
     */
	public LocalDateTime getDataHora() {
		return dataHora;
	}

    /**
     * @return Nome do jogador.
     */
	public String getJogador() {
		return jogador;
	}
	
    /**
     * @return Data formatada (DD_MM_AAAA).
     */
	public String getDataFormatada() {
		return dataHora.format(DDMMYYYY_FORMATTER);
	}
	
    /**
     * Compara dois objetos Ranking com base no tempo (ascendente).
     * Em caso de empate, compara pela data (mais antiga vem primeiro).
     *
     * @param outro Outro objeto Ranking a ser comparado.
     * @return Valor negativo, zero ou positivo conforme a ordenação.
     */
	@Override
	public int compareTo(Ranking outro) {
		if (this.tempo != outro.tempo) {
			return Integer.compare(this.tempo, outro.tempo); // Menor tempo prevalece
		}
		return this.dataHora.compareTo(outro.dataHora); // Mais antigo prevalece
	}
	
    /**
     * Retorna uma representação textual do ranking, no mesmo formato do arquivo.
     *
     * @return Linha formatada contendo data, jogador e tempo.
     */
	@Override
	public String toString() {
		return formataLinha();
	}
}
