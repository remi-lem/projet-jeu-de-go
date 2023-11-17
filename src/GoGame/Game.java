package GoGame;


import Players.Player;

public class Game {
    private Board board;
    private Player playerBlack;
    private Player playerWhite;

    public Game(Board board, Player playerBlack, Player playerWhite) {
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

    public void clearBoard() {
        testInitBoard();
        this.board.clear();
    }
}
