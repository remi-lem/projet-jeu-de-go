package GoGame;

import Stones.Color;

import java.util.ArrayList;

public interface IStone {
    Color getColor();
    int getNbLiberties(ArrayList<IIntersection> intersections);
}
