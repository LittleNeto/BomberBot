package principal.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import principal.Posicao;

class PosicaoTest {

	@Test
    public void testPosicaoConstructor() {
        int xEsperado = 10;
        int yEsperado = 20;

        Posicao posicao = new Posicao(xEsperado, yEsperado);

        assertEquals(xEsperado, posicao.x, "O valor de x deve ser igual ao esperado");
        assertEquals(yEsperado, posicao.y, "O valor de y deve ser igual ao esperado");
    }

}
