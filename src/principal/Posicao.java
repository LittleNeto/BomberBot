package principal;


/**
 * Representa uma posição no espaço 2D com coordenadas inteiras X e Y.
 * Pode ser usada para representar posições de entidades, objetos, explosões, etc.
 * 
 * É uma classe utilitária simples.
 * 
 * @author Mateus
 * @version 1.0 
 * @since 2025-05-13
 */
public class Posicao {
	
	/** Coordenada X da posição. */
    public int x;
    
    /** Coordenada Y da posição. */
    public int y;
    
    /**
     * Construtor da classe Posicao.
     *
     * @param x Valor da coordenada X
     * @param y Valor da coordenada Y
     */
    public Posicao(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
