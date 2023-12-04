package players;

import game.IPlayer;

public abstract class Player implements IPlayer {
    private final int score;
    private final String color;
    public Player(String color){
        this.score = 0;
        this.color = color;
    }

    @Override
    public int getScore() {
        return score;
    }

    public String getColor() {return color;}
}
