package stones;

import game.IIntersection;
import game.IStone;

import java.util.ArrayList;

public class Stone implements IStone {

    private final Color color;
    //private int nbLiberties;
//TODO A SUPPR ?
    /**
     * constructor of stone
     * @param color the color of the stones
     */
    public Stone(Color color){
        this.color = color;
        //TODO this.nbLiberties = 4;
    }

    /**
     * Get the number of liberties of an Intersection
     * @param intersections Array containing the 4 touching intersections (north, est, west, south)
     * @return the number of liberties
     */
    public int getNbLiberties(ArrayList<IIntersection> intersections) {
        int liberties = 0;
        for(IIntersection i : intersections)
            if (i.isFree())
                liberties++;
    return liberties;
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
