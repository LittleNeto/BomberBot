package junit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.awt.TextField;

import static org.junit.jupiter.api.Assertions.*;
import principal.GamePanel;
import principal.ManipuladorTeclado;
import principal.GameState;
import principal.UI;

/**
 * JUnit que vai testar o funcionamento da classe ManipuladorTeclado
 * 
 * @author Neto
 * @version 1.0
 * @since 2025-06-15
 */
class ManipuladorTecladoTest {

	/**Objeto cuja classe será avaliado no JUnit*/
	private ManipuladorTeclado manipulador;
	/**Para testar o ManipuladorTeclado, faz-se necessário que haja um objeto da classe GamePanel*/
    private GamePanel gp;

    /**
     * Teste do funcionamento do construtor
     */
    @BeforeEach
    void setUp() {
        gp = new GamePanel();
        manipulador = new ManipuladorTeclado(gp);
    }

    /**
     * irá testar se o clique nos botões de movimentação(W, A, S, D) do personagem funcionam como deveria
     */
    @Test
    void testPressionarTeclaDeMovimento() {
        gp.gameState = GameState.PLAY;

        manipulador.keyPressed(new KeyEvent(new TextField(), 0, 0, 0, KeyEvent.VK_W, 'W'));
        assertTrue(manipulador.getCimaPress());

        manipulador.keyPressed(new KeyEvent(new TextField(), 0, 0, 0, KeyEvent.VK_A, 'A'));
        assertTrue(manipulador.getEsqPress());

        manipulador.keyPressed(new KeyEvent(new TextField(), 0, 0, 0, KeyEvent.VK_S, 'S'));
        assertTrue(manipulador.getBaixoPress());

        manipulador.keyPressed(new KeyEvent(new TextField(), 0, 0, 0, KeyEvent.VK_D, 'D'));
        assertTrue(manipulador.getDirPress());
    }

    /**
     * Irá testar se a função de registrar que as teclas de movimento (W, A, S, D) não estão pressionadas funciona como deveria
     */
    @Test
    void testSoltarTeclaDeMovimento() {
        manipulador.keyReleased(new KeyEvent(new TextField(), 0, 0, 0, KeyEvent.VK_W, 'W'));
        assertFalse(manipulador.getCimaPress());

        manipulador.keyReleased(new KeyEvent(new TextField(), 0, 0, 0, KeyEvent.VK_A, 'A'));
        assertFalse(manipulador.getEsqPress());

        manipulador.keyReleased(new KeyEvent(new TextField(), 0, 0, 0, KeyEvent.VK_S, 'S'));
        assertFalse(manipulador.getBaixoPress());

        manipulador.keyReleased(new KeyEvent(new TextField(), 0, 0, 0, KeyEvent.VK_D, 'D'));
        assertFalse(manipulador.getDirPress());
    }

    /**
     * Irá testar se a tecla de colocar a bomba no mapa (X) tem o clique registrado
     */
    @Test
    void testPressionarTeclaBomba() {
        gp.gameState = GameState.PLAY;

        manipulador.keyPressed(new KeyEvent(new TextField(), 0, 0, 0, KeyEvent.VK_X, 'X'));
        assertTrue(manipulador.getTeclaBombaPressionada());

        manipulador.keyReleased(new KeyEvent(new TextField(), 0, 0, 0, KeyEvent.VK_X, 'X'));
        assertFalse(manipulador.getTeclaBombaPressionada());
    }

    /**
     * Irá registrar se a tecla responsável pelo PLAY/PAUSE (P) tem o seu clique registrado
     */
    @Test
    void testAlternarPause() {
        gp.gameState = GameState.PLAY;
        manipulador.keyPressed(new KeyEvent(new TextField(), 0, 0, 0, KeyEvent.VK_P, 'P'));
        assertEquals(GameState.PAUSE, gp.gameState);

        manipulador.keyPressed(new KeyEvent(new TextField(), 0, 0, 0, KeyEvent.VK_P, 'P'));
        assertEquals(GameState.PLAY, gp.gameState);
    }

    /**
     * irá testar se as teclas responsáveis por mexer no menu principal (W, S) tem o seu clique registrado
     */
    @Test
    void testTituloMenuNavegacao() {
        gp.gameState = GameState.TITULO;
        gp.ui = new UI(gp);
        gp.ui.numComando = 0;

        manipulador.keyPressed(new KeyEvent(new TextField(), 0, 0, 0, KeyEvent.VK_W, 'W'));
        assertEquals(2, gp.ui.numComando);

        manipulador.keyPressed(new KeyEvent(new TextField(), 0, 0, 0, KeyEvent.VK_S, 'S'));
        assertEquals(0, gp.ui.numComando);
    }

}
