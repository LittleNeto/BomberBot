package entidade;

import java.awt.image.BufferedImage;

/**
 *
 * @author Mateus
 */
public class Personagem {
    public int x, y;
    public int velocidade;
    
    public BufferedImage cima1, cima2, baixo1, baixo2, esq1, esq2, dir1, dir2;
    public String direcao;
    
    public int spriteCount = 0;
    public int spriteNum = 1;
}
