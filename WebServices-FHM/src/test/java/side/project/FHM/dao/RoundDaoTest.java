package side.project.FHM.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import side.project.FHM.model.Round;
import side.project.FHM.model.RoundId;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@Sql("/test-data.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RoundDaoTest {

    @Autowired
    RoundDao roundDaoUnderTest;

    @Autowired
    @Qualifier("RoundDaoTest")
    RoundSeqDaoForTesting testRoundDaoUnderTest;

    @Test
    void getAllRounds_positive() {

        Set<Round> actual = roundDaoUnderTest.getAllRounds();

        RoundId roundId = new RoundId(1, 1, 1);
        Round round = new Round(roundId, 0, 0, false);

        Set<Round> expected = new HashSet<>();
        expected.add(round);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Sql("/test-delete-data.sql")
    void getAllRounds_roundDoesNotExist_negative() {

        Set<Round> actual = roundDaoUnderTest.getAllRounds();
        Set<Round> expected = new HashSet<>();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addRound_positive() {

        RoundId roundId = new RoundId(2, 2, 1);
        Round roundToAdd = new Round(roundId, 0, 0, false);

        Round actual = roundDaoUnderTest.addRound(roundToAdd);
        Round expected = new Round(roundId, 0, 0, false);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addRound_missingRoundId_negative() {

        Round roundToAdd = new Round(null, 0, 0, false);
        Assertions.assertThrows(JpaSystemException.class, () -> roundDaoUnderTest.addRound(roundToAdd));
    }

    @Test
    void addRound_noRoundInfo_negative() {

        Round roundToAdd = new Round();
        Assertions.assertThrows(JpaSystemException.class, () -> roundDaoUnderTest.addRound(roundToAdd));
    }

    @Test
    void getRoundsByRoundId_positive() {

        List<Round> actual = roundDaoUnderTest.getRoundsByRoundId(1);

        RoundId roundId = new RoundId(1, 1, 1);
        Round round = new Round(roundId, 0, 0, false);

        List<Round> expected = new ArrayList<>();
        expected.add(round);

        Assertions.assertTrue(expected.containsAll(actual));
        Assertions.assertTrue(actual.containsAll(expected));
    }

    @Test
    void getRoundsByRoundId_roundDoesNotExist_negative() {

        List<Round> actual = roundDaoUnderTest.getRoundsByRoundId(2);
        List<Round> expected = new ArrayList<>();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getRoundByRoundIdTeamId_positive() {

        Round actual = roundDaoUnderTest.getRoundByRoundIdTeamId(1, 1);

        RoundId roundId = new RoundId(1, 1, 1);
        Round expected = new Round(roundId, 0, 0, false);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getRoundByRoundIdTeamId_roundIdDoesNotExist_negative() {

        Round actual = roundDaoUnderTest.getRoundByRoundIdTeamId(2, 1);
        Assertions.assertNull(actual);
    }

    @Test
    void getRoundByRoundIdTeamId_teamIdDoesNotExist_negative() {

        Round actual = roundDaoUnderTest.getRoundByRoundIdTeamId(1, 3);
        Assertions.assertNull(actual);
    }

    @Test
    void getRoundsByRoundIdGameId_positive() {

        List<Round> actual = roundDaoUnderTest.getRoundsByRoundIdGameId(1, 1);

        RoundId roundId = new RoundId(1, 1, 1);
        Round round = new Round(roundId, 0, 0, false);

        List<Round> expected = new ArrayList<>();
        expected.add(round);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getRoundsByRoundIdGameId_roundIdDoesNotExist_negative() {

        List<Round> actual = roundDaoUnderTest.getRoundsByRoundIdGameId(2, 1);
        List<Round> expected = new ArrayList<>();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getRoundsByRoundIdGameId_gameIdDoesNotExist_negative() {

        List<Round> actual = roundDaoUnderTest.getRoundsByRoundIdGameId(1, 2);
        List<Round> expected = new ArrayList<>();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateRoundByRoundIdTeamId_updateRoundScore_positive() {

        RoundId roundId = new RoundId(1, 1, 1);
        Round roundToUpdate = new Round(roundId, 100, 0, false);

        Round actual = roundDaoUnderTest.updateRoundByRoundIdTeamId(roundToUpdate);

        Round expected = new Round(roundId, 100, 0, false);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateRoundByRoundIdTeamId_updateSpinScore_positive() {

        RoundId roundId = new RoundId(1, 1, 1);
        Round roundToUpdate = new Round(roundId, 0, 100, false);

        Round actual = roundDaoUnderTest.updateRoundByRoundIdTeamId(roundToUpdate);

        Round expected = new Round(roundId, 0, 100, false);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateRoundByRoundIdTeamId_updateSpinToken_positive() {

        RoundId roundId = new RoundId(1, 1, 1);
        Round roundToUpdate = new Round(roundId, 0, 100, true);

        Round actual = roundDaoUnderTest.updateRoundByRoundIdTeamId(roundToUpdate);

        Round expected = new Round(roundId, 0, 100, true);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Sql("/test-roundSeq.sql")
    void getSequenceId_positive() {

        Long actual = testRoundDaoUnderTest.getSequenceId();
        int expected = 2;

        Assertions.assertEquals(expected, actual);
    }
}