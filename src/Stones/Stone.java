package Stones;

import GoGame.IStone;

public class Stone implements IStone {

    private final Color color;
    private final boolean isAttacked;

    public Stone(Color color, boolean isAttacked){
        this.color = color;
        this.isAttacked = isAttacked;
    }

    @Override
    public String toString() {
        if (getColor() == Color.black){
            return "X";
        }
        return "O";
    }

    @Override
    public Color getColor() {
        return color;
    }
}
