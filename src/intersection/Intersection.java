package intersection;

import game.IIntersection;
import java.util.ArrayList;

public class Intersection implements IIntersection {
    public enum Color {
        white, black
    }

    private Color color;

    public Intersection() {
        this.color = null;
    }

    public Intersection(String color) {
        this.color = Color.valueOf(color);
    }

    @Override
    public boolean isFree() {
        return this.color == null;
    }

    @Override
    public boolean isCaptured(ArrayList<IIntersection> neighborsIntersections) {
        int cptOtherColor = 0;
        for (IIntersection intersection : neighborsIntersections) {
            if (intersection.isFree()) return false;
            else if (intersection.getColor().equals(this.getColor())){
                // TODO : regarder si le groupe est encerclé (recursivité?)
            }
        }
        return true;
    }

    @Override
    public String getColor() {
        return color.toString();
    }

    @Override
    public String toString() {
        if (isFree())
            return ".";
        else {
            if (getColor().compareToIgnoreCase(Color.black.toString()) == 0)
                return "X";
            return "O";
        }
    }
}
