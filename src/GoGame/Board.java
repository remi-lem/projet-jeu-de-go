package GoGame;

import Intersection.Intersection;
import Stones.Color;
import Stones.Stone;

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
                boardMap.get(i).add(new Intersection());
            }
        }
    }

    public void clear() {
        initialize(boardMap.size());
    }

    public void show() {
        int size = boardMap.size();
        System.out.print("  ");
        for (int i = 0; i < size; i++)
            System.out.print(" " + (char) (i + 65) + " ");

        System.out.println();
        for (int i = 0; i < size; i++) {
            if (size - i <= 9)
                System.out.print(" ");
            System.out.print(size - i);

            ArrayList<IIntersection> line = boardMap.get(i);
            for (int j = 0; j < size; j++)
                System.out.print(" " + line.get(j) + " ");

            if ((size - i) == 2) {
                System.out.print("2     ");
                //TODO : Faire la méthode white capture
                System.out.println("//WHITE (O) has captured 0 stones");
            } else if ((size - i) == 1) {
                System.out.print("1     ");
                //TODO : Faire la méthode black capture
                System.out.println("//BLACK (X) has captured 0 stones");
            } else
                System.out.println(size - i);
        }
        System.out.print("  ");
        for (int i = 0; i < size; i++)
            System.out.print(" " + (char) (i + 65) + " ");
        System.out.println();
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
            if (color.equals("black")) {
                this.boardMap.get(number).set(letter, new Intersection(new Stone(Color.black)));// TODO : false par défaut mais peut être attaqué
            } else
                this.boardMap.get(number).set(letter, new Intersection(new Stone(Color.white)));
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

        if (color.equals("black"))
            this.boardMap.get(number).set(letter, new Intersection(new Stone(Color.black))); // TODO : false par défaut mais peut être attaqué
        else
            this.boardMap.get(number).set(letter, new Intersection(new Stone(Color.white)));
        return ("=" + noCommand + "\n\n");
    }
}