package GoApp;

import GoGame.Game;
import GoGame.Board;
import Players.Player;
import Players.NaturePlayer;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class TUI {
    public static void main(String[] args) {
        String[] commands;
        Game game = null;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print("> ");
            String input = sc.nextLine();
            commands = input.split(" ");
            switch(commands[0]) {
                case "boardsize":
                    try {
                        //voir si commands[1] != null
                        int size = Integer.parseInt(commands[1]);
                        game = new Game(new Board(size), new Player("Nose", NaturePlayer.Human), new Player("Foot", NaturePlayer.Robot));
                        System.out.println("Board Initialisé");
                    } catch(NumberFormatException e) {
                        System.err.println("Cette valeur n'est pas un chiffre, essaie encore !");
                    }
                    break;
                case "showboard":
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
        }
        while (!commands[0].equals("quit"));
    }
}