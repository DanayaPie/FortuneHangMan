package side.project.FHM.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import side.project.FHM.dao.RoundDao;
import side.project.FHM.dao.RoundSeqForRoundServiceTesting;
import side.project.FHM.exception.GamesDoesNotExistException;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.exception.TeamDoesNotExistException;
import side.project.FHM.model.Round;
import side.project.FHM.model.RoundId;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("RoundServiceTest")
public class RoundSeqServiceForTesting extends RoundService {

    @Autowired
    private RoundDao roundDao;

    @Autowired
    private GameService gameService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private RoundSeqForRoundServiceTesting roundSeqForRoundServiceTesting;

    @Override
    public List<Round> addRound(List<Integer> teamIds, Integer gameId) throws InvalidParameterException, TeamDoesNotExistException, GamesDoesNotExistException {
//        logger.debug("sequence [{}]", roundDao.getSequenceId());

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

    @Override
    public long getSequenceId() {

        return roundSeqForRoundServiceTesting.getSequenceId();
    }
}
