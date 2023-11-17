package Players;

import Players.NaturePlayer;


public class Player {
    private int score;
    private String name;
    private NaturePlayer nature;
    public Player(String name, NaturePlayer nature){
        this.score = 0;
        this.name = name;
        this.nature = nature;
    }
}
