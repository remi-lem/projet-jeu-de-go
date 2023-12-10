package game;

import intersection.Intersection;
import java.util.ArrayList;

public class Board {
    private ArrayList<ArrayList<IIntersection>> boardMap;
    //TODO : éventuellement passer sur un tableau 2D

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
                sb.append(" ").append(line.get(j)).append(" ");

            if ((size - i) == 2) {
                sb.append("2     ");
                sb.append("WHITE (O) has captured ").append(0).append(" stones\n");//TODO get white's score
            } else if ((size - i) == 1) {
                sb.append("1     ");
                sb.append("BLACK (X) has captured ").append(0).append(" stones\n");//TODO get black's score
            } else
                sb.append(size - i).append("\n");
        }
        sb.append("  ");
        for (int i = 0; i < size; i++)
            sb.append(" ").append((char) (i + 65)).append(" ");
        sb.append("\n");
        return sb.toString();
    }


    private boolean isMoveValid(String color, int x, int y) {
        IIntersection currentIntersection = this.boardMap.get(x).get(y);
        ArrayList<IIntersection> neighborsIntersections = new ArrayList<>();
        try {
            neighborsIntersections.add(this.boardMap.get(x - 1).get(y));
        } catch(IndexOutOfBoundsException e){
            neighborsIntersections.add(new Intersection(getOppositeColor(color)));
        }
        try {
            neighborsIntersections.add(this.boardMap.get(x + 1).get(y));
        } catch(IndexOutOfBoundsException e){
            neighborsIntersections.add(new Intersection(getOppositeColor(color)));
        }
        try {
            neighborsIntersections.add(this.boardMap.get(x).get(y - 1));
        } catch(IndexOutOfBoundsException e){
            neighborsIntersections.add(new Intersection(getOppositeColor(color)));
        }
        try {
            neighborsIntersections.add(this.boardMap.get(x).get(y + 1));
        } catch(IndexOutOfBoundsException e){
            neighborsIntersections.add(new Intersection(getOppositeColor(color)));
        }
        return currentIntersection.isFree() && !currentIntersection.isCaptured(neighborsIntersections);
    }

    private String getOppositeColor(String color) {
        if(color.equals(Intersection.Color.black.toString())){
            return Intersection.Color.white.toString();
        }
        return Intersection.Color.black.toString();
    }

    public String makeMove(String color, String move, String noCommand) {
        int size = boardMap.size();
        int letter = Character.toUpperCase(move.charAt(0)) - 'A';
        int number;

        if (move.length() == 2) {
            number = size - Integer.parseInt(move.substring(1, 2));
        } else {
            number = size - Integer.parseInt(move.substring(1, 3));
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
            letter = (int) (Math.random() * boardMap.size());
            number = (int) (Math.random() * boardMap.size());
            numTest++;
            if(numTest > Math.pow(boardMap.size(), 2))
                return ("?" + noCommand + " illegal move");
        } while (!isMoveValid(color, number, letter));
            this.boardMap.get(number).set(letter, new Intersection(color));
        return ("=" + noCommand + " ");
    }

    public void updateScore() {
        // TODO : compter le nombre d'intersections contrôlées (+1 point par pierres en jeu)
        // TODO : compter le nombre de pierres capturées (+1 par pierre)
        // TODO : compter territoires encerclés (+1 par espaces vides entourés par les pierres d'un seul joueur)
    }

    public void updateCaptures() {
        //TODO
    }
}