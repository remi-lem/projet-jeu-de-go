package GoGame;

import Players.NaturePlayer;
import Players.Player;

public class Game {
    private Board board;
    private Player playerBlack;
    private Player playerWhite;
    private final int BOARD_SIZE_MAX = 19;
    private final int BOARD_SIZE_MIN = 1;


    public Game(Player playerBlack, Player playerWhite) {
        this.playerBlack = playerBlack;
        this.playerWhite = playerWhite;
    }

    public void commandInterpreter(String[] command) throws RuntimeException {
        switch(command[0]) {
            case "boardsize", "b":
                try {
                    setSizeBoard(Integer.parseInt(command[1]));
                    System.out.println("Board initialized"); //TODO: enlever les messages
                } catch(NumberFormatException e) {
                    System.out.println("You have to put a valid number");
                } catch(ArrayIndexOutOfBoundsException e) {
                    System.out.println("There is no number");
                }
                break;
            case "showboard", "s":
                showBoard(noCommand);
                break;
            case "play", "p":
                testInitBoard();
                playMove(command[1], command[2]);
                showBoard();
                break;
            case "clearboard", "c":
                testInitBoard();
                clearBoard();
                showBoard();
                break;
            case "genmove", "g":
                break;
            case "quit", "q":
                return;
            default:
                System.err.println("commande non reconnue");
                break;
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

    public void playMove(String playerColor, String coordinate) {
        this.board.makeMove(playerColor.toUpperCase(), coordinate.toUpperCase());
    }

    public void testInitBoard() throws RuntimeException {
        if (board == null) {
            throw new RuntimeException("Board not initalized");
        }
    }

    public Board getBoard() {
        return board;
    }
}
