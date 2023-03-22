package side.project.FHM.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import side.project.FHM.dao.TeamDao;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.exception.TeamsDoesNotExist;
import side.project.FHM.model.Team;

import java.util.List;

@Service
public class TeamService {

    private Logger logger = LoggerFactory.getLogger(TeamService.class);

    @Autowired
    private TeamDao teamDao;


    public List<Team> getAllTeams() throws TeamsDoesNotExist, InvalidParameterException {
        logger.info("TeamService.getAllTeams() invoked");

        List<Team> teams = this.teamDao.getAllTeams();

        try {
            if (teams.isEmpty()) {
                throw new TeamsDoesNotExist("No teams on file.");
            }
            return teams;

        } catch (DataAccessException e) {
            throw new InvalidParameterException("No teams on file.");
        }
    }
}
