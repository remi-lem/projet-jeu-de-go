package GoGame;

public class Board {
    private final char[][] boardMap;

    public Board(int size) {
        boardMap = new char[size][size];
        for (int i = 0 ; i < size ; i++) {
            for (int j = 0; j < size; j++) {
                boardMap[i][j] = '.';
            }
        }
    }

    public void show() {
        int size = boardMap.length;
        System.out.print("  ");
        for (int i = 0; i < size; i++) {
            System.out.print(" " + (char)(i + 65) + " ");
        }
        System.out.println();
        for (int i = 0 ; i < size ; i++) {
            if (size - i <= 9) {
                System.out.print(" ");
            }
            System.out.print(size - i);
            for (int j = 0 ; j < size ; j++) {
                System.out.print(" " + boardMap[i][j] + " ");
            }
            if ((size-i) == 2){
                System.out.print("2     ");
                //TODO : Faire la méthode white capture
                System.out.println("//WHITE (O) has captured 0 stones");
            }
            else if ((size-i) == 1){
                System.out.print("1     ");
                //TODO : Faire la méthode black capture
                System.out.println("//BLACK (X) has captured 0 stones");
            }
            else {
                System.out.println(size-i);
            }
        }
        System.out.print("  ");
        for (int i = 0; i < size; i++) {
            System.out.print(" " + (char)(i + 65) + " ");
        }
        System.out.println();
    }
}
