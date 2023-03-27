package side.project.FHM.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import side.project.FHM.dao.RoundDao;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.exception.RoundDoesNotExist;
import side.project.FHM.exception.TeamDoesNotExist;
import side.project.FHM.model.Game;
import side.project.FHM.model.Round;
import side.project.FHM.model.RoundId;
import side.project.FHM.model.Team;

import java.util.Set;

@Service
public class RoundService {

    private Logger logger = LoggerFactory.getLogger(RoundService.class);

    @Autowired
    private RoundDao roundDao;

    @Autowired
    private TeamService teamService;

    @Autowired
    private GameService gameService;

    public Set<Round> getAllRounds() throws RoundDoesNotExist, InvalidParameterException {
        logger.info("RoundService.getAllRounds() invoked");

        Set<Round> rounds = roundDao.getAllRounds();

        try {
            if (rounds.isEmpty()) {
                throw new RoundDoesNotExist("No rounds on file.");
            }

            return rounds;
        } catch (DataAccessException e) {
            throw new InvalidParameterException("No rounds on file.");
        }
    }

    public Round addRound(Integer teamId, Integer gameId) throws InvalidParameterException, TeamDoesNotExist {
        logger.info("RoundService.addRound() invoked");

        int roundScore = 0;
        int spinScore = 0;
        boolean spinToken = false;
        Team team = teamService.getTeamByTeamId(teamId);
        Game game = gameService.getGameByGameId(gameId);

        RoundId roundIdToAdd = new RoundId();
        roundIdToAdd.setTeam(team);
        roundIdToAdd.setGame(game);

        Round roundToAdd = new Round();
        roundToAdd.setRoundId(roundIdToAdd);
        roundToAdd.setRoundScore(roundScore);
        roundToAdd.setSpinScore(spinScore);
        roundToAdd.setSpinToken(spinToken);

        Round roundAdded = roundDao.addRound(roundToAdd);
        return roundAdded;
    }

    public Round getRoundByRoundId(int roundId) throws InvalidParameterException {
        logger.info("RoundService.getRoundByRoundId() invoked");

        Round roundToGet = roundDao.getRoundByRoundId(roundId);

        try {
            if (roundToGet == null) {
                throw new InvalidParameterException("No round with the round ID of " + roundId);
            }
            return roundToGet;

        } catch (DataAccessException | InvalidParameterException e) {
            throw new InvalidParameterException("No round with the round ID of " + roundId);
        }
    }

    public Round getRoundByRoundIdTeamId(int roundId, int teamId) throws InvalidParameterException {
        logger.info("RoundService.getRoundByRoundIdTeamId() invoked");

        Round roundToGet = roundDao.getRoundByRoundIdTeamId(roundId, teamId);

        try {
            if (roundToGet == null) {
                throw new InvalidParameterException("No round with the round ID of " + roundId + " and team ID of " + teamId);
            }
            return roundToGet;
        } catch (DataAccessException e) {
            throw new InvalidParameterException("No round with the round ID of " + roundId + " and team ID of " + teamId);
        }
    }

//    public Round updateRoundByRoundIdTeamId(int roundId, int teamId, String roundScore, String spinScore, String s) throws InvalidParameterException {
//        logger.info("RoundService.updateRoundBYRoundId() invoked");
//
//        Round roundToUpdate = new Round();
//        roundToUpdate = getRoundByRoundId(roundId);
//
//        /*
//            parse int inputs and set to roundToUpdate instance
//         */
//        Boolean roundInputsIntErrorBoolean = false;
//        StringBuilder roundInputsErrorString = new StringBuilder();
//
//        try {
//
//            // team ID
//            if (teamId != null) {
//                logger.info("Updating team ID");
//
//                if (teamId.matches("^[0-9]*$")) {
//                     // set team id
//                    int teamIdNumber = Integer.parseInt(teamId.trim());
//                    Team teamToGet = teamService.getTeamByTeamId(teamIdNumber);
//                }
//            }
//
//            // append int error message
//            if (roundInputsIntErrorBoolean) {
//                roundInputsErrorString.append(" must be whole number.");
//                throw new NumberFormatException(roundInputsErrorString.toString());
//            }
//
//        } catch (NumberFormatException e) {
//            throw new InvalidParameterException(roundInputsErrorString.toString());
//        }
//
//        Round roundUpdated = roundDao.updateRoundByRoundId(roundToUpdate);
//        return roundUpdated;
//    }
}
