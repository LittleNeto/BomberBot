package principal.test;

import static org.junit.jupiter.api.Assertions.*;

import inimigo.BotMedio;
import objeto.OBJ_Porta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import recursos.GeradorMapa;
import tile_Interativo.BlocoLixo;
import principal.GamePanel;
import principal.AssetSetter;

class AssetSetterTest {

	private GamePanel gp;
    private GeradorMapa gMapa;
    private AssetSetter assetSetter;

    @BeforeEach
    public void setup() {
        gp = new GamePanel();

        // Ajuste para simplificação do teste
        gp.obj = new objeto.SuperObjeto[10];
        gp.monstros = new BotMedio[10];
        gp.iTiles = new BlocoLixo[100];

        gMapa = new GeradorMapa();
        gMapa.posicaoPorta[0][0] = 2;
        gMapa.posicaoPorta[0][1] = 3;

        gMapa.posicaoInimigos[0][0] = 4;
        gMapa.posicaoInimigos[0][1] = 5;
        gMapa.posicaoInimigos[1][0] = 6;
        gMapa.posicaoInimigos[1][1] = 7;

        // Blocos interativos
        gMapa.getGrade()[1][1] = 2;
        gMapa.getGrade()[2][2] = 2;

        assetSetter = new AssetSetter(gp, gMapa);
    }

    @Test
    public void testSetObject() {
        assetSetter.setObject();

        assertNotNull(gp.obj[0]);
        assertTrue(gp.obj[0] instanceof OBJ_Porta);
        assertEquals(3 * gp.getTileSize(), gp.obj[0].mundoX);
        assertEquals(2 * gp.getTileSize(), gp.obj[0].mundoY);
    }

    @Test
    public void testSetBot() {
        assetSetter.setBot();

        assertNotNull(gp.monstros[0]);
        assertNotNull(gp.monstros[1]);
        assertTrue(gp.monstros[0] instanceof BotMedio);
        assertTrue(gp.monstros[1] instanceof BotMedio);

        assertEquals(5 * gp.getTileSize(), gp.monstros[0].getMundoX());
        assertEquals(4 * gp.getTileSize(), gp.monstros[0].getMundoY());

        assertEquals(7 * gp.getTileSize(), gp.monstros[1].getMundoX());
        assertEquals(6 * gp.getTileSize(), gp.monstros[1].getMundoY());
    }

    @Test
    public void testSetBlocoInterativo() {
        assetSetter.setBlocoInterativo();

        assertNotNull(gp.iTiles[0]);
        assertTrue(gp.iTiles[0] instanceof BlocoLixo);

        assertNotNull(gp.iTiles[1]);
        assertTrue(gp.iTiles[1] instanceof BlocoLixo);
    }

}
