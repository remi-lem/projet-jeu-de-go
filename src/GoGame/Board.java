package GoGame;

public class Board {
    private char[][] boardMap;

    public Board(int size) {
        initialize(size);
    }

    public void initialize(int size) {
        boardMap = new char[size][size];
        for (int i = 0 ; i < size ; i++) {
            for (int j = 0; j < size; j++) {
                boardMap[i][j] = '.';
            }
        }
    }

    public void clear() {
        initialize(boardMap.length);
    }

    public void show() {
        int size = boardMap.length;
        System.out.print("  ");
        for (int i = 0; i < size; i++)
            System.out.print(" " + (char)(i + 65) + " ");

        System.out.println();
        for (int i = 0 ; i < size ; i++) {
            if (size - i <= 9)
                System.out.print(" ");
            System.out.print(size - i);

            for (int j = 0 ; j < size ; j++)
                System.out.print(" " + boardMap[i][j] + " ");

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
            else
                System.out.println(size-i);
        }
        System.out.print("  ");
        for (int i = 0; i < size; i++)
            System.out.print(" " + (char)(i + 65) + " ");

        System.out.println();
    }

    public void makeMove(String color, String move){
        final int ascii_0 = 49, ascii_A = 65;
        int letter = move.charAt(0);
        int number = move.charAt(1);

        if (color.equals("BLACK"))
            boardMap[number-ascii_0][letter-ascii_A] = 'X';
        else
            boardMap[number-ascii_0][letter-ascii_A] = 'O';
        show(); // TODO : update le jeu
    }
}