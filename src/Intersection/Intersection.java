package Intersection;

import GoGame.IIntersection;
import Stones.Stone;

import java.util.Objects;

public class Intersection implements IIntersection {
    private final Stone stone;

    public Intersection(){
        this.stone = null;
    }

    public Intersection(Stone stone){
        this.stone = stone;
    }

    @Override
    public boolean isFree() {
        return this.stone == null;
    }

    @Override
    public String toString(){
        if (isFree()){
            return ".";
        }
        return Objects.requireNonNull(stone).toString();
    }
}