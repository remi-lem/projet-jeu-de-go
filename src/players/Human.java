package players;

public class Human extends Player {
    public Human() {
        super();
    }

    @Override
    public boolean canPlayConsole() {
        return true;
    }
}
