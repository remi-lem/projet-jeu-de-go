package game;

import app.Factory;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    private final ArrayList<Board> boardHistory;
    private Board board;
    private IPlayer playerBlack;
    private IPlayer playerWhite;
    private final int BOARD_SIZE_MAX = 19;
    private int cptSkip = 0;
    private boolean isFinished = false;
    private String mode = "gtp";

    public Game(IPlayer playerBlack, IPlayer playerWhite) throws CloneNotSupportedException {
        this.playerBlack = playerBlack;
        this.playerWhite = playerWhite;
        this.board = new Board(BOARD_SIZE_MAX);
        this.boardHistory = new ArrayList<>();
        this.boardHistory.add(new Board(this.board));
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
        String ret;
        try {
            Integer.parseInt(command[0]);
            noCommand = command[0];
            command = Arrays.copyOfRange(command, 1, command.length);
        } catch(NumberFormatException ignored) {

        }

        ret = switch (command[0]) {
            case "boardsize", "b" -> boardsize(command, noCommand);
            case "showboard", "s" -> showBoard(noCommand);
            case "play", "p" -> playMove(command, noCommand);
            case "clear_board", "c" -> clearBoard(noCommand);
            case "genmove", "g" -> genMove(command, noCommand);
            case "final_score", "f" -> scoring(noCommand);
            case "player", "pr" -> changeTypePlayers(command, noCommand);
            case "set_handicaps", "sh" -> setHandicaps(command, noCommand);
            case "undo", "u" -> undoMove(noCommand);
            case "quit", "q" -> endGame(noCommand);
            default -> "unrecognized command";
        };
        return ret;
    }

    private String commandGTP(String noCommand) {
        return "=" + noCommand + "\n";
    }

    private String boardsize(String[] command, String noCommand) {
        String ret;
        try {
            int size = Integer.parseInt(command[1]);
            if ((size > 0) && (size < BOARD_SIZE_MAX + 1)) {
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

        if (mode.equals("direct") && !currentPlayer.canPlayConsole())
            return ret.append("A robot can't play in the console\n").toString();

        ret.append(commandGTP(noCommand));
        String passStr = "pass\n";
        if (command[2].equals("pass")) {
            ++cptSkip;
            if (cptSkip == 2) {
                return ret.append(passStr).append("\n").append(endGame(noCommand)).toString();
            }
        } else {
            cptSkip = 0;
            try {
                if (command[1].equalsIgnoreCase("black")
                        || command[1].equalsIgnoreCase("white")) {
                    this.board.makeMove(command[1].toLowerCase(), command[2].toUpperCase());
                    this.boardHistory.add(new Board(this.board));
                    ret.append("\n").append(this.board.toString());

                    IPlayer nextPlayer = currentPlayer.equals(playerBlack) ? playerWhite : playerBlack;
                    if (!nextPlayer.canPlayConsole())
                        ret.append("\n").append(robotPlay(nextPlayer.equals(playerBlack) ? "black" : "white"));
                } else throw new RuntimeException("syntax error");
            } catch (RuntimeException e) {
                if(e.getMessage().equalsIgnoreCase("not enough stones"))
                    ret.delete(0,ret.length()).append(endGame(noCommand));
                else ret.append("?").append(noCommand).append(" illegal move\n");
            }
            return ret.toString();
        }
        return passStr;
    }

    private String genMove(String[] command, String noCommand) {
        StringBuilder ret = new StringBuilder();
        IPlayer currentPlayer = command[1].equalsIgnoreCase("black") ? playerBlack : playerWhite;

        if (mode.equals("direct") && currentPlayer.canPlayConsole())
            return ret.append("A human can't play randomly\n").toString();

        try {
            if (command[1].equalsIgnoreCase("BLACK") || command[1].equalsIgnoreCase("WHITE")) {
                String movePlayed = this.board.makeRndMove(command[1].toLowerCase());

                this.boardHistory.add(new Board(this.board)); //TODO: it change all the boards, not only the last one
                ret.append(commandGTP(noCommand));

                if (mode.equals("direct"))
                    ret.append("Robot ").append(command[1]).append(" have played ").append(movePlayed).append("\n");
                ret.append(this.board.toString());
            } else throw new RuntimeException("syntax error");
        } catch (RuntimeException e) {
            if(e.getMessage().equalsIgnoreCase("pass") ||
                    e.getMessage().equalsIgnoreCase("not enough stones"))
                ret.append(endGame(noCommand));
            else ret.append("?").append(noCommand).append(" illegal move\n");
        }
        return ret.toString();
    }

    private String scoring(String noCommand) {
        return commandGTP(noCommand) + this.board.finalScore(playerBlack, playerWhite);
    }

    private String endGame(String noCommand) {
        isFinished = true;
        return scoring(noCommand) + "\nGame finished!";
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
        return commandGTP(noCommand) + "player modified\n";
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

    private String undoMove(String noCommand) {
        if (boardHistory.size() > 1) {
            this.boardHistory.remove(boardHistory.size() - 1);
            this.board = new Board(boardHistory.get(boardHistory.size() - 1));  // Utilisez la copie de l'état précédent
            return commandGTP(noCommand) + this.board.toString();
        }
        return commandGTP(noCommand) + "can't undo command";
    }

    public boolean isOnlyRobotPlay() {
        return !playerBlack.canPlayConsole() && !playerWhite.canPlayConsole();
    }

    public boolean isRobotFirstPlayer() {
        return !playerBlack.canPlayConsole();
    }

    public String robotPlay(String color) {
        return genMove(new String[]{"", color}, "");
    }

    public String onlyRobotPlay() {
        return robotPlay("black") + "\n" + (isNotFinished() ? robotPlay("white") : "");
    }
}
