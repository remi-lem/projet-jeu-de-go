package GoApp;

import GoGame.Game;
import Players.NaturePlayer;
import Players.Player;

import java.util.Arrays;
import java.util.Scanner;

public class TUI {
    public static void main(String[] args) throws RuntimeException {
        String[] commands;
        Game game = new Game(new Player("Nose", NaturePlayer.Human), new Player("Foot", NaturePlayer.Robot));
        Scanner sc = new Scanner(System.in);
        do {
            commands = sc.nextLine().split(" ");
            game.commandInterpreter(commands);
        } while (!(commands[0].equals("quit") || commands[0].equals("q")));
    }
}