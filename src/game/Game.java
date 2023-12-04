package game;

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

    public String commandInterpreter(String[] command) throws RuntimeException {
        String noCommand = "";
        String ret = "";
        try{
            Integer.parseInt(command[0]);
            noCommand = command[0];
            command = Arrays.copyOfRange(command, 1, command.length);
        } catch(NumberFormatException e) {

        }

        switch(command[0]) {
            case "boardsize", "b":
                ret = boardsize(command, noCommand);
                break;
            case "showboard", "s":
                ret = showBoard(noCommand);
                break;
            case "play", "p":
                ret = playMove(command, noCommand);
                break;
            case "clear_board", "c":
                ret = clearBoard(noCommand);
                break;
            case "genmove", "g":
                ret = genMove(command, noCommand);
                break;
            case "quit", "q":
                break;
            default:
                ret = "unrecognized command";
                break;
        }
        return ret;
    }

    private String commandGTP(String command) {
        return "=" + command + "\n";
    }

    private String boardsize(String[] command, String noCommand) {
        String ret;
        try {
            int size = Integer.parseInt(command[1]);
            if ((size > BOARD_SIZE_MIN - 1) && (size < BOARD_SIZE_MAX + 1)) {
                setSizeBoard(Integer.parseInt(command[1]));
                ret = commandGTP(noCommand);
            }
            else throw new RuntimeException();
        } catch(RuntimeException e) {
            ret = "?" + noCommand + " unacceptable size\n";
        }
        return ret;
    }

    private void setSizeBoard(int sizeBoard) {
        board = new Board(sizeBoard);
    }

    public String showBoard(String noCommand) {
        return commandGTP(noCommand) + this.board.toString();
    }

    public String clearBoard(String noCommand) {
        this.board.clear();
        return commandGTP(noCommand);
    }


    public String playMove(String[] command, String noCommand) {
        StringBuilder ret = new StringBuilder();
        try {

            if (command[1].equalsIgnoreCase("black") || command[1].equalsIgnoreCase("white")) {
                this.board.makeMove(command[1].toUpperCase(), command[2].toUpperCase(), noCommand);
                ret.append("=").append(noCommand).append("\n").append(this.board.toString());
            }
            else throw new RuntimeException("syntax error");
        }
        catch (RuntimeException e) {
            ret.append("?").append(noCommand).append(" illegal move\n");
        }
        return ret.toString();
    }

    private String genMove(String[] command, String noCommand) {
        StringBuilder ret = new StringBuilder();
        try {
            if (command[1].equalsIgnoreCase("BLACK") || command[1].equalsIgnoreCase("WHITE")) {
                this.board.makeRndMove(command[1].toLowerCase(), noCommand);
                ret.append("=").append(noCommand).append("\n");
                ret.append(this.board.toString());
            }
            else throw new RuntimeException("syntax error");
        }
        catch (RuntimeException e){
            ret.append("?").append(noCommand).append(" illegal move\n");
        }
        return ret.toString();
    }
}
