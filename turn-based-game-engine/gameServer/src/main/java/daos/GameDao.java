package daos;

import model.Game;

// Interface for data access operations for the Game
public interface GameDao {
    void createGame(Game game);
    Game getGame(String gameId);
    void updateGame(Game game);
}
