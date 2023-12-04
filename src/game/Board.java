package game;

import intersection.Intersection;
import java.util.ArrayList;

public class Board {
    private ArrayList<ArrayList<IIntersection>> boardMap;
    //TODO: eventuellemnt passer sur un tableau 2D

    public Board(int size) {
        initialize(size);
    }

    public void initialize(int size) {
        boardMap = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            boardMap.add(new ArrayList<>(size));
            for (int j = 0; j < size ; j++) {
                boardMap.get(i).add(new Intersection());
            }
        }
    }

    public void clear() {
        initialize(boardMap.size());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int size = boardMap.size();
        sb.append("  ");
        for (int i = 0; i < size; i++)
            sb.append(" ").append((char) (i + 65)).append(" ");

        sb.append("\n");
        for (int i = 0; i < size; i++) {
            if (size - i <= 9)
                sb.append(" ");
            sb.append(size - i);

            ArrayList<IIntersection> line = boardMap.get(i);
            for (int j = 0; j < size; j++)
                sb.append(" " + line.get(j) + " ");

            if ((size - i) == 2) {
                sb.append("2     ");
                //TODO : Faire la méthode white capture
                sb.append("WHITE (O) has captured 0 stones\n");
            } else if ((size - i) == 1) {
                sb.append("1     ");
                //TODO : Faire la méthode black capture
                sb.append("BLACK (X) has captured 0 stones\n");
            } else
                sb.append(size - i).append("\n");
        }
        sb.append("  ");
        for (int i = 0; i < size; i++)
            sb.append(" " + (char) (i + 65) + " ");
        sb.append("\n");
        return sb.toString();
    }


    private boolean isMoveValid(String color, int x, int y) {
        IIntersection iIntersection = this.boardMap.get(x).get(y);
        if (iIntersection.isFree()) {
            //TODO : savoir si malgrès que le coup soit libre il soit jouable
            return true;
        }
        return false;
    }

    public String makeMove(String color, String move, String noCommand) {
        int size = boardMap.size();

        final int ascii_A = 65;
        int letter = (move.charAt(0)) - ascii_A;
        int number;

        if (move.length() == 2) {
            number = size-Integer.parseInt(move.substring(1, 2));
        } else {
            number = size-Integer.parseInt(move.substring(1, 3));
        }

        if (isMoveValid(color, number, letter)){
            this.boardMap.get(number).set(letter, new Intersection(color));
            return ("=" + noCommand + " ");
        }
        return ("?" + noCommand + " illegal move\n\n");
    }

    public String makeRndMove(String color, String noCommand) {
        int letter;
        int number;
        int numTest = 0;

        do {
            letter = (int) ((Math.random() * (boardMap.size() + 1))-1);
            number = (int) ((Math.random() * (boardMap.size() + 1))-1);
            numTest++;
            if(numTest <= Math.pow(boardMap.size(), 2)){
                return ("?" + noCommand + " illegal move\n\n");
            }
        } while (!isMoveValid(color, number, letter));

        this.boardMap.get(number).set(letter, new Intersection(color));
        return ("=" + noCommand + " ");
    }
}