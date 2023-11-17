package GoApp;


import GoGame.Game;
import GoGame.Board;
import Players.Player;
import Players.NaturePlayer;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class TUI {
    public static void main(String[] args) throws Exception {
        String[] commands;
        Game game = null;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print("> ");
            commands = sc.nextLine().split(" ");
            game.commandInterpreter(commands); // TODO : Game n'existe pas au début
        }
        while (!commands[0].equals("quit"));
    }
}