package intersection;

import game.IIntersection;
import java.util.ArrayList;

public class Intersection implements IIntersection {
    public enum Color {
        white, black
    }
    private Color color;

    public Intersection(){
        this.color = null;
    }

    public Intersection(String color){
        this.color = Color.valueOf(color);
    }

    @Override
    public boolean isFree() {
        return this.stone == null;
    }

    @Override
    public String getColor() {
        return color.toString();// TODO : À tester
    }

    @Override
    public String toString(){
        if (isFree())
            return ".";
        else {
            if (getColor().compareToIgnoreCase(Color.black.toString()) == 0) //TODO : À VERIFIER
                return "X";
            return "O";
        }
    }
}
