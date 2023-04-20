package side.project.FHM.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import side.project.FHM.model.Game;
import side.project.FHM.model.Word;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Sql("/test-data.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class GameDaoUnitTest {

    @Autowired
    private GameDao gameDaoUnderTest;

    @Test
    void getAllGames_positive() {

        List<Game> actual = gameDaoUnderTest.getAllGames();

        Game game = new Game(1, "FHM Test", null, null, "STARTED", null, 1, 1, 2);

        List<Game> expected = new ArrayList<>();
        expected.add(game);

        Assertions.assertTrue(expected.containsAll(actual));
        Assertions.assertTrue(actual.containsAll(expected));
    }

    @Test
    @Sql("/test-delete-data.sql")
    void getAllGames_gameDoesNotExist_negative() {

        List<Game> actual = gameDaoUnderTest.getAllGames();
        List<Game> expected = new ArrayList<>();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addGame_positive() {

        Word word = new Word(1, "FRUIT", "MANGO");
        Game gameToAdd = new Game("FHM Test", 1, word, "STARTED", "FHM", 1, 1, 2);

        Game actual = gameDaoUnderTest.addGame(gameToAdd);
        Game expected = new Game(2, "FHM Test", 1, word, "STARTED", "FHM", 1, 1, 2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addGame_missingRoundId_positive() {

        Word word = new Word(1, "FRUIT", "MANGO");
        Game gameToAdd = new Game("FHM Test", null, word, "STARTED", "FHM", 1, 1, 2);

        Game actual = gameDaoUnderTest.addGame(gameToAdd);
        Game expected = new Game(2, "FHM Test", null, word, "STARTED", "FHM", 1, 1, 2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addGame_missingWord_positive() {

        Game gameToAdd = new Game("FHM Test", 1, null, "STARTED", "FHM", 1, 1, 2);

        Game actual = gameDaoUnderTest.addGame(gameToAdd);
        Game expected = new Game(2, "FHM Test", 1, null, "STARTED", "FHM", 1, 1, 2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addGame_missingCurrentTeamTurn_positive() {

        Word word = new Word(1, "FRUIT", "MANGO");
        Game gameToAdd = new Game("FHM Test", 1, word, "STARTED", "FHM", null, 1, 2);

        Game actual = gameDaoUnderTest.addGame(gameToAdd);
        Game expected = new Game(2, "FHM Test", 1, word, "STARTED", "FHM", null, 1, 2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addGame_missingCurrentRound_positive() {

        Word word = new Word(1, "FRUIT", "MANGO");
        Game gameToAdd = new Game("FHM Test", 1, word, "STARTED", "FHM", 1, null, 2);

        Game actual = gameDaoUnderTest.addGame(gameToAdd);
        Game expected = new Game(2, "FHM Test", 1, word, "STARTED", "FHM", 1, null, 2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addGame_missingTotalTeam_positive() {

        Word word = new Word(1, "FRUIT", "MANGO");
        Game gameToAdd = new Game("FHM Test", 1, word, "STARTED", "FHM", 1, 1, null);

        Game actual = gameDaoUnderTest.addGame(gameToAdd);
        Game expected = new Game(2, "FHM Test", 1, word, "STARTED", "FHM", 1, 1, null);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addGame_missingGameName_negative() {

        Game gameToAdd = new Game(null, null, null, "STARTED", null, 1, 1, 2);

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> gameDaoUnderTest.addGame(gameToAdd));
    }

    @Test
    void addGame_missingGameStatus_negative() {

        Game gameToAdd = new Game("FHM Test", null, null, null, null, 1, 1, 2);

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> gameDaoUnderTest.addGame(gameToAdd));
    }

    @Test
    void addGame_noGameInfo_negative() {

        Game gameToAdd = new Game();
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> gameDaoUnderTest.addGame(gameToAdd));
    }

    @Test
    void getGameByGameId_positive() {

        Game actual = gameDaoUnderTest.getGameByGameId(1);
        Game expected = new Game(1, "test", 1, null, "STARTED", null, 1, 1, 2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getGameByGameId_gameDoesNotExist_negative() {

        Game actual = gameDaoUnderTest.getGameByGameId(2);
        Assertions.assertNull(actual);
    }

    @Test
    void updateGameByGameId_updateRoundId_positive() {

        Game gameToUpdate = new Game(1, "test", 2, null, "STARTED", null, 1, 1, 2);

        Game actual = gameDaoUnderTest.updateGameByGameId(gameToUpdate);
        Game expected = new Game(1, "test", 2, null, "STARTED", null, 1, 1, 2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateGameByGameId_updateWordId_positive() {

        Word word = new Word(1, "FRUIT", "MANGO");
        Game gameToUpdate = new Game(1, "test", 1, word, "STARTED", null, 1, 1, 2);

        Game actual = gameDaoUnderTest.updateGameByGameId(gameToUpdate);
        Game expected = new Game(1, "test", 1, word, "STARTED", null, 1, 1, 2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateGameByGameId_updateGameStatus_positive() {

        Game gameToUpdate = new Game(1, "test", 1, null, "ENDED", null, 1, 1, 2);

        Game actual = gameDaoUnderTest.updateGameByGameId(gameToUpdate);
        Game expected = new Game(1, "test", 1, null, "ENDED", null, 1, 1, 2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateGameByGameId_updateLetterGuessed_positive() {

        Game gameToUpdate = new Game(1, "test", 1, null, "STARTED", "FHM", 1, 1, 2);

        Game actual = gameDaoUnderTest.updateGameByGameId(gameToUpdate);
        Game expected = new Game(1, "test", 1, null, "STARTED", "FHM", 1, 1, 2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateGameByGameId_updateCurrentTeamTurn_positive() {

        Game gameToUpdate = new Game(1, "test", 1, null, "STARTED", null, 2, 1, 2);

        Game actual = gameDaoUnderTest.updateGameByGameId(gameToUpdate);
        Game expected = new Game(1, "test", 1, null, "STARTED", null, 2, 1, 2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateGameByGameId_updateCurrentRound_positive() {

        Game gameToUpdate = new Game(1, "test", 1, null, "STARTED", null, 1, 2, 2);

        Game actual = gameDaoUnderTest.updateGameByGameId(gameToUpdate);
        Game expected = new Game(1, "test", 1, null, "STARTED", null, 1, 2, 2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateGameByGameId_updateGameId_negative() {

        Game gameToUpdate = new Game(1, "test", 1, null, "STARTED", null, 1, 1, 2);

        Game actual = gameDaoUnderTest.updateGameByGameId(gameToUpdate);
        Game expected = new Game(1, "test", 1, null, "STARTED", null, 1, 1, 2);

        Assertions.assertEquals(expected, actual);
    }
}