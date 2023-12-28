package game;

import app.Factory;

import java.util.Arrays;

public class Game {
    private Board board;
    private IPlayer playerBlack;
    private IPlayer playerWhite;
    private final int BOARD_SIZE_MAX = 19;
    private final int BOARD_SIZE_MIN = 1;
    private int cptSkip = 0;
    private boolean isFinished = false;
    private String mode = "gtp";

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

    public boolean isNotFinished() {
        return !isFinished;
    }

    public void setMode(String mode) {
        this.mode = mode.equals("direct") ? "direct" : "gtp";
    }

    public String commandInterpreter(String[] command) throws RuntimeException {
        String noCommand = "";
        String ret = "";
        try {
            Integer.parseInt(command[0]);
            noCommand = command[0];
            command = Arrays.copyOfRange(command, 1, command.length);
        } catch(NumberFormatException ignored) {

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
            case "player", "pr":
                ret = changeTypePlayers(command, noCommand);
                break;
            case "set_handicaps", "sh":
                ret = setHandicaps(command, noCommand);
                break;
            case "quit", "q":
                isFinished = true;
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

    private String showBoard(String noCommand) {
        return commandGTP(noCommand) + this.board.toString();
    }

    private String clearBoard(String noCommand) {
        this.board.clear();
        return commandGTP(noCommand);
    }

    private String playMove(String[] command, String noCommand) {
        StringBuilder ret = new StringBuilder();
        IPlayer currentPlayer = command[1].equalsIgnoreCase("black") ? playerBlack : playerWhite;
        if (mode.equals("direct") && currentPlayer.canPlayConsole()) {
            String passStr = "=" + noCommand + " pass\n";
            if (command[2].equals("pass")) {
                ++cptSkip;
                if (cptSkip == 2) {
                    isFinished = true;
                    return ret.append(passStr).append("Game finished!").toString();
                }
            } else {
                cptSkip = 0;
                try {
                    if (command[1].equalsIgnoreCase("black")
                            || command[1].equalsIgnoreCase("white")) {
                        ret.append(this.board.makeMove(command[1].toLowerCase(), command[2].toUpperCase(), noCommand));
                        ret.append("\n").append(this.board.toString());

                        IPlayer nextPlayer = currentPlayer.equals(playerBlack) ? playerWhite : playerBlack;
                        if (!nextPlayer.canPlayConsole()) robotPlay(nextPlayer.equals(playerBlack) ? "black" : "white");
                    } else throw new RuntimeException("syntax error");
                } catch (RuntimeException e) {
                    ret.append("?").append(noCommand).append(" illegal move\n");
                }
                return ret.toString();
            }
            return passStr;
        }
        return ret.append("A robot can't play in the console\n").toString();
    }

    private String genMove(String[] command, String noCommand) {
        StringBuilder ret = new StringBuilder();
        IPlayer currentPlayer = command[1].equalsIgnoreCase("black") ? playerBlack : playerWhite;
        if (mode.equals("direct") && !currentPlayer.canPlayConsole()) {
            try {
                if (command[1].equalsIgnoreCase("BLACK") || command[1].equalsIgnoreCase("WHITE")) {
                    ret.append(this.board.makeRndMove(command[1].toLowerCase(), noCommand)).append("\n");
                    ret.append(this.board.toString());
                } else throw new RuntimeException("syntax error");
            } catch (RuntimeException e) {
                ret.append("?").append(noCommand).append(" illegal move\n");
            }
            return ret.toString();
        }
        return ret.append("A human can't play randomly\n").toString();
    }

    private String scoring() {
        return this.board.finalScore(playerBlack, playerWhite);
    }

    private String changeTypePlayers(String[] command, String noCommand) {
        String color = command[1];
        String type = command[2];
        IPlayer tmpPlayer;
        tmpPlayer = color.equals("black") ? playerBlack : (color.equals("white") ? playerWhite : null);
        if(tmpPlayer == null) return "?" + noCommand + " unrecognised color\n";
        try {
            int tmpScore = tmpPlayer.getScore();
            tmpPlayer = Factory.createPlayer(type);
            tmpPlayer.setScore(tmpScore);
        } catch (IllegalArgumentException e) {
            return "?" + noCommand + " unrecognised player type\n";
        }
        return "=" + noCommand + " player modified\n";
    }

    private String setHandicaps(String[] command, String noCommand) {
        if (this.board.getSize() >= 7) {
            String[] commandColor = {"", "black"};
            int nbHandicaps = Integer.parseInt(command[1]);

            if (nbHandicaps <= 4 && nbHandicaps >= 2) {
                for (int i = 0; i < nbHandicaps; i++) {
                    genMove(commandColor, noCommand);
                }
                return this.board.toString();
            } else return "?" + noCommand + " illegal handicaps number\n";
        }
        else return "?" + noCommand + " illegal size of board\n";
    }

    public boolean isOnlyRobotPlay() {
        return !playerBlack.canPlayConsole() && !playerWhite.canPlayConsole();
    }

    public void robotPlay(String color) {
        genMove(new String[]{"", color}, "");
        //TODO : Return the IA move
    }

    public void onlyRobotPlay() {
        robotPlay("black");
        robotPlay("white");
    }
}
