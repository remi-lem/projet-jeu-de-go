package src.GoApp;

import src.GoGame.Board;
import src.GoGame.Game;
import src.Players.NaturePlayer;
import src.Players.Player;

import java.util.Scanner;

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
                        String size_c = commands[1];
                        int size = Integer.parseInt(size_c);
                        game = new Game(new Board(size), new Player("Nose", NaturePlayer.Human), new Player("Foot", NaturePlayer.Robot));
                        System.out.println("Board Initialisé");
                    } catch(NumberFormatException e) {
                        System.out.println("You have to put a valid number");
                    } catch(ArrayIndexOutOfBoundsException e) {
                        System.out.println("There is no number");
                    }
                    break;
                case "showboard":
                    if(game != null){
                        game.showBoard();
                    }
                    else {
                        System.out.println("Board not initialized");
                    }
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