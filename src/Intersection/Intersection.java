package Intersection;

import GoGame.IIntersection;
import Stones.Stone;

public class Intersection implements IIntersection {
    private Stone stone;

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
        return stone.toString();
    }
}