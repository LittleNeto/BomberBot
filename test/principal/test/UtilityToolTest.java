package principal.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.awt.Color;

import principal.UtilityTool;

class UtilityToolTest {

	@Test
    public void testScaleImage() {
        // Cria uma imagem original 100x100, tipo RGB
        BufferedImage original = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        
        // Preenche a imagem com uma cor (por exemplo, branco) para garantir que não esteja vazia
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                original.setRGB(x, y, Color.WHITE.getRGB());
            }
        }

        UtilityTool utility = new UtilityTool();

        // Escala para 50x50
        BufferedImage scaled = utility.scaleImage(original, 50, 50);

        // Verifica se o objeto não é nulo
        assertNotNull(scaled);

        // Verifica se a largura e altura estão corretas
        assertEquals(50, scaled.getWidth());
        assertEquals(50, scaled.getHeight());

        // Verifica se o tipo da imagem escalada é igual ao original
        assertEquals(original.getType(), scaled.getType());
    }

}
