package principal;

/**
 * Enumeração que representa os diferentes estados do jogo.
 * 
 * <p>
 * Cada estado pode estar associado a um índice de música,
 * utilizado pelo sistema de áudio para tocar a trilha sonora apropriada.
 * O valor {@code -1} indica que não há música associada ao estado.
 * </p>
 * 
 * <p>
 * Os valores incluem:
 * <ul>
 *   <li>{@code TITULO} — Tela de título/início do jogo.</li>
 *   <li>{@code FASE1}, {@code FASE2}, {@code FASE3} — Telas de transição de fases.</li>
 *   <li>{@code PLAY} — Estado ativo durante o jogo.</li>
 *   <li>{@code RANKING} — Tela de exibição de rankings.</li>
 *   <li>{@code CADASTRAR_RANKING} — Tela de entrada de nome para salvar no ranking.</li>
 *   <li>{@code GAME_OVER} — Tela de fim de jogo.</li>
 *   <li>{@code PAUSE} — Jogo pausado (sem música).</li>
 * </ul>
 * </p>
 * 
 * @author Mateus
 * @version
 * @since 
 */
public enum GameState {
    TITULO(0),
    FASE1(4),
    FASE2(4),
    FASE3(4),
    PLAY(1),
    RANKING(2),
    CADASTRAR_RANKING(3),
    GAME_OVER(4),
    PAUSE(-1);  

	/** Índice da música associada ao estado. */
    private final int musicaIndex;

    /**
     * Construtor do estado do jogo.
     *
     * @param musicaIndex Índice da música correspondente, ou -1 se não houver.
     */
    private GameState(int musicaIndex) {
        this.musicaIndex = musicaIndex;
    }

    /**
     * Retorna o índice da música associada a este estado.
     * 
     * @return Índice da música ou -1 se não houver música.
     */
    public int getMusicaIndex() {
        return musicaIndex;
    }
}
