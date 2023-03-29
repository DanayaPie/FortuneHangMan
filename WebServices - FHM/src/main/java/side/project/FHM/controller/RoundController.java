package side.project.FHM.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.exception.RoundDoesNotExist;
import side.project.FHM.exception.TeamDoesNotExist;
import side.project.FHM.model.Round;
import side.project.FHM.service.RoundService;

import java.util.Map;
import java.util.Set;

@RestController
public class RoundController {

    private Logger logger = LoggerFactory.getLogger(RoundController.class);

    @Autowired
    private RoundService roundService;

    @GetMapping(path = "/round")
    public ResponseEntity<Object> getAllRounds() {
        logger.info("RoundController.getAllRounds() invoked");

        try {
            Set<Round> allRounds = roundService.getAllRounds();
            return ResponseEntity.status(200).body(allRounds);

        } catch (InvalidParameterException | RoundDoesNotExist e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping(path = "/round")
    public ResponseEntity<Object> addRound(@RequestBody Map<String, Integer> json) {
        logger.info("RoundController.getAllRounds() invoked");

        try {
            Round roundToAdd = roundService.addRound(json.get("teamId"), json.get("gameId"));
            return ResponseEntity.status(200).body(roundToAdd);

        } catch (InvalidParameterException | TeamDoesNotExist e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping(path = "/round/{roundId}")
    public ResponseEntity<Object> getRoundByRoundId(@PathVariable int roundId) {
        logger.info("RoundController.getRoundByRoundId() invoked");

        try {
            Round roundToGet = roundService.getRoundByRoundId(roundId);
            return ResponseEntity.status(200).body(roundToGet);

        } catch (InvalidParameterException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping(path = "/round/{roundId}/{teamId}")
    public ResponseEntity<Object> getRoundByRoundIdTeamId(@PathVariable(value = "roundId") int roundId
            , @PathVariable(value = "teamId") int teamId) {
        logger.info("RoundController.getRoundByRoundIdTeamId() invoked");

        try {
            Round roundToGet = roundService.getRoundByRoundIdTeamId(roundId, teamId);
            return ResponseEntity.status(200).body(roundToGet);
        } catch (InvalidParameterException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping(path = "/round/{roundId}/{teamId}")
    public ResponseEntity<Object> updateRoundByRoundIdTeamId(@PathVariable(value = "roundId") int roundId
            , @PathVariable(value = "teamId") int teamId
            , @RequestParam Map<String, String> json) {
        logger.info("RoundController.updateRoundByRoundIdTeamId() invoked");

        try {
            Round roundToUpdate = roundService.updateRoundByRoundIdTeamId(roundId, teamId
                    , json.get("roundScore")
                    , json.get("spinScore")
                    , json.get("spinToken"));

            return ResponseEntity.status(200).body(roundToUpdate);
        } catch (InvalidParameterException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
