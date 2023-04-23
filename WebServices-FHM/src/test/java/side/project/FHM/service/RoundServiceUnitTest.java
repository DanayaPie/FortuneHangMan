package side.project.FHM.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import side.project.FHM.dao.RoundDao;
import side.project.FHM.exception.GamesDoesNotExistException;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.exception.RoundDoesNotExistException;
import side.project.FHM.exception.TeamDoesNotExistException;
import side.project.FHM.model.Game;
import side.project.FHM.model.Round;
import side.project.FHM.model.RoundId;
import side.project.FHM.model.Team;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RoundServiceUnitTest {

    @InjectMocks
    private RoundService roundServiceUnderTest;

    @Mock
    private RoundDao roundDao;

    @Mock
    private GameService gameService;

    @Mock
    private TeamService teamService;

    @Test
    void getAllRounds_positive() throws RoundDoesNotExistException {

        RoundId roundId = new RoundId(1, 1, 1);
        Round round = new Round(roundId, 0, 0, false);

        Set<Round> roundList = new HashSet<>();
        roundList.add(round);

        when(roundDao.getAllRounds()).thenReturn(roundList);
        Set<Round> actual = roundServiceUnderTest.getAllRounds();

        Set<Round> expected = new HashSet<>();
        expected.add(round);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAllRounds_roundDoesNotExist_negative() throws RoundDoesNotExistException {

        Set<Round> roundList = new HashSet<>();
        when(roundDao.getAllRounds()).thenReturn(roundList);

        Throwable actualExceptionThrown = Assertions.assertThrows(RoundDoesNotExistException.class, () -> roundServiceUnderTest.getAllRounds());
        Assertions.assertEquals("No rounds on file.", actualExceptionThrown.getMessage());
    }

    @Test
    void addRound_positive() throws InvalidParameterException, TeamDoesNotExistException, GamesDoesNotExistException {

        List<Integer> teamIds = new ArrayList<>();
        teamIds.add(1);

        Game game = new Game(1, "FHM TEST", null, null, "STARTED", null, 1, 1, 2);
        Team team = new Team(1, "ONE", 1, 1, 0);
        RoundId roundId = new RoundId(1, 1, 1);
        Round roundToAdd = new Round(roundId, 0, 0, false);

        when(gameService.getGameByGameId(1)).thenReturn(game);
        when(teamService.getTeamByTeamId(1)).thenReturn(team);
        when(roundDao.getSequenceId()).thenReturn(1L);
        when(roundDao.addRound(roundToAdd)).thenReturn(roundToAdd);

        List<Round> actual = roundServiceUnderTest.addRound(teamIds, 1);

        List<Round> expected = new ArrayList<>();
        expected.add(roundToAdd);

        Assertions.assertTrue(expected.containsAll(actual));
        Assertions.assertTrue(actual.containsAll(expected));
    }

    @Test
    void getRoundsByRoundId_positive() throws RoundDoesNotExistException {

        RoundId roundId = new RoundId(1, 1, 1);
        Round round = new Round(roundId, 0, 0, false);

        List<Round> roundList = new ArrayList<>();
        roundList.add(round);

        when(roundDao.getRoundsByRoundId(1)).thenReturn(roundList);
        List<Round> actual = roundServiceUnderTest.getRoundsByRoundId(1);

        List<Round> expected = new ArrayList<>();
        expected.add(round);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getRoundsByRoundId_roundDoesNotExist_negative() {

        List<Round> roundList = new ArrayList<>();

        when(roundDao.getRoundsByRoundId(1)).thenReturn(roundList);

        Throwable actualExceptionThrown = Assertions.assertThrows(RoundDoesNotExistException.class, () -> roundServiceUnderTest.getRoundsByRoundId(1));
        Assertions.assertEquals("No rounds with the round ID of 1", actualExceptionThrown.getMessage());
    }

    @Test
    void getRoundByRoundIdTeamId_positive() throws RoundDoesNotExistException {

        RoundId roundId = new RoundId(1, 1, 1);
        Round round = new Round(roundId, 0, 0, false);

        when(roundDao.getRoundByRoundIdTeamId(1, 1)).thenReturn(round);
        Round actual = roundServiceUnderTest.getRoundByRoundIdTeamId(1, 1);

        Round expected = new Round(roundId, 0, 0, false);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getRoundByRoundIdTeamId_teamDoesNotExist_negative() {

        when(roundDao.getRoundByRoundIdTeamId(1, 1)).thenReturn(null);

        Throwable actualExceptionThrown = Assertions.assertThrows(RoundDoesNotExistException.class, () -> roundServiceUnderTest.getRoundByRoundIdTeamId(1, 1));
        Assertions.assertEquals("No round with the round ID of 1 and team ID of 1", actualExceptionThrown.getMessage());
    }

    @Test
    void getRoundsByRoundIdGameId_positive() throws RoundDoesNotExistException {

        RoundId roundId = new RoundId(1, 1, 1);
        Round round = new Round(roundId, 0, 0, false);

        List<Round> roundList = new ArrayList<>();
        roundList.add(round);

        when(roundDao.getRoundsByRoundIdGameId(1, 1)).thenReturn(roundList);
        List<Round> actual = roundServiceUnderTest.getRoundsByRoundIdGameId(1, 1);

        List<Round> expected = new ArrayList<>();
        expected.add(round);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getRoundsByRoundIdGameId_gameDoesNotExist_negative() {

        List<Round> roundList = new ArrayList<>();

        when(roundDao.getRoundsByRoundIdGameId(1, 1)).thenReturn(roundList);

        Throwable actualExceptionThrown = Assertions.assertThrows(RoundDoesNotExistException.class, () -> roundServiceUnderTest.getRoundsByRoundIdGameId(1, 1));
        Assertions.assertEquals("No round with the round ID of 1 and game ID of 1", actualExceptionThrown.getMessage());
    }

    @Test
    void updateRoundByRoundIdTeamId_updateRoundScore_positive() throws InvalidParameterException, RoundDoesNotExistException {

        RoundId roundId = new RoundId(1, 1, 1);
        Round round = new Round(roundId, 0, 0, false);
        Round roundToUpdate = new Round(roundId, 100, 0, false);

        when(roundDao.getRoundByRoundIdTeamId(1, 1)).thenReturn(round);
        when(roundDao.updateRoundByRoundIdTeamId(roundToUpdate)).thenReturn(roundToUpdate);
        Round actual = roundServiceUnderTest.updateRoundByRoundIdTeamId(1, 1, "100", null, null);

        Round expected = new Round(roundId, 100, 0, false);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateRoundByRoundIdTeamId_updateSpinScore_positive() throws InvalidParameterException, RoundDoesNotExistException {

        RoundId roundId = new RoundId(1, 1, 1);
        Round round = new Round(roundId, 0, 0, false);
        Round roundToUpdate = new Round(roundId, 0, 100, false);

        when(roundDao.getRoundByRoundIdTeamId(1, 1)).thenReturn(round);
        when(roundDao.updateRoundByRoundIdTeamId(roundToUpdate)).thenReturn(roundToUpdate);
        Round actual = roundServiceUnderTest.updateRoundByRoundIdTeamId(1, 1, null, "100", null);

        Round expected = new Round(roundId, 0, 100, false);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateRoundByRoundIdTeamId_updateSpinToken_positive() throws InvalidParameterException, RoundDoesNotExistException {

        RoundId roundId = new RoundId(1, 1, 1);
        Round round = new Round(roundId, 0, 0, false);
        Round roundToUpdate = new Round(roundId, 0, 0, true);

        when(roundDao.getRoundByRoundIdTeamId(1, 1)).thenReturn(round);
        when(roundDao.updateRoundByRoundIdTeamId(roundToUpdate)).thenReturn(roundToUpdate);
        Round actual = roundServiceUnderTest.updateRoundByRoundIdTeamId(1, 1, null, null, "true");

        Round expected = new Round(roundId, 0, 0, true);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateRoundByRoundIdTeamId_updateAll_positive() throws InvalidParameterException, RoundDoesNotExistException {

        RoundId roundId = new RoundId(1, 1, 1);
        Round round = new Round(roundId, 0, 0, false);
        Round roundToUpdate = new Round(roundId, 100, 100, true);

        when(roundDao.getRoundByRoundIdTeamId(1, 1)).thenReturn(round);
        when(roundDao.updateRoundByRoundIdTeamId(roundToUpdate)).thenReturn(roundToUpdate);
        Round actual = roundServiceUnderTest.updateRoundByRoundIdTeamId(1, 1, "100", "100", "true");

        Round expected = new Round(roundId, 100, 100, true);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateRoundByRoundIdTeamId_roundScoreNotInt_negative() throws InvalidParameterException, RoundDoesNotExistException {

        RoundId roundId = new RoundId(1, 1, 1);
        Round round = new Round(roundId, 0, 0, false);

        when(roundDao.getRoundByRoundIdTeamId(1, 1)).thenReturn(round);

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> roundServiceUnderTest.updateRoundByRoundIdTeamId(1, 1, "ninety", null, null));
        Assertions.assertEquals("Round score must be whole number.", actualExceptionThrown.getMessage());
    }

    @Test
    void updateRoundByRoundIdTeamId_spinScoreNotInt_negative() throws InvalidParameterException, RoundDoesNotExistException {

        RoundId roundId = new RoundId(1, 1, 1);
        Round round = new Round(roundId, 0, 0, false);

        when(roundDao.getRoundByRoundIdTeamId(1, 1)).thenReturn(round);

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> roundServiceUnderTest.updateRoundByRoundIdTeamId(1, 1, null, "sixty", null));
        Assertions.assertEquals("Spin score must be whole number.", actualExceptionThrown.getMessage());
    }

    @Test
    void updateRoundByRoundIdTeamId_spinTokenNotBoolean_negative() throws InvalidParameterException, RoundDoesNotExistException {

        RoundId roundId = new RoundId(1, 1, 1);
        Round round = new Round(roundId, 0, 0, false);

        when(roundDao.getRoundByRoundIdTeamId(1, 1)).thenReturn(round);

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> roundServiceUnderTest.updateRoundByRoundIdTeamId(1, 1, null, null, "yes"));
        Assertions.assertEquals("Spin token must be either TRUE or FALSE.", actualExceptionThrown.getMessage());
    }

    @Test
    void updateRoundByRoundIdTeamId_allIntValuesNotInt_negative() throws InvalidParameterException, RoundDoesNotExistException {

        RoundId roundId = new RoundId(1, 1, 1);
        Round round = new Round(roundId, 0, 0, false);

        when(roundDao.getRoundByRoundIdTeamId(1, 1)).thenReturn(round);

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> roundServiceUnderTest.updateRoundByRoundIdTeamId(1, 1, "ninety", "sixty", null));
        Assertions.assertEquals("Round score, spin score must be whole number.", actualExceptionThrown.getMessage());
    }
}