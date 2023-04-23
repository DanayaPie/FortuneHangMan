package side.project.FHM.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import side.project.FHM.dao.RoundDao;
import side.project.FHM.exception.GamesDoesNotExistException;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.exception.RoundDoesNotExistException;
import side.project.FHM.exception.TeamDoesNotExistException;
import side.project.FHM.model.Round;
import side.project.FHM.model.RoundId;
import side.project.FHM.utility.ValidateRound;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Primary
public class RoundService {

    private Logger logger = LoggerFactory.getLogger(RoundService.class);

    @Autowired
    private RoundDao roundDao;

    @Autowired
    private TeamService teamService;

    @Autowired
    private GameService gameService;

    public Set<Round> getAllRounds() throws RoundDoesNotExistException {
        logger.info("RoundService.getAllRounds() invoked");

        Set<Round> rounds = roundDao.getAllRounds();

        if (rounds.isEmpty()) {
            throw new RoundDoesNotExistException("No rounds on file.");
        }

        return rounds;
    }

    public List<Round> addRound(List<Integer> teamIds, Integer gameId) throws InvalidParameterException, TeamDoesNotExistException, GamesDoesNotExistException {
        logger.info("RoundService.addRound() invoked");
        logger.debug("sequence [{}]", roundDao.getSequenceId());

        gameService.getGameByGameId(gameId); // check if game exist

        long roundId = getSequenceId();
        int roundScore = 0;
        int spinScore = 0;
        List<Round> listRoundAdded = new ArrayList<>();

        for (Integer teamId : teamIds) {
            teamService.getTeamByTeamId(teamId); // check if team exist

            RoundId roundIdToAdd = new RoundId();
            roundIdToAdd.setRoundId(roundId);
            roundIdToAdd.setTeamId(teamId);
            roundIdToAdd.setGameId(gameId);

            Round roundToAdd = new Round();
            roundToAdd.setRoundId(roundIdToAdd);
            roundToAdd.setRoundScore(roundScore);
            roundToAdd.setSpinScore(spinScore);
            roundToAdd.setSpinToken(false);

            Round roundAdded = roundDao.addRound(roundToAdd);
            listRoundAdded.add(roundAdded);
        }

        return listRoundAdded;
    }

    public long getSequenceId() {
        logger.info("RoundService.getSequenceId() invoked");

        return roundDao.getSequenceId();
    }

    public List<Round> getRoundsByRoundId(long roundId) throws RoundDoesNotExistException {
        logger.info("RoundService.getRoundsByRoundId() invoked");

        List<Round> roundsToGet = roundDao.getRoundsByRoundId(roundId);

        if (roundsToGet.isEmpty()) {
            throw new RoundDoesNotExistException("No rounds with the round ID of " + roundId);
        }

        return roundsToGet;
    }

    public Round getRoundByRoundIdTeamId(long roundId, int teamId) throws RoundDoesNotExistException {
        logger.info("RoundService.getRoundByRoundIdTeamId() invoked");

        Round roundToGet = roundDao.getRoundByRoundIdTeamId(roundId, teamId);

        if (roundToGet == null) {
            throw new RoundDoesNotExistException("No round with the round ID of " + roundId + " and team ID of " + teamId);
        }

        return roundToGet;
    }

    public List<Round> getRoundsByRoundIdGameId(long roundId, int gameId) throws RoundDoesNotExistException {
        logger.info("RoundService.getRoundsByRoundIdGameId() invoked");

        List<Round> roundsToGet = roundDao.getRoundsByRoundIdGameId(roundId, gameId);

        if (roundsToGet.isEmpty()) {
            throw new RoundDoesNotExistException("No round with the round ID of " + roundId + " and game ID of " + gameId);
        }

        return roundsToGet;
    }

    public Round updateRoundByRoundIdTeamId(long roundId, int teamId, String roundScore, String spinScore, String spinToken) throws RoundDoesNotExistException, InvalidParameterException {
        logger.info("RoundService.updateRoundByRoundIdTeamId() invoked");

        Round roundToUpdate = getRoundByRoundIdTeamId(roundId, teamId);

        /*
            parse int inputs and set to roundToUpdate instance
         */
        Boolean roundInputsIntErrorBoolean = false;
        StringBuilder roundInputsErrorString = new StringBuilder();

        // round score
        if (roundScore != null) {
            logger.info("Updating round score");

            if (roundScore.matches("^[0-9]*$")) {
                // set round score
                int roundScoreNumber = Integer.parseInt(roundScore);
                roundToUpdate.setRoundScore(roundScoreNumber);
            } else {
                logger.info("Round score is not an int");

                roundInputsErrorString.append("Round score");
                roundInputsIntErrorBoolean = true;
            }
        }

        // spin score
        if (spinScore != null) {
            logger.info("Updating spin score");

            if (spinScore.matches("^[0-9]*$")) {
                // set spin score
                int spinScoreNumber = Integer.parseInt(spinScore);
                roundToUpdate.setSpinScore(spinScoreNumber);
            } else {
                logger.info("Spin score is not an int");

                if (roundInputsIntErrorBoolean) {
                    roundInputsErrorString.append(", spin score");
                } else {
                    roundInputsErrorString.append("Spin score");
                }
                roundInputsIntErrorBoolean = true;
            }
        }

        // append int error message
        if (roundInputsIntErrorBoolean) {
            roundInputsErrorString.append(" must be whole number.");
            throw new InvalidParameterException(roundInputsErrorString.toString());
        }

        /*
            spinToken error
         */
        logger.debug("spinToken {}", spinToken);
        // spin token
        if (spinToken != null) {
            logger.info("Updating spin token");

            ValidateRound.validateSpinToken(spinToken.trim());
            boolean spinTokenBoolean = Boolean.parseBoolean(spinToken);
            roundToUpdate.setSpinToken(spinTokenBoolean);
        }

        Round roundUpdated = roundDao.updateRoundByRoundIdTeamId(roundToUpdate);
        return roundUpdated;
    }
}
