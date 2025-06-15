package tile.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import tile.Tile;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    private Tile tile;
    private BufferedImage imagemTeste;

    @BeforeEach
    void setUp() {
        tile = new Tile();
        imagemTeste = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
    }

    @Test
    void testSetAndGetImagem() {
        tile.setImagem(imagemTeste);
        assertEquals(imagemTeste, tile.getImagem(), "A imagem definida deve ser retornada corretamente.");
    }

    @Test
    void testSetAndGetColisaoTrue() {
        tile.setColisao(true);
        assertTrue(tile.getColisao(), "Colisão deveria ser true após definição.");
    }

    @Test
    void testSetAndGetColisaoFalse() {
        tile.setColisao(false);
        assertFalse(tile.getColisao(), "Colisão deveria ser false após definição.");
    }
}