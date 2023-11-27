package game;

import stones.Color;

import java.util.ArrayList;

public interface IStone {
    Color getColor();
    int getNbLiberties(ArrayList<IIntersection> intersections);
}
