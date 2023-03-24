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

    @GetMapping(path = "/team/{teamId}")
    public ResponseEntity<Object> getTeamByTeamId(@PathVariable int teamId) {
        logger.info("TeamController.getTeamByTeamId() invoked");

        try {
            Team teamToGet = this.teamService.getTeamByTeamId(teamId);
            return ResponseEntity.status(200).body(teamToGet);

        } catch (InvalidParameterException | TeamDoesNotExist e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
