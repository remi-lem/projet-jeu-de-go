package intersection;

import game.IIntersection;
import stones.Stone;

import java.util.Objects;

public class intersection implements IIntersection {
    private final Stone stone;

    public intersection(){
        this.stone = null;
    }

    public intersection(Stone stone){
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