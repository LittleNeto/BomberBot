package principal.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import principal.GamePanel;
import principal.GameState;

class GamePanelTest {

	private GamePanel gamePanel;

    @BeforeEach
    public void setUp() {
        gamePanel = new GamePanel();
        gamePanel.setupGame();
    }

    @Test
    public void testInicializacaoEstadoTitulo() {
        assertEquals(GameState.TITULO, gamePanel.gameState);
    }

    @Test
    public void testColocarBombaLimitaUmaBomba() {
        int posX = 96;
        int posY = 96;

        gamePanel.colocarBomba(posX, posY);

        boolean bombaCriada = false;
        for (int i = 0; i < gamePanel.obj.length; i++) {
            if (gamePanel.obj[i] != null) {
                bombaCriada = true;
                break;
            }
        }
        assertTrue(bombaCriada);
        assertTrue(gamePanel.isBombaAtiva());

        // Tentar colocar outra bomba, deve ser ignorado
        gamePanel.colocarBomba(posX + 96, posY);
        int count = 0;
        for (int i = 0; i < gamePanel.obj.length; i++) {
            if (gamePanel.obj[i] instanceof objeto.OBJ_Bomba) {
                count++;
            }
        }
        assertEquals(1, count);
    }

    @Test
    public void testRestartReiniciaVidaJogador() {
        gamePanel.getJogador().setVida(0);
        assertEquals(0, gamePanel.getJogador().getVida());

        gamePanel.restart();

        assertTrue(gamePanel.getJogador().getVida() > 0);
    }
}
