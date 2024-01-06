package game;


public interface IIntersection {
    boolean isFree();
    void remove();
    String getColor();
    String toString();
}
