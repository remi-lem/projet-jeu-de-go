package GoGame;

import Stones.Color;

import java.util.Arrays;

public class Game {
    private Board board;
    private final IPlayer playerBlack;
    private final IPlayer playerWhite;
    private final int BOARD_SIZE_MAX = 19;
    private final int BOARD_SIZE_MIN = 1;

    public Game(IPlayer playerBlack, IPlayer playerWhite) {
        this.playerBlack = playerBlack;
        this.playerWhite = playerWhite;
        this.board = new Board(BOARD_SIZE_MAX);
    }

    public void commandInterpreter(String[] command) throws RuntimeException {
        String noCommand = "";

        try{
            Integer.parseInt(command[0]);
            noCommand = command[0];
            command = Arrays.copyOfRange(command, 1, command.length);
        } catch(NumberFormatException e) {

        }

        switch(command[0]) {
            case "boardsize", "b":
                boardsize(command, noCommand);
                break;
            case "showboard", "s":
                showBoard(noCommand);
                break;
            case "play", "p":
                playMove(command, noCommand);
                break;
            case "clear_board", "c":
                clearBoard(noCommand);
                break;
            case "genmove", "g":
                genMove(command, noCommand);
                break;
            case "quit", "q":
                return;
            default:
                System.err.println("commande non reconnue");
                break;
        }
    }

    private void printCommandGTP(String command) {
        System.out.println("=" + command + "\n");
    }

    private void boardsize(String[] command, String noCommand) {
        try {
            int size = Integer.parseInt(command[1]);
            if ((size > BOARD_SIZE_MIN - 1) && (size < BOARD_SIZE_MAX + 1)) {
                setSizeBoard(Integer.parseInt(command[1]));
                printCommandGTP(noCommand);
            }
            else throw new RuntimeException("wrong number");
        } catch(RuntimeException e) {
            System.err.println("?" + noCommand + " unacceptable size\n");
        }
    }

    private void setSizeBoard(int sizeBoard) {
        board = new Board(sizeBoard);
    }

    public void showBoard(String noCommand) {
        printCommandGTP(noCommand);
        this.board.show();
    }

    public void clearBoard(String noCommand) {
        printCommandGTP(noCommand);
        this.board.clear();
    }


    public void playMove(String[] command, String noCommand) {
        try {
            if (command[1].equalsIgnoreCase("black") || command[1].equalsIgnoreCase("white")) {
                this.board.makeMove(Color.valueOf(command[1].toUpperCase()), command[2].toUpperCase());
                System.out.println("=" + noCommand + "\n");
                this.board.show();
            }
            else throw new RuntimeException("syntax error");
        }
        catch (RuntimeException e){
            System.err.println("?" + noCommand + " illegal move\n");
        }
    }

    private void genMove(String[] command, String noCommand) {
        try {
            if (command[1].equalsIgnoreCase("BLACK") || command[1].equalsIgnoreCase("WHITE")) {
                this.board.makeRndMove(Color.valueOf(command[1].toLowerCase()));
                System.out.println("=" + noCommand + "\n");
                this.board.show();
            }
            else throw new RuntimeException("syntax error");
        }
        catch (RuntimeException e){
            System.err.println("?" + noCommand + " illegal move\n");
        }
    }
}
