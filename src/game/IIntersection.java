package game;

import java.util.ArrayList;

public interface IIntersection {
    boolean isFree();
    boolean isCaptured(ArrayList<IIntersection> iIntersections);
    String getColor();
    String toString();
}
