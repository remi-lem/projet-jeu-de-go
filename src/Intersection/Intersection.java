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
        if(this.stone == null){
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        if (isFree()){
            return ".";
        }
        return stone.toString();
    }
}