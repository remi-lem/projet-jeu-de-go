package players;

import game.IPlayer;

public abstract class Player implements IPlayer {
    private int score;
    public Player(){
        this.score = 0;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean canPlayConsole() {
        return false;
    }
}
