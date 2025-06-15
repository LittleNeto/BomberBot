package principal.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import principal.GameState;

class GameStateTest {

	@Test
    public void testMusicaIndexAssociations() {
        assertEquals(0, GameState.TITULO.getMusicaIndex());
        assertEquals(4, GameState.FASE1.getMusicaIndex());
        assertEquals(4, GameState.FASE2.getMusicaIndex());
        assertEquals(4, GameState.FASE3.getMusicaIndex());
        assertEquals(1, GameState.PLAY.getMusicaIndex());
        assertEquals(2, GameState.RANKING.getMusicaIndex());
        assertEquals(3, GameState.CADASTRAR_RANKING.getMusicaIndex());
        assertEquals(4, GameState.GAME_OVER.getMusicaIndex());
        assertEquals(-1, GameState.PAUSE.getMusicaIndex());
    }

}
