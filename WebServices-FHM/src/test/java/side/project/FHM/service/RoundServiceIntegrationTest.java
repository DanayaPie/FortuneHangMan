package side.project.FHM.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import side.project.FHM.dao.RoundDao;
import side.project.FHM.exception.GamesDoesNotExistException;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.exception.RoundDoesNotExistException;
import side.project.FHM.exception.TeamDoesNotExistException;
import side.project.FHM.model.Round;
import side.project.FHM.model.RoundId;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@Sql("/test-data.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RoundServiceIntegrationTest {

    @Autowired
    private RoundService roundServiceUnderTest;

    @Autowired
    private RoundDao roundDao;

    @Autowired
    @Qualifier("RoundServiceTest")
    private RoundSeqServiceForTesting roundSeqServiceForTesting;

    @Test
    void getAllRounds_positive() throws RoundDoesNotExistException {

        Set<Round> actual = roundServiceUnderTest.getAllRounds();

        RoundId roundId = new RoundId(1, 1, 1);
        Round round = new Round(roundId, 0, 0, false);

        Set<Round> expected = new HashSet<>();
        expected.add(round);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Sql("/test-delete-data.sql")
    void getAllRounds_roundDoesNotExist_negative() throws RoundDoesNotExistException {

        Throwable actualExceptionThrown = Assertions.assertThrows(RoundDoesNotExistException.class, () -> roundServiceUnderTest.getAllRounds());
        Assertions.assertEquals("No rounds on file.", actualExceptionThrown.getMessage());
    }

    @Test
    @Sql("/testRoundService-roundSeq.sql")
    void addRound_positive() throws InvalidParameterException, TeamDoesNotExistException, GamesDoesNotExistException {

        List<Integer> teamIds = new ArrayList<>();
        teamIds.add(2);

        List<Round> actual = roundSeqServiceForTesting.addRound(teamIds, 1);

        RoundId roundId = new RoundId(2, 2, 1);
        Round roundToAdd = new Round(roundId, 0, 0, false);

        List<Round> expected = new ArrayList<>();
        expected.add(roundToAdd);

        Assertions.assertTrue(expected.containsAll(actual));
        Assertions.assertTrue(actual.containsAll(expected));
    }

    @Test
    void getRoundsByRoundId_positive() throws RoundDoesNotExistException {

        List<Round> actual = roundServiceUnderTest.getRoundsByRoundId(1);

        RoundId roundId = new RoundId(1, 1, 1);
        Round round = new Round(roundId, 0, 0, false);

        List<Round> expected = new ArrayList<>();
        expected.add(round);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Sql("/test-delete-data.sql")
    void getRoundsByRoundId_roundDoesNotExist_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(RoundDoesNotExistException.class, () -> roundServiceUnderTest.getRoundsByRoundId(1));
        Assertions.assertEquals("No rounds with the round ID of 1", actualExceptionThrown.getMessage());
    }

    @Test
    void getRoundByRoundIdTeamId_positive() throws RoundDoesNotExistException {

        Round actual = roundServiceUnderTest.getRoundByRoundIdTeamId(1, 1);

        RoundId roundId = new RoundId(1, 1, 1);
        Round expected = new Round(roundId, 0, 0, false);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Sql("/test-delete-data.sql")
    void getRoundByRoundIdTeamId_teamDoesNotExist_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(RoundDoesNotExistException.class, () -> roundServiceUnderTest.getRoundByRoundIdTeamId(1, 1));
        Assertions.assertEquals("No round with the round ID of 1 and team ID of 1", actualExceptionThrown.getMessage());
    }

    @Test
    void getRoundsByRoundIdGameId_positive() throws RoundDoesNotExistException {

        List<Round> actual = roundServiceUnderTest.getRoundsByRoundIdGameId(1, 1);

        RoundId roundId = new RoundId(1, 1, 1);
        Round round = new Round(roundId, 0, 0, false);

        List<Round> expected = new ArrayList<>();
        expected.add(round);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Sql("/test-delete-data.sql")
    void getRoundsByRoundIdGameId_gameDoesNotExist_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(RoundDoesNotExistException.class, () -> roundServiceUnderTest.getRoundsByRoundIdGameId(1, 1));
        Assertions.assertEquals("No round with the round ID of 1 and game ID of 1", actualExceptionThrown.getMessage());
    }

    @Test
    void updateRoundByRoundIdTeamId_updateRoundScore_positive() throws InvalidParameterException, RoundDoesNotExistException {

        Round actual = roundServiceUnderTest.updateRoundByRoundIdTeamId(1, 1, "100", null, null);

        RoundId roundId = new RoundId(1, 1, 1);
        Round expected = new Round(roundId, 100, 0, false);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateRoundByRoundIdTeamId_updateSpinScore_positive() throws InvalidParameterException, RoundDoesNotExistException {

        Round actual = roundServiceUnderTest.updateRoundByRoundIdTeamId(1, 1, null, "100", null);

        RoundId roundId = new RoundId(1, 1, 1);
        Round expected = new Round(roundId, 0, 100, false);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateRoundByRoundIdTeamId_updateSpinToken_positive() throws InvalidParameterException, RoundDoesNotExistException {

        Round actual = roundServiceUnderTest.updateRoundByRoundIdTeamId(1, 1, null, null, "true");

        RoundId roundId = new RoundId(1, 1, 1);
        Round expected = new Round(roundId, 0, 0, true);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateRoundByRoundIdTeamId_updateAll_positive() throws InvalidParameterException, RoundDoesNotExistException {

        Round actual = roundServiceUnderTest.updateRoundByRoundIdTeamId(1, 1, "100", "100", "true");

        RoundId roundId = new RoundId(1, 1, 1);
        Round expected = new Round(roundId, 100, 100, true);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateRoundByRoundIdTeamId_roundScoreNotInt_negative() throws InvalidParameterException, RoundDoesNotExistException {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> roundServiceUnderTest.updateRoundByRoundIdTeamId(1, 1, "ninety", null, null));
        Assertions.assertEquals("Round score must be whole number.", actualExceptionThrown.getMessage());
    }

    @Test
    void updateRoundByRoundIdTeamId_spinScoreNotInt_negative() throws InvalidParameterException, RoundDoesNotExistException {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> roundServiceUnderTest.updateRoundByRoundIdTeamId(1, 1, null, "sixty", null));
        Assertions.assertEquals("Spin score must be whole number.", actualExceptionThrown.getMessage());
    }

    @Test
    void updateRoundByRoundIdTeamId_spinTokenNotBoolean_negative() throws InvalidParameterException, RoundDoesNotExistException {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> roundServiceUnderTest.updateRoundByRoundIdTeamId(1, 1, null, null, "yes"));
        Assertions.assertEquals("Spin token must be either TRUE or FALSE.", actualExceptionThrown.getMessage());
    }

    @Test
    void updateRoundByRoundIdTeamId_allIntValuesNotInt_negative() throws InvalidParameterException, RoundDoesNotExistException {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> roundServiceUnderTest.updateRoundByRoundIdTeamId(1, 1, "ninety", "sixty", null));
        Assertions.assertEquals("Round score, spin score must be whole number.", actualExceptionThrown.getMessage());
    }
}