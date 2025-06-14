package principal;

//cada música tem seu próprio valor e o -1 indica que não tem
public enum GameState {
    TITULO(0),
    PLAY(1),
    RANKING(2),
    CADASTRAR_RANKING(3),
    GAME_OVER(4),
    PAUSE(-1);  

    private final int musicaIndex;

    private GameState(int musicaIndex) {
        this.musicaIndex = musicaIndex;
    }

    public int getMusicaIndex() {
        return musicaIndex;
    }
}
