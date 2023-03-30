package side.project.FHM.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.exception.TeamDoesNotExist;
import side.project.FHM.model.Team;
import side.project.FHM.service.TeamService;

import java.util.List;
import java.util.Map;

@RestController
public class TeamController {

    private Logger logger = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    private TeamService teamService;

    @GetMapping(path = "/team")
    public ResponseEntity<Object> getAllTeams() {
        logger.info("TeamController.getAllTeams() invoked");

        try {
            List<Team> allTeams = teamService.getAllTeams();
            return ResponseEntity.status(200).body(allTeams);

        } catch (InvalidParameterException | TeamDoesNotExist e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping(path = "/team")
    public ResponseEntity<Object> addTeam(@RequestBody Map<String, String> json) {
        logger.info("TeamController.addTeam() invoked");

        try {
            Team teamToAdd = teamService.addTeam(json.get("teamName"), json.get("teamTurn"));
            return ResponseEntity.status(200).body(teamToAdd);

        } catch (InvalidParameterException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping(path = "/team/{teamId}")
    public ResponseEntity<Object> getTeamByTeamId(@PathVariable int teamId) {
        logger.info("TeamController.getTeamByTeamId() invoked");

        try {
            Team teamToGet = teamService.getTeamByTeamId(teamId);
            return ResponseEntity.status(200).body(teamToGet);

        } catch (InvalidParameterException | TeamDoesNotExist e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping(path = "/team/{teamId}")
    public ResponseEntity<Object> updateTeamByTeamId(@PathVariable int teamId, @RequestParam Map<String, String> json) {
        logger.info("TeamController.updateTeamByTeamId() invoked");

        try {
            Team teamToUpdate = teamService.updateTeamByTeamId(teamId
                    , json.get("gameId")
                    , json.get("totalScore"));
            return ResponseEntity.status(200).body(teamToUpdate);

        } catch (InvalidParameterException | TeamDoesNotExist e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping(path = "/team/game/{gameId}")
    public ResponseEntity<Object> getTeamsByGameId(@PathVariable int gameId) {
        logger.info("TeamController.getTeamsByGameId() invoked");

        try {
            List<Team> teamsToGet = teamService.getTeamsByGameId(gameId);
            return ResponseEntity.status(200).body(teamsToGet);
        } catch (InvalidParameterException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
