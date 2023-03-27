package side.project.FHM.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import side.project.FHM.dao.TeamDao;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.exception.TeamDoesNotExist;
import side.project.FHM.model.Team;
import side.project.FHM.utility.ValidateTeam;

import java.util.List;

@Service
public class TeamService {

    private Logger logger = LoggerFactory.getLogger(TeamService.class);

    @Autowired
    private TeamDao teamDao;

    public List<Team> getAllTeams() throws TeamDoesNotExist, InvalidParameterException {
        logger.info("TeamService.getAllTeams() invoked");

        List<Team> teams = this.teamDao.getAllTeams();

        try {
            if (teams.isEmpty()) {
                throw new TeamDoesNotExist("No teams on file.");
            }
            return teams;

        } catch (DataAccessException e) {
            throw new InvalidParameterException("No teams on file.");
        }
    }

    public Team addTeam(String teamName, String teamTurn) throws InvalidParameterException {
        logger.info("TeamService.addTeam() invoked");

        teamName = teamName.trim();

        int teamTurnNumber;
        int teamScore = 0;

        ValidateTeam.validateTeamNameBlank(teamName);

        try {
            teamTurnNumber = Integer.parseInt(teamTurn.trim());
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Team turn must be a whole number");
        }

        Team teamToAdd = teamDao.addTeam(teamName, teamTurnNumber, teamScore);
        return teamToAdd;
    }

    public Team getTeamByTeamId(int teamId) throws TeamDoesNotExist, InvalidParameterException {
        logger.info("TeamService.getTeamByTeamId() invoked");

        Team teamToGet = this.teamDao.getTeamByTeamId(teamId);

        try {
            if (teamToGet == null) {
                throw new TeamDoesNotExist("No team with the team ID of " + teamId);
            }
            return teamToGet;
        } catch (DataAccessException e) {
            throw new InvalidParameterException("No team with the team ID of " + teamId);

        }
    }

    public Team updateTeamByTeamId(int teamId, String totalScore) throws InvalidParameterException, TeamDoesNotExist {
        logger.info("TeamService.updateTeamByTeamId() invoked");

        int totalScoreNumber;
        Team teamToUpdate = getTeamByTeamId(teamId);

        try {
            totalScoreNumber = Integer.parseInt(totalScore);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Total score must be a whole number.");
        }

        teamToUpdate.setTotalScore(totalScoreNumber);

        Team updatedTeam = teamDao.updateTeamByTeamId(teamToUpdate);
        return updatedTeam;
    }
}
