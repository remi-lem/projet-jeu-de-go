package src.GoApp;

import src.GoGame.Game;

import java.util.Objects;
import java.util.Scanner;

public class TUI {
    private Game game;
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        do {
            // TODO : les autres commandes
        }
        while (!Objects.equals(sc.nextLine(), "quit"));
    }
}
