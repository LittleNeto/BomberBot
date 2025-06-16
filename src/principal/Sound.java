package principal;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Classe responsável pelo gerenciamento de sons e músicas do jogo.
 * Permite tocar, pausar, retomar, parar e repetir músicas em loop.
 * Utiliza a biblioteca {@code javax.sound.sampled} para controle de áudio.
 * 
 * Os arquivos de som devem estar em formato compatível
 * e localizados nos caminhos definidos no array {@code soundURL}.
 * 
 * @author Mateus
 * @version
 * @since 
 */
public class Sound {
	
	 /** Clip de áudio utilizado para reprodução do som atual. */
	public Clip clip;
	
	/** Array com até 30 URLs apontando para arquivos de som. */
	URL soundURL[] = new URL[30]; //uma lista dos 'paths' dos sons
	
	 /** Posição atual do áudio em microssegundos, usada para pausar e retomar. */
	private long posicaoAtual = 0;
	
	 /**
     * Construtor da classe Sound.
     * Inicializa os caminhos para as músicas padrão do jogo.
     */
	public Sound() {
		
		soundURL[0] = getClass().getResource("/musicas/musica1.wav");
		soundURL[1] = getClass().getResource("/musicas/musica2.wav");
		soundURL[2] = getClass().getResource("/musicas/musica3.wav");
	}
	
	/**
     * Carrega o arquivo de áudio correspondente ao índice fornecido.
     *
     * @param i Índice do som no array {@code soundURL}
     */
	public void setFile(int i) {
		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch(Exception e) {
		}
	}
	
	 /**
     * Inicia a reprodução do áudio carregado.
     * Deve ser chamado após {@code setFile}.
     */
	public void play() {
		if (clip != null) { //para que não tente começar a música antes de carregá-la
			clip.start(); 	
		}
	}
	
	/**
     * Coloca o som em repetição contínua (loop infinito).
     */
	public void loop() {
		if (clip != null) { //para que não tente começar a música antes de carregá-la
			clip.loop(clip.LOOP_CONTINUOUSLY); //para a música repetir após um tempo	
		}
	}
	
	/**
     * Para a reprodução do som atual.
     */
	public void parar() {
		if (clip != null) { //para que não tente começar a música antes de carregá-la
			clip.stop();
		}
	}
	
	/**
     * Pausa a reprodução do som, salvando a posição atual.
     */
	public void pausar() {
		if (clip != null && clip.isRunning()) {
			posicaoAtual = clip.getMicrosecondPosition(); //pega o tempo que parou a música em microssegundos e salva antes de parar
			clip.stop();
		}
	}
	
	/**
     * Retoma a reprodução do som a partir da posição em que foi pausado.
     */
	public void retomar() {
		if(clip != null) {
			clip.setMicrosecondPosition(posicaoAtual); //carrega o tempo da música que havia sido salva
			clip.start();
		}
	}
}
