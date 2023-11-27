package game;

import intersection.intersection;
import stones.Color;
import stones.Stone;

import java.util.ArrayList;

public class Board {

    private ArrayList<ArrayList<IIntersection>> boardMap;

    public Board(int size) {
        initialize(size);
    }

    public void initialize(int size) {
        boardMap = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            boardMap.add(new ArrayList<>(size));
            for (int j = 0; j < size ; j++) {
                boardMap.get(i).add(new intersection());
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

    public void makeMove(Color color, String move) {
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
            if (color.equals("BLACK")) {
                this.boardMap.get(number).set(letter, new intersection(new Stone(Color.black)));// TODO : false par défaut mais peut être attaqué
            } else
                this.boardMap.get(number).set(letter, new intersection(new Stone(Color.white)));
        }
    }

    private boolean isMoveValid(Color color, int x, int y) {
        if (this.boardMap.get(x).get(y).isFree()) {
            //TODO : savoir si malgrès que le coup soit libre il soit jouable
            return true;
        }
        return false;
    }

    public void makeRndMove(Color color) {
        int letter;
        int number;

        do {
            letter = (int) ((Math.random() * (boardMap.size() + 1))-1);
            number = (int) ((Math.random() * (boardMap.size() + 1))-1);
        } while (!isMoveValid(color, number, letter));

        if (color == Color.black) {
            this.boardMap.get(number).set(letter, new intersection(new Stone(Color.black)));// TODO : false par défaut mais peut être attaqué
        } else
            this.boardMap.get(number).set(letter, new intersection(new Stone(Color.white)));
    }
}