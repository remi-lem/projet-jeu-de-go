package game;

import java.util.ArrayList;

public interface IIntersection {
    boolean isFree();
    void remove();
    String getColor();
    String toString();
}
