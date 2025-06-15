package principal.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import principal.Sound;

class SoundTest {

	private Sound sound;

    @BeforeEach
    public void setUp() {
        sound = new Sound();
    }

    @Test
    public void testSetFile_validIndex_doesNotThrow() {
        assertDoesNotThrow(() -> sound.setFile(0), "Deveria carregar o arquivo de som corretamente.");
    }

    @Test
    public void testPlay_afterSetFile() {
        sound.setFile(0);
        assertDoesNotThrow(() -> sound.play(), "Deveria tocar o som sem lançar exceção.");
    }

    @Test
    public void testLoop_afterSetFile() {
        sound.setFile(1);
        assertDoesNotThrow(() -> sound.loop(), "Deveria iniciar o loop da música sem lançar exceção.");
    }

    @Test
    public void testStop_afterPlay() throws InterruptedException {
        sound.setFile(2);
        sound.play();
        Thread.sleep(200); // pequena espera para garantir que começou a tocar
        assertDoesNotThrow(() -> sound.parar(), "Deveria parar a música sem erro.");
    }

    @Test
    public void testPauseAndResume() throws InterruptedException {
        sound.setFile(0);
        sound.play();
        Thread.sleep(300); // tempo para iniciar a reprodução

        sound.pausar();
        long posicaoAntes = sound.clip.getMicrosecondPosition();
        Thread.sleep(200); // tempo que deveria estar pausado
        sound.retomar();
        Thread.sleep(200); // deve continuar tocando

        long posicaoDepois = sound.clip.getMicrosecondPosition();

        assertTrue(posicaoDepois > posicaoAntes, "A música deveria ter retomado e avançado após retomar.");
    }

    @Test
    public void testSetFile_invalidIndex_doesNotThrowButFailsSilently() {
        // Como não há tratamento de erro, isso só garante que não lança exceção
        assertDoesNotThrow(() -> sound.setFile(999), "Índice inválido não deveria lançar exceção.");
    }

}
