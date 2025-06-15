package tile.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import principal.GamePanel;
import recursos.GeradorMapa;

import static org.junit.jupiter.api.Assertions.*;
import tile.*;

class TileManagerTest {

    private TileManager tileManager;
    private GamePanel gamePanel;

    @BeforeEach
    void setUp() {
        gamePanel = new GamePanel();
        tileManager = new TileManager(gamePanel);
    }

    @Test
    void testGetImagemTileInicializaTiles() {
        Tile[] tiles = tileManager.getTile();
        
        assertNotNull(tiles[0], "Tile 0 deve ser inicializado");
        assertNotNull(tiles[1], "Tile 1 deve ser inicializado");
        assertNotNull(tiles[2], "Tile 2 deve ser inicializado");

        assertFalse(tiles[0].getColisao(), "Tile 0 não deve ter colisão");
        assertTrue(tiles[1].getColisao(), "Tile 1 deve ter colisão");
        assertFalse(tiles[2].getColisao(), "Tile 2 não deve ter colisão");
    }

    @Test
    void testCarregarMapaCopiaGradeCorretamente() {
        int[][] esperado = tileManager.getGMapa().getGrade();
        int[][] atual = tileManager.getMapTileNum();

        assertNotNull(atual, "mapTileNum não pode ser nulo");
        assertEquals(esperado.length, atual.length, "Número de linhas deve coincidir");

        for (int i = 0; i < esperado.length; i++) {
            assertArrayEquals(esperado[i], atual[i], "Linha " + i + " da grade deve ser igual");
        }
    }

    @Test
    void testSetAndGetGp() {
        GamePanel novoGp = new GamePanel();
        tileManager.setGp(novoGp);
        assertEquals(novoGp, tileManager.getGp());
    }

    @Test
    void testSetAndGetMapTileNum() {
        int[][] novoMapa = new int[5][5];
        tileManager.setMapTileNum(novoMapa);
        assertEquals(novoMapa, tileManager.getMapTileNum());
    }

    @Test
    void testSetAndGetTile() {
        Tile[] novoTile = new Tile[3];
        novoTile[0] = new Tile();
        tileManager.setTile(novoTile);
        assertEquals(novoTile, tileManager.getTile());
    }

    @Test
    void testSetAndGetGMapa() {
        GeradorMapa novoMapa = new GeradorMapa();
        tileManager.setMapa(novoMapa);
        assertEquals(novoMapa, tileManager.getGMapa());
    }
}