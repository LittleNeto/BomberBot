package principal;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	
	public Clip clip;
	URL soundURL[] = new URL[30]; //uma lista dos 'paths' dos sons
	private long posicaoAtual = 0;
	
	public Sound() {
		
		soundURL[0] = getClass().getResource("/musicas/musica1.wav");
		soundURL[1] = getClass().getResource("/musicas/musica2.wav");
		soundURL[2] = getClass().getResource("/musicas/musica3.wav");
	}
	
	public void setFile(int i) {
		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch(Exception e) {
		}
	}
	
	public void play() {
		if (clip != null) { //para que não tente começar a música antes de carregá-la
			clip.start(); 	
		}
	}
	
	public void loop() {
		if (clip != null) { //para que não tente começar a música antes de carregá-la
			clip.loop(clip.LOOP_CONTINUOUSLY); //para a música repetir após um tempo	
		}
	}
	
	public void parar() {
		if (clip != null) { //para que não tente começar a música antes de carregá-la
			clip.stop();
		}
	}
	
	public void pausar() {
		if (clip != null && clip.isRunning()) {
			posicaoAtual = clip.getMicrosecondPosition(); //pega o tempo que parou a música em microssegundos e salva antes de parar
			clip.stop();
		}
	}
	
	public void retomar() {
		if(clip != null) {
			clip.setMicrosecondPosition(posicaoAtual); //carrega o tempo da música que havia sido salva
			clip.start();
		}
	}
}
