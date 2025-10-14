package dtos;

import model.Shot;

// Data Transfer Object for representing a player's action
public class GameAction {
    private String actionType; // e.g., "TAKE_SHOT"
    private String playerId;
    private Shot shot;
}
