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
	
	public Ranking(LocalDateTime dataHora, String jogador, int tempo) {
		super();
		this.dataHora = dataHora;
		this.jogador = jogador;
		this.tempo = tempo;
	}
	
	
	

}
