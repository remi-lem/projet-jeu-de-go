package GoApp;

import GoGame.Game;
import GoGame.Board;
import Players.Player;
import Players.NaturePlayer;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class TUI {
    public static void main(String[] args) throws RuntimeException {
        String[] commands;
        Game game = new Game(new Player("Nose", NaturePlayer.Human), new Player("Foot", NaturePlayer.Robot));
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print("> ");
            commands = sc.nextLine().split(" ");
            game.commandInterpreter(commands);
        }
        while (!commands[0].equals("quit"));
    }
}