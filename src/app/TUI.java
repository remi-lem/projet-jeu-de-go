package app;

import game.Game;
import players.*;

import java.util.Scanner;

public class TUI {
    public static final int MIN_LENGHT_ARGS = 1;
    public static final int MAX_LENGHT_ARGS = 3;
    public static void main(String[] args) throws RuntimeException {
        if(args.length < MIN_LENGHT_ARGS || args.length > MAX_LENGHT_ARGS){
            System.err.println("Erreur : merci de lancer le programme avec les bons arguments");
        }
        else{
            boolean errorArgs = false;
            Game game = new Game(new Human(), new Human());
            switch(args[0]) {
                case "gtp": break;
                case "direct":
                    if(args[1].equals("robot")){
                        game.setPlayerBlack(new Robot()); //TODO : mettre un pattern design (supp liens)
                    }
                    if(args[2].equals("robot")){
                        game.setPlayerWhite(new Robot());
                    }
                    break;
                default:
                    System.err.println("Mauvais arguments");
                    errorArgs = true;
            }
            if(!errorArgs){
                String[] commands;
                Scanner sc = new Scanner(System.in);
                do {
                    commands = sc.nextLine().split(" ");
                    System.out.println(game.commandInterpreter(commands));
                } while (!(commands[0].equals("quit") || commands[0].equals("q")));
            }
        }

    }
}
