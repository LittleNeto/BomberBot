package principal.test;

import entidade.Personagem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import principal.ColisaoChecador;
import principal.GamePanel;
import tile_Interativo.BlocoInterativo;
import entidade.Jogador;
import principal.ManipuladorTeclado;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ColisaoChecadorTest {

	private ColisaoChecador cc;
    private GamePanel gp;
    private Personagem personagem;
    private ManipuladorTeclado keyH;

    @BeforeEach
    void setUp() {
        gp = new GamePanel();
        keyH = new ManipuladorTeclado(gp);
        personagem = new Jogador(gp, keyH);

        personagem.setMundoX(32);
        personagem.setMundoY(32);
        personagem.setVelocidade(4);
        personagem.setDirecao("baixo");

        // Área sólida padrão
        Rectangle area = new Rectangle(8, 8, 16, 16);
        personagem.setAreaSolida(area);
        personagem.areaSolidaDefaultX = 8;
        personagem.areaSolidaDefaultY = 8;

        cc = new ColisaoChecador(gp);
    }

    @Test
    void testColisaoComTile() {
        // Suponha que a tile (2,2) tem colisão
        gp.getTileM().getMapTileNum()[2][1] = 1;
        gp.getTileM().getMapTileNum()[2][2] = 1;
        gp.getTileM().getTile()[1].setColisao(true);

        cc.checaTile(personagem);

        assertTrue(personagem.getColisaoLig(), "O personagem deveria detectar colisão com o tile.");
    }

    @Test
    void testColisaoComBlocoInterativo() {
        BlocoInterativo bloco = new BlocoInterativo(gp, 31, 10);
        bloco.setMundoX(32);
        bloco.setMundoY(48); // personagem vai para baixo
        bloco.setAreaSolida(new Rectangle(0, 0, 32, 32));
        bloco.setAreaSolidaDefaultX(0);
        bloco.setAreaSolidaDefaultY(0);

        BlocoInterativo[] blocos = { bloco };

        int index = cc.checaBlocoInterativo(personagem, blocos);
        assertEquals(0, index, "Deveria colidir com o bloco interativo.");
        assertTrue(personagem.getColisaoLig(), "O personagem deveria ter colisão ligada.");
    }

    @Test
    void testSemColisaoComBloco() {
        BlocoInterativo bloco = new BlocoInterativo(gp, 31, 10);
        bloco.setMundoX(200); // Longe do personagem
        bloco.setMundoY(200);
        bloco.setAreaSolida(new Rectangle(0, 0, 32, 32));
        bloco.setAreaSolidaDefaultX(0);
        bloco.setAreaSolidaDefaultY(0);

        BlocoInterativo[] blocos = { bloco };

        int index = cc.checaBlocoInterativo(personagem, blocos);
        assertEquals(999, index, "Não deveria detectar colisão.");
        assertFalse(personagem.getColisaoLig(), "A colisão não deveria estar ativada.");
    }

}
