package game;

import players.Human;
import players.Robot;

import java.util.Arrays;

public class Game {
    private Board board;
    private IPlayer playerBlack;
    private IPlayer playerWhite;
    private final int BOARD_SIZE_MAX = 19;
    private final int BOARD_SIZE_MIN = 1;

    public Game(IPlayer playerBlack, IPlayer playerWhite) {
        this.playerBlack = playerBlack;
        this.playerWhite = playerWhite;
        this.board = new Board(BOARD_SIZE_MAX);
    }
    public void setPlayerBlack(IPlayer player) {
        this.playerBlack = player;
    }
    public void setPlayerWhite(IPlayer player) {
        this.playerWhite = player;
    }

    public String commandInterpreter(String[] command) throws RuntimeException {
        String noCommand = "";
        String ret = "";
        try {
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
            case "final_score", "f":
                ret = scoring();
                break;
            case "player":
                ret = changePlayers(command, noCommand);
                break;
            case "quit", "q":
                break;
            default:
                ret = "unrecognized command";
                break;
        }
        return ret;
    }

    private String changePlayers(String[] command, String noCommand) {
        String color = command[1];
        String type = command[2];
        if(color.equals("black")){
            if(type.equals("random")){
                int tmpScore = this.playerBlack.getScore();
                this.playerBlack = new Robot("black");
                this.playerBlack.setScore(tmpScore);
            }
            else if(type.equals("human")){
                int tmpScore = this.playerBlack.getScore();
                this.playerBlack = new Human("black");
                this.playerBlack.setScore(tmpScore);
            }
            else {
                return "?" + noCommand + " unrecognised player type\n";
            }
        }
        else if(color.equals("white")){
            if(type.equals("random")){
                int tmpScore = this.playerWhite.getScore();
                this.playerWhite = new Robot("black");
                this.playerWhite.setScore(tmpScore);
            }
            else if(type.equals("human")){
                int tmpScore = this.playerWhite.getScore();
                this.playerWhite = new Human("black");
                this.playerWhite.setScore(tmpScore);
            }
            else {
                return "?" + noCommand + " unrecognised player type\n";
            }
        }
        else {
            return "?" + noCommand + " unrecognised color\n";
        }
        return "=" + noCommand + " player modified\n";
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
                ret.append(this.board.makeMove(command[1].toLowerCase(), command[2].toUpperCase(), noCommand));
                ret.append("\n").append(this.board.toString());
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
                ret.append(this.board.makeRndMove(command[1].toLowerCase(), noCommand)).append("\n");
                ret.append(this.board.toString());
            }
            else throw new RuntimeException("syntax error");
        }
        catch (RuntimeException e){
            ret.append("?").append(noCommand).append(" illegal move\n");
        }
        return ret.toString();
    }

    private String scoring() {
        return "WHITE : " + playerWhite.getScore() + " points" +
                "\nBLACK : " + playerBlack.getScore() + " points";
    }
}
