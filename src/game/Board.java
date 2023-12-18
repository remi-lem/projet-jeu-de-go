package game;

import intersection.Intersection;
import java.util.ArrayList;

import intersection.Intersection.Color;

public class Board {
    private ArrayList<ArrayList<IIntersection>> boardMap;

    public static final int NB_NEIGHBORS = 4;
    public static int capturedStonesWhite = 0, capturedStonesBlack = 0;//TODO stocker ca dans Player

    public Board(int size) {
        initialize(size);
    }

    public void initialize(int size) {
        boardMap = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            boardMap.add(new ArrayList<>(size));
            for (int j = 0; j < size ; j++)
                boardMap.get(i).add(new Intersection());
        }
    }

    public void clear() {
        initialize(boardMap.size());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int size = boardMap.size();
        sb.append(" ");
        for (int i = 0; i < size; i++)
            sb.append("  ").append((char) (i + 65));

        sb.append("\n");
        for (int i = 0; i < size; i++) {
            if (size - i <= 9)
                sb.append(" ");
            sb.append(size - i);

            ArrayList<IIntersection> line = boardMap.get(size - 1 - i);
            for (int j = 0; j < size; j++)
                sb.append(" ").append(line.get(j)).append(" ");

            if ((size - i) == 2) {
                sb.append("2     ");
                sb.append("WHITE (O) has captured ").append(capturedStonesBlack).append(" stones\n");
            } else if ((size - i) == 1) {
                sb.append("1     ");
                sb.append("BLACK (X) has captured ").append(capturedStonesWhite).append(" stones\n");
            } else
                sb.append(size - i).append("\n");
        }
        sb.append(" ");
        for (int i = 0; i < size; i++)
            sb.append("  ").append((char) (i + 65));
        sb.append("\n");
        return sb.toString();
    }


    private boolean isMoveValid(String color, int x, int y) {
        if(x < 0 || x > this.boardMap.size() || y < 0 || y > this.boardMap.size()){
            return false;
        }
        int cptOtherColor = 0;
        for(IIntersection i : getNeighborsIntersections(x, y)){
            if(!i.getColor().equals("nothing") && i.getColor().equals(getOppositeColor(color))){
                cptOtherColor++;
            }
            //suicide si les cases voisines sont prises
            //TODO : à améliorer
        }
        //TODO : compter les murs dans cptOtherColor
        if(cptOtherColor == NB_NEIGHBORS){
            return false;
        }
        return this.boardMap.get(y).get(x).isFree();
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
        int number = Integer.parseInt(move.substring(1)) - 1;

        if (isMoveValid(color, letter, number)){
            this.boardMap.get(number).set(letter, new Intersection(color));
            updateCaptures(color, letter, number);
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
        } while (!isMoveValid(color, letter, number));

        if (isMoveValid(color, letter, number)){
            this.boardMap.get(number).set(letter, new Intersection(color));
            updateCaptures(color, letter, number);
        }
        return ("=" + noCommand + " ");
    }

    public void updateScore() {
        int scoreBlack = 0; // TODO : Mettre dans le score des joueurs
        int scoreWhite = 0;
        // Compte le nombre d'intersections contrôlées (+1 point par pierres en jeu)
        for (ArrayList<IIntersection> intersectionsX : boardMap) {
            for (IIntersection intersectionsY : intersectionsX) {
                if (intersectionsY.getColor().equals(Color.black.toString()))
                    scoreBlack++;
                else if (intersectionsY.getColor().equals(Color.white.toString()))
                    scoreWhite++;
            }
        }
        // TODO : compter le nombre de pierres capturées (+1 par pierre)
        scoreBlack += capturedStonesBlack;
        scoreWhite += capturedStonesWhite; // TODO : ajouter ce score au joueur
    }

    public void updateCaptures(String Color, int letter, int number) {
        int nbLiberties = getNbLiberties(letter, number);
        if (nbLiberties < 1) {
            if (nbLiberties != -1) {
                boardMap.get(number).get(letter).remove();
                if (Color.equalsIgnoreCase("white"))
                    capturedStonesWhite++;
                else capturedStonesBlack++;
                updateScore();
            }
        }
    }

    public ArrayList<IIntersection> getNeighborsIntersections(int x, int y) {
        ArrayList<IIntersection> neighborsIntersections = new ArrayList<>();
        if (x - 1 >= 0)
            neighborsIntersections.add(this.boardMap.get(x - 1).get(y)); // Left
        if (x + 1 < boardMap.size())
            neighborsIntersections.add(this.boardMap.get(x + 1).get(y)); // Right
        if (y - 1 >= 0)
            neighborsIntersections.add(this.boardMap.get(x).get(y - 1)); // Up
        if (y + 1 < boardMap.size())
            neighborsIntersections.add(this.boardMap.get(x).get(y + 1)); // Down
        return neighborsIntersections;
    }

    public int getNbLiberties(int x, int y) {
        boolean[][] visited = new boolean[boardMap.size()][boardMap.get(0).size()];
        IIntersection currentIntersection = this.boardMap.get(y).get(x);
        String color = currentIntersection.getColor();
        if (currentIntersection.isFree()) return -1;
        return countLiberties(x, y, visited, color);
    }
    private int countLiberties(int x, int y, boolean[][] visited, String color) {
        if (!isValidCoordinate(x, y) || visited[x][y] || this.boardMap.get(x).get(y).getColor().equals((getOppositeColor(color))))
            return 0;
        visited[x][y] = true;

        int liberties = 0;
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // Down, Left, Up, Right

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            liberties = countLiberties(newX, newY, visited, color);
        }
        return liberties + 1;
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < boardMap.size() && y >= 0 && y < boardMap.get(0).size();
    }
}
