package inimigo;

/**
 * Enumeração que representa os possíveis estados de comportamento de um bot inimigo.
 * Cada estado influencia a lógica de movimentação e tomada de decisão do bot.
 *
 * <ul>
 *   <li>{@link #ANDANDO} – O bot está se movendo normalmente na direção atual.</li>
 *   <li>{@link #COLIDIU} – O bot colidiu com um obstáculo e está esperando para reagir.</li>
 *   <li>{@link #ESPERANDO_NOVA_DIRECAO} – O bot aguarda um curto período antes de escolher uma nova direção após colisão.</li>
 * </ul>
 *
 * Usado na classe {@link entidade.BotPersonagem} e suas subclasses.
 * 
 * @author júlio
 * @version
 * @since
 */
public enum EstadoBot {
    
    /**
     * Bot está andando normalmente na direção atual.
     */
    ANDANDO,

    /**
     * Bot colidiu com algo (ex: parede) e está em pausa.
     */
    COLIDIU,

    /**
     * Bot está pronto para escolher uma nova direção após colisão.
     */
    ESPERANDO_NOVA_DIRECAO
}