package intersection;

import game.IIntersection;
import java.util.ArrayList;

public class Intersection implements IIntersection {
    public enum Color {
        white, black, nothing
    }
    private Color color;

    public Intersection() {
        this.color = Color.nothing;
    }

    public Intersection(String color) {
        this.color = Color.valueOf(color);
    }

    @Override
    public boolean isFree() {
        return this.color == Color.nothing;
    }

    @Override
    public void remove() {
        this.color = Color.nothing;
    }

    @Override
    public String getColor() {
        return this.color.name();
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
