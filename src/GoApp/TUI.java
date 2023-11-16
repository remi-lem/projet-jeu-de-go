package GoApp;

import GoGame.*;
import Players.*;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class TUI {
    //public static int INIT_SIZE = 5;
    public static void main(String[] args) {
        Game game = null;
        Scanner sc = new Scanner(System.in);
        do {
            switch(sc.next()){
                case "boardsize":
                    int size = Integer.parseInt(sc.next());
                    game = new Game(new Board(size), new Player("Nose", NaturePlayer.Human), new Player("Foot", NaturePlayer.Robot));
                    break;
                case "showboard":
                    game.showBoard();
                    break;
                case "play":
                    break;

                case "clearboard":
                    break;

                case "genmove":
                    break;
                default:
                    System.out.println("commande non reconnue");
                    break;
            }
            sc.nextLine();
        }
        while (!sc.nextLine().equals("quit"));
    }
}