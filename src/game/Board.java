package game;

import intersection.Intersection;
import java.util.ArrayList;

import intersection.Intersection.Color;

public class Board {
    private ArrayList<ArrayList<IIntersection>> boardMap;
    public static int capturedStonesWhite = 0, capturedStonesBlack = 0;
    public static int numStonesWhite, numStonesBlack;

    public Board(int size) {
        initialize(size);
        setNumStones();
    }

    public Board(int size, String move){
        initialize(size);
        initializeSGF(move);
        setNumStones();
    }

    public int getSize(){
        return this.boardMap.size();
    }

    public void setNumStones() {
        numStonesWhite = numStonesBlack = (int) (Math.pow(getSize(),2)/2);
    }

    public void playStone(String color) {
        if (color.equalsIgnoreCase("black")) numStonesBlack--;
        else numStonesWhite--;
    }

    private void initializeSGF(String move) {
        String[] listMove = move.split(" ");
        int i = 0;
        for (String m : listMove) {
            if (i%2 == 0) makeMove("black", m);
            else makeMove("white", m);
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

    public void play(char color, int i, int j) {
        makeMove(color == 'b' ? "black" : "white",(i+1)+""+(j+1));
}

    public void makeMove(String color, String move) throws RuntimeException {
        if(isStonesEmpty(color)) throw new RuntimeException("not enough stones");

        int letter, number;

        if(Character.isDigit(move.charAt(0))) letter = Integer.parseInt(move.substring(0,1)) - 1;
        else letter = Character.toUpperCase(move.charAt(0)) - 'A';

        if(Character.isDigit(move.charAt(1))) number = Integer.parseInt(move.substring(1)) - 1;
        else number = Character.toUpperCase(move.charAt(1)) - 'A';

        if (isMoveValid(letter, number)){
            this.boardMap.get(number).set(letter, new Intersection(color));
            updateCaptures(color, letter, number);
            playStone(color);
        }
        else throw new IllegalArgumentException("illegal move");
    }

    public String makeRndMove(String color) throws RuntimeException {
        if(isStonesEmpty(color)) throw new RuntimeException("not enough stones");

        int letter, number, maxNum = (int) Math.pow(this.boardMap.size(), 2);

        do {
            letter = (int) (Math.random() * this.boardMap.size());
            number = (int) (Math.random() * this.boardMap.size());

            if (maxNum-- == 0) throw new RuntimeException("pass");
        } while (!isMoveValid(letter, number));

        if (isMoveValid(letter, number)) {
            this.boardMap.get(number).set(letter, new Intersection(color));
            updateCaptures(color, letter, number);
            playStone(color);
        }
        return ((char)(letter+65) + "" + (number+1));
    }

    private boolean isMoveValid(int x, int y) {
        if(x < 0 || x >= this.boardMap.size() || y < 0 || y >= this.boardMap.size()) return false;
        if(getNbLiberties(x, y) < 1) return false; //TODO: Check if the stone capture
        return this.boardMap.get(y).get(x).isFree();
    }
    private boolean isStonesEmpty(String color) {
        return (color.equalsIgnoreCase("black") ? numStonesBlack : numStonesWhite) <= 0;
    }

    private String getOppositeColor(String color) {
        if(color.equals(Intersection.Color.black.toString())) return Intersection.Color.white.toString();
        return Intersection.Color.black.toString();
    }

    public ArrayList<IIntersection> getNeighborsIntersections(int x, int y) {
        ArrayList<IIntersection> neighborsIntersections = new ArrayList<>();
        if (y - 1 >= 0)
            neighborsIntersections.add(this.boardMap.get(y - 1).get(x)); // Left
        if (y + 1 < boardMap.size())
            neighborsIntersections.add(this.boardMap.get(y + 1).get(x)); // Right
        if (x - 1 >= 0)
            neighborsIntersections.add(this.boardMap.get(y).get(x - 1)); // Down
        if (x + 1 < boardMap.size())
            neighborsIntersections.add(this.boardMap.get(y).get(x + 1)); // Up
        return neighborsIntersections;
    }

    public void updateCaptures(String color, int letter, int number) {
        int nbLiberties = getNbLibertiesGroup(letter, number);
        if (nbLiberties < 1)
            if (nbLiberties != -1) {
                boardMap.get(number).get(letter).remove();
                if (color.equalsIgnoreCase("white"))
                    capturedStonesWhite++;
                else capturedStonesBlack++;
            }
    }

    public int getNbLiberties(int x, int y) {
        int nbLiberties = 0;

        for(IIntersection i : getNeighborsIntersections(x, y))
            if (i.getColor().equals("nothing"))
                nbLiberties++;
        return nbLiberties;
    }

    public int getNbLibertiesGroup(int x, int y) {
        IIntersection currentIntersection = this.boardMap.get(y).get(x);
        String color = currentIntersection.getColor();

        if (currentIntersection.isFree()) return -1;
        return verifyNeighborsLiberties(x, y, color);
    }

    private int verifyNeighborsLiberties(int coordLetter, int coordNumber, String color) {
        int size = boardMap.size();
        boolean[][] visited = new boolean[size][size];
        int[][] neighbors = {{-1,0}, {0,1}, {1,0}, {0,-1}}; // Up, Right, Down, Left

        for (int[] neighbor : neighbors) {
            int coordLetNeighbor = coordLetter + neighbor[0];
            int coordNumNeighbor = coordNumber + neighbor[1];

            if(coordNumNeighbor >= size
                    || coordNumNeighbor < 0
                    || coordLetNeighbor >= size
                    || coordLetNeighbor < 0) continue; // Hors limites

            if(this.boardMap.get(coordNumNeighbor).get(coordLetNeighbor).getColor().equals(getOppositeColor(color)))
                // Verify if this neighbor was captured
                verifyMyLiberties(coordNumNeighbor, coordLetNeighbor, getOppositeColor(color), visited, true);
        }
        return 1;
    }

    private boolean verifyMyLiberties(int coordNumber, int coordLetter,
                                      String color, boolean[][] visitedMap, boolean isFirstCalled) {
        if (visitedMap[coordLetter][coordNumber]) return false; // I'm surrounded, but I have at least a friend near me
        else visitedMap[coordLetter][coordNumber] = true; // We will find if I'm still fine

        int liberties = 4;
        int size = boardMap.size();
        int[][] neighbors = {{-1,0}, {0,1}, {1,0}, {0,-1}}; // Up, Right, Down, Left
        ArrayList<int[]> sameColNeighbors = new ArrayList<>();

        for (int[] neighbor : neighbors) {
            int coordLetNeighbor = coordLetter + neighbor[0];
            int coordNumNeighbor = coordNumber + neighbor[1];
            if (coordNumNeighbor >= size || coordNumNeighbor < 0
                    || coordLetNeighbor >= size || coordLetNeighbor < 0) { // Off limits
                liberties--;
                continue;
            }
            if (this.boardMap.get(coordNumNeighbor).get(coordLetNeighbor).getColor().equals(getOppositeColor(color)))
                liberties--;
            else if (this.boardMap.get(coordNumNeighbor).get(coordLetNeighbor).getColor().equals(color)) {
                liberties--;
                sameColNeighbors.add(new int[] {coordNumNeighbor, coordLetNeighbor});
            }
        }

        if (liberties == 0 && sameColNeighbors.isEmpty()) {
            removeStone(coordLetter, coordNumber, color);
            return false;
        } else if (liberties == 0) {
            // Some neighbors are there to support the current stone, let see if they are all captured
            for (int[] neighbor : sameColNeighbors)
                if (verifyMyLiberties(neighbor[0], neighbor[1], color, visitedMap, false))
                   return true;
            if (isFirstCalled)
                for (int i = 0; i < size; i++) for (int j = 0; j < size; j++)
                        if (visitedMap[i][j]) removeStone(i, j, color);
            return false;
        } else return true;
    }

    public void removeStone(int letter, int number, String color) {
        boardMap.get(number).get(letter).remove();
        if (color.equalsIgnoreCase("white")) capturedStonesWhite++;
        else capturedStonesBlack++;
    }


    public String finalScore(IPlayer pBlack, IPlayer pWhite) {
        int scoreBlack = 0, scoreWhite = 0;

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
        pBlack.setScore(scoreBlack+capturedStonesWhite);
        pWhite.setScore(scoreWhite+capturedStonesBlack);

        return "WHITE : " + pWhite.getScore() + " points" +
                "\nBLACK : " + pBlack.getScore() + " points";
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
}
