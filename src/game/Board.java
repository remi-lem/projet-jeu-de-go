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

    public Board(int size, String move){
        initialize(size);
        initializeSGF(move);}

    private void initializeSGF(String move) {
        String[] listMove = move.split(" ");
        int i = 0;
        for (String m : listMove) {
            if (i%2 == 0) makeMove("black", m,"");
            else makeMove("white", m,"");
            i++;
        }
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
        if(x < 0 || x > this.boardMap.size() || y < 0 || y > this.boardMap.size()) return false;

        int cptOtherColor = 0;
        int nbIntersections = 0;

        for(IIntersection i : getNeighborsIntersections(x, y)) {
            if(!i.getColor().equals("nothing") && i.getColor().equals(getOppositeColor(color)))
                cptOtherColor++;
            nbIntersections++;
        }

        if(((NB_NEIGHBORS - nbIntersections) + cptOtherColor) == NB_NEIGHBORS) return false;
        return this.boardMap.get(y).get(x).isFree();
    }

    private String getOppositeColor(String color) {
        if(color.equals(Intersection.Color.black.toString())) return Intersection.Color.white.toString();
        return Intersection.Color.black.toString();
    }

    public void play(int i, int j) {
        makeMove("black",i+""+j, "");
    }

    public String makeMove(String color, String move, String noCommand) {
        int letter, number;

        if(Character.isDigit(move.charAt(0))) {
            letter = Integer.parseInt(move.substring(0,1)) - 1;
        } else {
            letter = Character.toUpperCase(move.charAt(0)) - 'A';
        }

        if(Character.isDigit(move.charAt(1))) {
            number = Integer.parseInt(move.substring(1)) - 1;
        } else {
            number = Character.toUpperCase(move.charAt(1)) - 'A';
        }

        if (isMoveValid(color, letter, number)){
            this.boardMap.get(number).set(letter, new Intersection(color));
            updateCaptures(color, letter, number);
            return ("=" + noCommand + " ");
        }
        return ("?" + noCommand + " illegal move\n\n");
    }

    public String makeRndMove(String color, String noCommand) {
        int letter, number, numTest = 0;

        do {
            letter = (int) (Math.random() * boardMap.size());
            number = (int) (Math.random() * boardMap.size());
            numTest++;
            if(numTest > Math.pow(boardMap.size(), 2)) return ("?" + noCommand + " illegal move");
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
        // Compte le nombre de pierres capturées (+1 par pierre)
        scoreBlack += capturedStonesBlack;
        scoreWhite += capturedStonesWhite;
    }

    public void updateCaptures(String color, int letter, int number) { // TODO : Appliquer au groupe
        int nbLiberties = getNbLiberties(letter, number);
        if (nbLiberties < 1)
            if (nbLiberties != -1) {
                boardMap.get(number).get(letter).remove();
                if (color.equalsIgnoreCase("white"))
                    capturedStonesWhite++;
                else capturedStonesBlack++;
                updateScore();
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
        boolean[][] visited = new boolean[boardMap.size()][boardMap.size()];
        IIntersection currentIntersection = this.boardMap.get(y).get(x);
        String color = currentIntersection.getColor();

        if (currentIntersection.isFree()) return -1;
        return countLiberties(x, y, visited, color);
    }
    private int countLiberties(int x, int y, boolean[][] visited, String color) { //TODO : à développer
        return 4;
    }


    private int verifyNeighborsLiberties(int coordNumber, int coordLetter, String color) {
        int size = boardMap.size();
        int[][] neighbors = {{-1,0}, {0,1}, {1,0}, {0,-1}}; // Up, Right, Down, Left
        for (int[] neighbor : neighbors) {
            int coordNumNeighbor = coordNumber + neighbor[0];
            int coordLetNeighbor = coordLetter + neighbor[1];
            if(coordNumNeighbor > size
                    || coordNumNeighbor < 0
                    || coordLetNeighbor > size
                    || coordLetNeighbor < 0) continue; // Hors limites
            if(this.boardMap.get(coordNumNeighbor).get(coordLetNeighbor).getColor().equals(getOppositeColor(color))) {
                    // Verify if this neighbor was captured

                break;
            }
        }
        return 0;
    }

    private int verifyMyLiberties(int coordNumber, int coordLetter, String color) {
        int liberties = 4;
        int size = boardMap.size();
        int[][] neighbors = {{-1,0}, {0,1}, {1,0}, {0,-1}}; // Up, Right, Down, Left
        ArrayList<int[]> sameColNeighbors = new ArrayList<>();
        for (int[] neighbor : neighbors) {
            int coordNumNeighbor = coordNumber + neighbor[0];
            int coordLetNeighbor = coordLetter + neighbor[1];
            if (coordNumNeighbor > size
                    || coordNumNeighbor < 0
                    || coordLetNeighbor > size
                    || coordLetNeighbor < 0) continue; // Off limits
            if (this.boardMap.get(coordNumNeighbor).get(coordLetNeighbor).getColor().equals(getOppositeColor(color))) {
                liberties--;
            } else if (this.boardMap.get(coordNumNeighbor).get(coordLetNeighbor).getColor().equals(color)) {
                liberties--;
                sameColNeighbors.add(new int[] {coordNumNeighbor, coordLetNeighbor});
            }
        }
        if (liberties == 0 && sameColNeighbors.isEmpty()) {
            // Remove captured stone
            boardMap.get(coordNumber).get(coordLetter).remove();
            if (color.equalsIgnoreCase("white")) capturedStonesWhite++;
            else capturedStonesBlack++;
            updateScore();
        } else if (liberties == 0 && !sameColNeighbors.isEmpty()) {
            // Some neighbors are there to support the current stone, let see if they are all captured
            for (int[] neighbor : sameColNeighbors) {
                //TODO : verify if neighbors has no liberties and be aware of the stones already visited
            }
        }
        return 0;
    }
}
