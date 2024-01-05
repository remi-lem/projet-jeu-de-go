package app;

import game.Game;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TUI {
    public static final int MIN_LENGHT_ARGS = 1;
    public static final int MAX_LENGHT_ARGS = 3;
    public static void main(String[] args) throws RuntimeException, InterruptedException {
        if (args.length < MIN_LENGHT_ARGS || args.length > MAX_LENGHT_ARGS)
            System.err.println("Error: please run with good arguments");
        else {
            boolean errorArgs = false;
            Game game = new Game(Factory.createPlayer("human"), Factory.createPlayer("human"));
            game.setMode(args[0]);
            switch(args[0]) {
                case "gtp": break;
                case "direct":
                    game.setPlayerBlack(Factory.createPlayer(args[1]));
                    game.setPlayerWhite(Factory.createPlayer(args[2]));
                    break;
                default:
                    System.err.println("Mauvais arguments");
                    errorArgs = true;
            }
            if(!errorArgs) {
                if (game.isOnlyRobotPlay()) {
                    do {
                        System.out.println(game.onlyRobotPlay());
                        TimeUnit.SECONDS.sleep(3);
                    } while (game.isNotFinished());
                }
                else {
                    if (game.isRobotFirstPlayer()) System.out.println(game.robotPlay("black"));

                    String[] commands;
                    Scanner sc = new Scanner(System.in);

                    do {
                        commands = sc.nextLine().split(" ");
                        System.out.println(game.commandInterpreter(commands));
                    } while (game.isNotFinished());
                }
            }
        }

    }
}
