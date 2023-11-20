package Stones;

import GoGame.IStone;

public class Stone implements IStone {

    private final Color color;
    private final boolean isFree;

    public Stone(String color, boolean isFree){
        this.color = Color.valueOf(color);
        this.isFree = isFree;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public boolean isFree() {
        return isFree;
    }
}
