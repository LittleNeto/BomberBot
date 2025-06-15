package tile_Interativo.test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import principal.GamePanel;
import tile_Interativo.BlocoInterativo;

class BlocoInterativoTest {

	private GamePanel gp;
    private BlocoInterativo bloco;

    @BeforeEach
    public void setUp() {
        // Criar um GamePanel "fake" ou real simples
        gp = new GamePanel(); // Você pode precisar adaptar se GamePanel tiver dependências complexas
        bloco = new BlocoInterativo(gp, 2, 3); // Exemplo: coluna=2, linha=3
    }

    @Test
    public void testConstrutorEGetters() {
        int tileSize = gp.getTileSize();
        assertEquals(2 * tileSize, bloco.getMundoX());
        assertEquals(3 * tileSize, bloco.getMundoY());
        assertNotNull(bloco.getAreaSolida());
        assertEquals(tileSize, bloco.getAreaSolida().width);
        assertEquals(tileSize, bloco.getAreaSolida().height);
    }

    @Test
    public void testSetters() {
        bloco.setMundoX(100);
        bloco.setMundoY(150);
        bloco.setDirecao("baixo");
        Rectangle novaArea = new Rectangle(10, 10, 20, 20);
        bloco.setAreaSolida(novaArea);
        bloco.setAreaSolidaDefaultX(5);
        bloco.setAreaSolidaDefaultY(7);

        assertEquals(100, bloco.getMundoX());
        assertEquals(150, bloco.getMundoY());
        assertEquals("baixo", bloco.getDirecao()); // atributo protegido, acesso direto
        assertEquals(novaArea, bloco.getAreaSolida());
        assertEquals(5, bloco.getAreaSolidaDefaultX());
        assertEquals(7, bloco.getAreaSolidaDefaultY());
    }

    @Test
    public void testSetupCarregaImagem() {
        // Coloque uma imagem válida no pacote resources para esse teste funcionar
        BufferedImage img = bloco.setup("/tiles_Interativos/lixo_alfa.png");
        assertNotNull(img, "/tiles_Interativos/lixo_alfa.png");
        assertEquals(gp.getTileSize(), img.getWidth());
        assertEquals(gp.getTileSize(), img.getHeight());
    }

}
