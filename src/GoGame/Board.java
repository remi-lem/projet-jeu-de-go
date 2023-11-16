package GoGame;

public class Board {
    private char[][] boardMap;

    public Board(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardMap[i][j] = '.';
            }
        }
    }

    public void show() {
        int size = boardMap.length;
        for (int i = 0; i < size; i++) {
            System.out.println((char)(i+64));
        }
        for (int i = 0 ; i < size ; i++) {
            System.out.print(size-i);
            for (int j=0 ; j<size; j++) {
                System.out.print(boardMap[i][j]);
            }
            System.out.println(size-i);
        }
        for (int i = 0; i < size; i++) {
            System.out.println((char)(i+64));
        }
    }

    public boolean initialized() {
        //TODO : a coder
        return true;
    }
}