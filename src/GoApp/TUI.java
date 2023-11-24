package GoApp;

import GoGame.Game;
import Players.*;

import java.util.Scanner;

public class TUI {
    public static void main(String[] args) throws RuntimeException {
        String[] commands;
        Game game = new Game(new Human("black"), new Human("white")); // TODO : faire une fabrique
        Scanner sc = new Scanner(System.in);
        do {
            commands = sc.nextLine().split(" ");
            game.commandInterpreter(commands);
        } while (!(commands[0].equals("quit") || commands[0].equals("q")));
    }
}
