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

    public void makeMove(String color, String move) {
        int size = boardMap.length;

        final int ascii_A = 65;
        int letter = move.charAt(0);
        int number;

        if (move.length() == 2) {
            number = Integer.parseInt(move.substring(1, 2));
        } else {
            number = Integer.parseInt(move.substring(1, 3));
        }

        if (color.equals("BLACK")) {
            this.boardMap[size - (number)][letter - ascii_A] = 'X';
        }
        else
            this.boardMap[size-(number)][letter-ascii_A] = 'O';
    }

    public void makeRndMove(String color) {
        int size = boardMap.length;

        int letter = (int) ((Math.random() * (boardMap.length + 1)));
        int number = (int) ((Math.random() * (boardMap.length + 1)));
        System.out.println(size - number);
        System.out.println(letter);

        for (int i =0; i<100;i++) {
            letter = (int) ((Math.random() * (boardMap.length + 1))-1);
            number = (int) ((Math.random() * (boardMap.length + 1))-1);
            System.out.print(size - number);
            System.out.println(letter);
        }

        if (color.equals("BLACK"))
            this.boardMap[size - (number)][letter] = 'X';
        else
            this.boardMap[size-(number)][letter] = 'O';
    }

    public char[][] getBoardMap() {
        return this.boardMap;
    }

}