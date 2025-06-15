package principal.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import principal.UI;
import principal.GamePanel;


class UITest {

	private UI ui;
    private Graphics2D g2;
    private GamePanel gp;
    
	@BeforeEach
    public void setUp() {
        gp = new GamePanel();
        ui = new UI(gp);
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        g2 = img.createGraphics();
        ui.setG2(g2);
    }

    @Test
    public void testGetXparaTextoCentralizado() {
        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        String texto = "BomberBot";
        int x = ui.getXparaTextoCentralizado(texto);
        int screenWidth = gp.getScreenWidth();
        int textWidth = g2.getFontMetrics().stringWidth(texto);
        int expectedX = screenWidth / 2 - textWidth / 2;

        assertEquals(expectedX, x);
    }

    @Test
    public void testMostrarMensagem() {
        ui.mostrarMensagem("Olá jogador!");
        assertEquals("Olá jogador!", ui.mensagem);
        assertEquals(true, ui.mensagemLig);
    }
}
