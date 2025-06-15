package principal.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;
import principal.GamePanel;
import principal.ManipuladorTeclado;
import principal.GameState;
import principal.UI;

class ManipuladorTecladoTest {

	private ManipuladorTeclado manipulador;
    private GamePanel gp; // substituto do GamePanel real

    @BeforeEach
    void setUp() {
        gp = new GamePanel();
        manipulador = new ManipuladorTeclado(gp);
    }

    @Test
    void testPressionarTeclaDeMovimento() {
        gp.gameState = GameState.PLAY;

        manipulador.keyPressed(new KeyEvent(new java.awt.TextField(), 0, 0, 0, KeyEvent.VK_W, 'W'));
        assertTrue(manipulador.getCimaPress());

        manipulador.keyPressed(new KeyEvent(new java.awt.TextField(), 0, 0, 0, KeyEvent.VK_A, 'A'));
        assertTrue(manipulador.getEsqPress());

        manipulador.keyPressed(new KeyEvent(new java.awt.TextField(), 0, 0, 0, KeyEvent.VK_S, 'S'));
        assertTrue(manipulador.getBaixoPress());

        manipulador.keyPressed(new KeyEvent(new java.awt.TextField(), 0, 0, 0, KeyEvent.VK_D, 'D'));
        assertTrue(manipulador.getDirPress());
    }

    @Test
    void testSoltarTeclaDeMovimento() {
        manipulador.keyReleased(new KeyEvent(new java.awt.TextField(), 0, 0, 0, KeyEvent.VK_W, 'W'));
        assertFalse(manipulador.getCimaPress());

        manipulador.keyReleased(new KeyEvent(new java.awt.TextField(), 0, 0, 0, KeyEvent.VK_A, 'A'));
        assertFalse(manipulador.getEsqPress());

        manipulador.keyReleased(new KeyEvent(new java.awt.TextField(), 0, 0, 0, KeyEvent.VK_S, 'S'));
        assertFalse(manipulador.getBaixoPress());

        manipulador.keyReleased(new KeyEvent(new java.awt.TextField(), 0, 0, 0, KeyEvent.VK_D, 'D'));
        assertFalse(manipulador.getDirPress());
    }

    @Test
    void testPressionarTeclaBomba() {
        gp.gameState = GameState.PLAY;

        manipulador.keyPressed(new KeyEvent(new java.awt.TextField(), 0, 0, 0, KeyEvent.VK_X, 'X'));
        assertTrue(manipulador.getTeclaBombaPressionada());

        manipulador.keyReleased(new KeyEvent(new java.awt.TextField(), 0, 0, 0, KeyEvent.VK_X, 'X'));
        assertFalse(manipulador.getTeclaBombaPressionada());
    }

    @Test
    void testAlternarPause() {
        gp.gameState = GameState.PLAY;
        manipulador.keyPressed(new KeyEvent(new java.awt.TextField(), 0, 0, 0, KeyEvent.VK_P, 'P'));
        assertEquals(GameState.PAUSE, gp.gameState);

        manipulador.keyPressed(new KeyEvent(new java.awt.TextField(), 0, 0, 0, KeyEvent.VK_P, 'P'));
        assertEquals(GameState.PLAY, gp.gameState);
    }

    @Test
    void testTituloMenuNavegacao() {
        gp.gameState = GameState.TITULO;
        gp.ui = new UI(gp);
        gp.ui.numComando = 0;

        manipulador.keyPressed(new KeyEvent(new java.awt.TextField(), 0, 0, 0, KeyEvent.VK_W, 'W'));
        assertEquals(2, gp.ui.numComando); // rolou para cima e voltou para 2

        manipulador.keyPressed(new KeyEvent(new java.awt.TextField(), 0, 0, 0, KeyEvent.VK_S, 'S'));
        assertEquals(0, gp.ui.numComando); // rolou para baixo e voltou para 0
    }

}
