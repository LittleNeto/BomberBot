package inimigo;

import entidade.BotPersonagem;
import principal.GamePanel;

public class BotFactory {

    public static BotPersonagem criarBot(String dificuldade, GamePanel gp) {
        return switch (dificuldade.toLowerCase()) {
            case "facil" -> new BotFacil(gp);
            case "medio" -> new BotMedio(gp);
            case "dificil" -> new BotDificil(gp);
            default -> throw new IllegalArgumentException("Dificuldade inválida: " + dificuldade);
        };
    }
}
