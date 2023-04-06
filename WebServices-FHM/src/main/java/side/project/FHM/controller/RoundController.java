package side.project.FHM.controller;

import org.json.JSONArray;
import org.json.JSONObject;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin(origins = {"http://localhost:8080","http://localhost:3000"},maxAge = 3600)
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
    public ResponseEntity<Object> addRound(@RequestBody Map<String, Object> json) {
        logger.info("RoundController.addRound() invoked");
        logger.debug("Json Body  [{}]", json);
        JSONObject jsonObj = new JSONObject(json);

        try {
            JSONArray teamJsonArray = jsonObj.getJSONArray("teamIds");
            logger.debug("teamJsonArray  [{}]", teamJsonArray);
            List<Integer> teamIds = new ArrayList<>();
            for (int i = 0; i < teamJsonArray.length(); i++) {
                teamIds.add(teamJsonArray.getInt(i));
            }

            List<Round> roundsToAdd = roundService.addRound(teamIds, jsonObj.getInt("gameId"));
            return ResponseEntity.status(200).body(roundsToAdd);

        } catch (InvalidParameterException | TeamDoesNotExist e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping(path = "/round/{roundId}")
    public ResponseEntity<Object> getRoundsByRoundId(@PathVariable long roundId) {
        logger.info("RoundController.getRoundsByRoundId() invoked");

        try {
            List<Round> roundsToGet = roundService.getRoundsByRoundId(roundId);
            return ResponseEntity.status(200).body(roundsToGet);

        } catch (InvalidParameterException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping(path = "/roundTeam/{roundId}/{teamId}")
    public ResponseEntity<Object> getRoundByRoundIdTeamId(@PathVariable(value = "roundId") long roundId
            , @PathVariable(value = "teamId") int teamId) {
        logger.info("RoundController.getRoundByRoundIdTeamId() invoked");

        try {
            Round roundToGet = roundService.getRoundByRoundIdTeamId(roundId, teamId);
            return ResponseEntity.status(200).body(roundToGet);
        } catch (InvalidParameterException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping(path = "/roundGame/{roundId}/{gameId}")
    public ResponseEntity<Object> getRoundsByRoundIdGameId(@PathVariable(value = "roundId") long roundId
            , @PathVariable(value = "gameId") int gameId) {
        logger.info("RoundController.getRoundsByRoundIdGameId() invoked");

        try {
            List<Round> roundsToGet = roundService.getRoundsByRoundIdGameId(roundId, gameId);
            return ResponseEntity.status(200).body(roundsToGet);
        } catch (InvalidParameterException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    @PutMapping(path = "/round/{roundId}/{teamId}")
    public ResponseEntity<Object> updateRoundByRoundIdTeamId(@PathVariable(value = "roundId") long roundId
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
