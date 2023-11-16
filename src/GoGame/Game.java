package GoGame;

import Players.Player;
import GoGame.Board;

public class Game {
    private Board board;
    private Player player1;
    private Player player2;

    public Game(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
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
