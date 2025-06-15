package tile_Interativo.test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import principal.GamePanel;
import tile_Interativo.BlocoLixo;

class BlocoLixoTest {

	private GamePanel gp;
    private BlocoLixo bloco;

    @BeforeEach
    public void setUp() {
        gp = new GamePanel();
        bloco = new BlocoLixo(gp, 4, 5);
    }

    @Test
    public void testConstrutorInicializaCorretamente() {
        int tileSize = gp.getTileSize();

        assertEquals(4 * tileSize, bloco.getMundoX());
        assertEquals(5 * tileSize, bloco.getMundoY());

        assertEquals("baixo", bloco.getDirecao());
        assertTrue(bloco.destruivel);
        assertNotNull(bloco.getBaixo1());
    }

    @Test
    public void testImagemCarregadaCorretamente() {
        BufferedImage imagem = bloco.getBaixo1();
        assertNotNull(imagem, "/tiles_Interativos/lixo_alfa.png");
        assertEquals(gp.getTileSize(), imagem.getWidth());
        assertEquals(gp.getTileSize(), imagem.getHeight());
    }

}
