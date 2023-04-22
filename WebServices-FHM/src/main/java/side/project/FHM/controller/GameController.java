package side.project.FHM.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import side.project.FHM.exception.GamesDoesNotExistException;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.exception.TeamDoesNotExistException;
import side.project.FHM.exception.WordDoesNotExistException;
import side.project.FHM.model.Game;
import side.project.FHM.service.GameService;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"}, maxAge = 3600)
@RestController
public class GameController {

    private Logger logger = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private GameService gameService;


    @GetMapping(path = "/game")
    public ResponseEntity<Object> getAllGames() {
        logger.info("GameController.getAllGames() invoked");

        try {
            List<Game> allGames = gameService.getAllGames();
            return ResponseEntity.status(200).body(allGames);

        } catch (GamesDoesNotExistException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping(path = "/game")
    public ResponseEntity<Object> addGame(@RequestBody Map<String, String> json) {
        logger.info("GameController.addGame() invoked");

        try {
            Game gameToAdd = gameService.addGame(json.get("gameName"), json.get("totalTeam"));
            return ResponseEntity.status(200).body(gameToAdd);

        } catch (InvalidParameterException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping(path = "/game/{gameId}")
    public ResponseEntity<Object> getGameByGameId(@PathVariable int gameId) {
        logger.info("GameController.getGameByGameId() invoked");

        try {
            Game gameToGet = gameService.getGameByGameId(gameId);
            return ResponseEntity.status(200).body(gameToGet);

        } catch (GamesDoesNotExistException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // update all game's fields except letter guessed
    @PutMapping(path = "/game/{gameId}")
    public ResponseEntity<Object> updateGameByGameId(@PathVariable(value = "gameId") int gameId, @RequestParam Map<String, String> json) {
        logger.info("GameController.updateGameByGameId() invoked");

        try {
            Game gameToUpdate = gameService.updateGameByGameId(gameId
                    , json.get("roundId")
                    , json.get("wordId")
                    , json.get("gameStatus")
                    , json.get("letterGuessed")
                    , json.get("currentTeamTurn")
                    , json.get("currentRound"));
            return ResponseEntity.status(200).body(gameToUpdate);

        } catch (InvalidParameterException | WordDoesNotExistException | TeamDoesNotExistException | GamesDoesNotExistException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
