package side.project.FHM.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import side.project.FHM.dao.TeamDao;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.exception.TeamDoesNotExistException;
import side.project.FHM.model.Team;
import side.project.FHM.utility.ValidateTeam;

import java.util.List;

@Service
public class TeamService {

    private Logger logger = LoggerFactory.getLogger(TeamService.class);

    @Autowired
    private TeamDao teamDao;

    public List<Team> getAllTeams() throws TeamDoesNotExistException {
        logger.info("TeamService.getAllTeams() invoked");

        List<Team> teams = this.teamDao.getAllTeams();
        return teams;
    }

    public Team addTeam(String teamName, String teamTurn, String gameId) throws InvalidParameterException {
        logger.info("TeamService.addTeam() invoked");

        Team teamToAdd = new Team();
        int teamScore = 0;

        teamName = teamName.trim();
        ValidateTeam.validateTeamNameBlank(teamName);

        teamToAdd.setTeamName(teamName);
        teamToAdd.setTotalScore(teamScore);

        boolean teamInputsIntegerErrorBoolean = false;
        StringBuilder teamInputsErrorString = new StringBuilder();

        try {
            if (teamTurn.matches("^[0-9]*$")) {
                //set team turn
                int teamTurnNumber = Integer.parseInt(teamTurn.trim());
                teamToAdd.setTeamTurn(teamTurnNumber);
            } else {
                logger.info("Team turn is not an int");

                teamInputsErrorString.append("Team turn");
                teamInputsIntegerErrorBoolean = true;
            }

            if (gameId.matches("^[0-9]*$")) {
                // set game id
                int gameIdNumber = Integer.parseInt(gameId.trim());
                teamToAdd.setGameId(gameIdNumber);
            } else {
                logger.info("Total score is not an int");

                if (teamInputsIntegerErrorBoolean) {
                    teamInputsErrorString.append(", game ID");
                } else {
                    teamInputsErrorString.append("Game ID");
                }
            }

            if (teamInputsIntegerErrorBoolean) {
                teamInputsErrorString.append(" must be whole number.");
                throw new NumberFormatException(teamInputsErrorString.toString());
            }
        } catch (NumberFormatException e) {
            throw new InvalidParameterException(teamInputsErrorString.toString());
        }

        Team addedTeam = teamDao.addTeam(teamToAdd);
        return addedTeam;
    }

    public Team getTeamByTeamId(int teamId) throws TeamDoesNotExistException, InvalidParameterException {
        logger.info("TeamService.getTeamByTeamId() invoked");

        Team teamToGet = this.teamDao.getTeamByTeamId(teamId);

        try {
            if (teamToGet == null) {
                throw new TeamDoesNotExistException("No team with the team ID of " + teamId);
            }
            return teamToGet;
        } catch (DataAccessException e) {
            throw new InvalidParameterException("No team with the team ID of " + teamId);
        }
    }

    public Team updateTeamByTeamId(int teamId, String gameId, String totalScore) throws InvalidParameterException, TeamDoesNotExistException {
        logger.info("TeamService.updateTeamByTeamId() invoked");

        Team teamToUpdate = getTeamByTeamId(teamId);

        boolean teamInputsIntegerErrorBoolean = false;
        StringBuilder teamInputsErrorString = new StringBuilder();

        try {
            if (gameId != null) {
                logger.info("Updating game ID");

                if (gameId.matches("^[0-9]*$")) {
                    //set game ID
                    int gameIdNumber = Integer.parseInt(gameId.trim());
                    teamToUpdate.setGameId(gameIdNumber);
                } else {
                    logger.info("Game ID is not an int");

                    teamInputsErrorString.append("Game ID");
                    teamInputsIntegerErrorBoolean = true;
                }
            }

            if (totalScore != null) {
                logger.info("Updating total score");

                if (totalScore.matches("^[0-9]*$")) {
                    // set total score
                    int totalScoreNumber = Integer.parseInt(totalScore.trim());
                    teamToUpdate.setTotalScore(totalScoreNumber);
                } else {
                    logger.info("Total score is not an int");

                    if (teamInputsIntegerErrorBoolean) {
                        teamInputsErrorString.append(", total score");
                    } else {
                        teamInputsErrorString.append("Total score");
                    }
                }
            }

            if (teamInputsIntegerErrorBoolean) {
                teamInputsErrorString.append(" must be whole number.");
                throw new NumberFormatException(teamInputsErrorString.toString());
            }
        } catch (NumberFormatException e) {
            throw new InvalidParameterException(teamInputsErrorString.toString());
        }

        Team updatedTeam = teamDao.updateTeamByTeamId(teamToUpdate);
        return updatedTeam;
    }

    public List<Team> getTeamsByGameId(int gameId) throws InvalidParameterException {
        logger.info("TeamService.getTeamsByGameId() invoked");

        List<Team> teamsToGet = teamDao.getTeamsByGameId(gameId);

        try {
            if (teamsToGet.isEmpty()) {
                throw new TeamDoesNotExistException("No teams with the game ID of " + gameId);
            }
            return teamsToGet;
        } catch (DataAccessException | TeamDoesNotExistException e) {
            throw new InvalidParameterException("No teams with the game ID of " + gameId);
        }
    }
}
