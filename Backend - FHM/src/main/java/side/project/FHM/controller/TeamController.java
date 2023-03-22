package side.project.FHM.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.exception.TeamsDoesNotExist;
import side.project.FHM.model.Team;
import side.project.FHM.service.TeamService;

import java.util.List;

@RestController
public class TeamController {

    private Logger logger = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    private TeamService teamService;

    @GetMapping(path = "/team")
    public ResponseEntity<Object> getAllTeams() throws TeamsDoesNotExist, InvalidParameterException {
        logger.info("TeamController.getAllTeams() invoked");

        try {
            List<Team> allTeams = teamService.getAllTeams();

            return ResponseEntity.status(200).body(allTeams);
        } catch (TeamsDoesNotExist e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
