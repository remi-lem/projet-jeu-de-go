package GoGame;

public class Board {
    private char[][] boardMap;

    public Board(int size) {
        boardMap = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardMap[i][j] = '.';
            }
        }
    }

    public void show() {
        //TODO : a coder
    }

    public boolean initialized() {
        //TODO : a coder
        return true;
    }
}