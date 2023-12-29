package app;

import game.IPlayer;
import players.Human;
import players.Robot;

public class Factory {
    static public IPlayer createPlayer(String type) {
        if (type.equals("human")) return new Human();
        else if (type.equals("robot")) return new Robot();
        else throw new IllegalArgumentException();
    }
}
