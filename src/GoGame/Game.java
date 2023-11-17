package GoGame;

import Players.Player;

public class Game {
    private Board board;
    private Player player1;
    private Player player2;

    public Game(Board board, Player player1, Player player2) {
        this.board = board;
        this.playerBlack = playerBlack;
        this.playerWhite = playerWhite;
    }

    public void showBoard() {
        if(board != null){
            this.board.show();
        }
        else {
            throw new RuntimeException("Board not initialized");
        }
    }
}
