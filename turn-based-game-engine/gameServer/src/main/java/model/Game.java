package model;

// Represents the state of a single game
public class Game {
    private String gameId;
    private Player player1;
    private Player player2;
    private String status; // e.g., WAITING, IN_PROGRESS, FINISHED
    private String winner;
}
