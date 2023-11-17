package GoGame;

import Players.NaturePlayer;
import Players.Player;

public class Game {
    private Board board;
    private Player playerBlack;
    private Player playerWhite;

    public Game(Player playerBlack, Player playerWhite) {
        this.playerBlack = playerBlack;
        this.playerWhite = playerWhite;
    }

    public void commandInterpreter(String[] command) throws RuntimeException {
        switch(command[0]) {
            case "boardsize":
                try {
                    setSizeBoard(Integer.parseInt(command[1]));
                    System.out.println("Board initialized"); //TODO: enlever les messages
                } catch(NumberFormatException e) {
                    System.out.println("You have to put a valid number");
                } catch(ArrayIndexOutOfBoundsException e) {
                    System.out.println("There is no number");
                }
                break;
            case "showboard":
                testInitBoard();
                showBoard();
                break;
            case "play":
                testInitBoard();
                playMove(command[1], command[2]);
            case "clearboard":
                testInitBoard();
                clearBoard();
                break;
            case "genmove":
                break;
            case "quit":
                return;
            default:
                System.err.println("commande non reconnue");
                break;
        }
    }

    private void setSizeBoard(int sizeBoard) {
        board = new Board(sizeBoard);
    }

    public void showBoard() {
        testInitBoard();
        this.board.show();
    }

    public void clearBoard() {
        testInitBoard();
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
}
