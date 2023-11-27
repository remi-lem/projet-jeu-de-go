package players;


import game.IPlayer;

public abstract class Player implements IPlayer {
    private final int score;
    private final String name;
    public Player(String name){
        this.score = 0;
        this.name = name;
    }

    @Override
    public int getScore() {
        return score;
    }
}
