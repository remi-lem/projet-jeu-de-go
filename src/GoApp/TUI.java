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
            commands = sc.nextLine().split(" ");
            switch(commands[0]) {
                case "boardsize":
                    try {
                        int size = Integer.parseInt(commands[1]);
                        game = new Game(new Board(size), new Player("Nose", NaturePlayer.Human), new Player("Foot", NaturePlayer.Robot));
                        System.out.println("Board initialized"); //TODO: enlever les messages
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