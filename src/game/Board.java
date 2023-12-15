package game;

import intersection.Intersection;
import java.util.ArrayList;
import intersection.Intersection.Color;

public class Board {
    private ArrayList<ArrayList<IIntersection>> boardMap;
    //TODO : éventuellement passer sur un tableau 2D

    public static final int NB_NEIGHBORS = 4;

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
                sb.append("BLACK (X) has captured ").append(capturedStonesBlack).append(" stones\n");
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
        if(x < 0 || x > this.boardMap.size() || y < 0 || y > this.boardMap.size()){
            return false;
        }
        int cptOtherColor = 0;
        for(IIntersection i : getNeighborsIntersections(x, y)){
            if(i.getColor().equals(getOppositeColor(color))){
                cptOtherColor++;
            }
            //suicide si les cases voisines sont prises
            //TODO : à améliorer
        }
        if(cptOtherColor == NB_NEIGHBORS){
            return false;
        }
        return this.boardMap.get(x).get(y).isFree();
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
        } while (!isMoveValid(color, number, letter));

        if (isMoveValid(color, number, letter)){
            this.boardMap.get(number).set(letter, new Intersection(color));
            updateCaptures(color, letter, number);
        }
        return ("=" + noCommand + " ");
    }

    public void updateScore() {
        int scoreBlack = 0; // TODO : Mettre dans le score des joueurs
        int scoreWhite = 0;
        // TODO : compter le nombre d'intersections contrôlées (+1 point par pierres en jeu)
        int size = boardMap.size();
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
                boardMap.get(letter).get(number).remove();
                if (Color.equals("WHITE"))
                    capturedStonesWhite++;
                else capturedStonesBlack++;
                updateScore();
            }
        }
    }

    public ArrayList<IIntersection> getNeighborsIntersections(int x, int y) {
        ArrayList<IIntersection> neighborsIntersections = new ArrayList<>();
        if (x - 1 > 0)
            neighborsIntersections.add(this.boardMap.get(x - 1).get(y)); // Left
        if (x + 1 <= boardMap.size())
            neighborsIntersections.add(this.boardMap.get(x + 1).get(y)); // Right
        if (y - 1 > 0)
            neighborsIntersections.add(this.boardMap.get(x).get(y - 1)); // Up
        if (y + 1 <= boardMap.size())
            neighborsIntersections.add(this.boardMap.get(x).get(y + 1)); // Down
        return neighborsIntersections;
    }

    public int getNbLiberties(int x, int y) {
        ArrayList<IIntersection> toVisit = new ArrayList<>(); // TODO : gérer les intersections visitées
        IIntersection currentIntersection = this.boardMap.get(x).get(y);
        String color = currentIntersection.getColor();
        if (currentIntersection.isFree()) return -1;
        int nbLiberties = 0;
        for (IIntersection intersection : getNeighborsIntersections(x, y)) {
            if (intersection.isFree()) nbLiberties++;
            else if (intersection.getColor().equals(color))
                toVisit.add(intersection);
                nbLiberties += getNbLibertiesAnnex(toVisit);
        }
        return nbLiberties;
    }
    public int getNbLibertiesAnnex(ArrayList<IIntersection> toVisit) {
        if (toVisit.isEmpty()) return 0;
        return -1;//TODO
    }
    /*
    public Set<Intersection> getGroupe(Plateau plateau) {
        Set<Intersection> groupe = new HashSet<>();
        Set<Intersection> dejaVisite = new HashSet<>();

        // Utiliser une recherche en profondeur (DFS) pour trouver le groupe
        dfs(plateau, this, groupe, dejaVisite);

        return groupe;
    }

    private void dfs(Plateau plateau, Pierre pierre, Set<Intersection> groupe, Set<Intersection> dejaVisite) {
        Intersection intersection = new Intersection(pierre.x, pierre.y);

        // Vérifier si l'intersection a déjà été visitée
        if (dejaVisite.contains(intersection)) {
            return;
        }

        // Ajouter l'intersection au groupe
        groupe.add(intersection);
        dejaVisite.add(intersection);

        // Vérifier les intersections voisines
        for (Pierre voisin : plateau.getVoisins(pierre)) {
            dfs(plateau, voisin, groupe, dejaVisite);
        }
    }
     */
}
