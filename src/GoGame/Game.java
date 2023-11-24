package GoGame;

import Stones.Color;

public class Game {
    private Board board;
    private IPlayer playerBlack;
    private IPlayer playerWhite;

    public Game(IPlayer playerBlack, IPlayer playerWhite) {
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
                testInitBoard();
                showBoard();
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

    public void showBoard() {
        this.board.show();
    }

    public void clearBoard() {
        this.board.clear();
    }

    public void playMove(String playerColor, String coordinate) {
        this.board.makeMove(playerColor.toUpperCase(), coordinate.toUpperCase());
    }

    public void testInitBoard() throws RuntimeException {
        if (board == null) {
            throw new RuntimeException("Board not initialised");
        }
    }

    public Board getBoard() {
        return board;
    }
}
