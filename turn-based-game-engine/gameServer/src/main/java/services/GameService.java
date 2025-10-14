package services;

import model.Game;
import model.Shot;

// Contains the core game logic
public class GameService {
    
    public Game createNewGame() {
        // Logic to create a new game instance
        return new Game();
    }

    public boolean joinGame(String gameId, String playerId) {
        // Logic for a player to join a game
        return true;
    }

    public boolean takeShot(String gameId, String playerId, Shot shot) {
        // Logic to process a shot and update the game state
        return true;
    }
}
