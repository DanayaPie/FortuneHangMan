package side.project.FHM.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import side.project.FHM.exception.GamesDoesNotExistException;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.exception.TeamDoesNotExistException;
import side.project.FHM.exception.WordDoesNotExistException;
import side.project.FHM.model.Game;
import side.project.FHM.model.Word;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Sql("/test-data.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class GameServiceIntegrationTest {

    @Autowired
    private GameService gameServiceUnderTest;

    @Test
    void getAllGames_positive() throws GamesDoesNotExistException {

        List<Game> actual = gameServiceUnderTest.getAllGames();

        Game game = new Game(1, "FHM Test", null, null, "STARTED", null, 1, 1, 2);

        List<Game> expected = new ArrayList<>();
        expected.add(game);

        Assertions.assertTrue(expected.containsAll(actual));
        Assertions.assertTrue(actual.containsAll(expected));
    }

    @Test
    @Sql("/test-delete-data.sql")
    void getAllGames_gameDoesNotExist_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(GamesDoesNotExistException.class, () -> gameServiceUnderTest.getAllGames());
        Assertions.assertEquals("No games on file.", actualExceptionThrown.getMessage());
    }

    @Test
    void addGame_positive() throws InvalidParameterException {

        Game actual = gameServiceUnderTest.addGame("FHM Test 2", "2");

        Game expected = new Game(2, "FHM Test 2", null, null, "STARTED", null, null, null, 2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addGame_totalTeamIsNotInt_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> gameServiceUnderTest.addGame("FHM Test", "Two"));
        Assertions.assertEquals("Total number of team must be a whole number between 2-4.", actualExceptionThrown.getMessage());
    }

    @Test
    void addGame_totalTeamIsLessThanTwo_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> gameServiceUnderTest.addGame("FHM Test", "1"));
        Assertions.assertEquals("The amount of teams have to be between 2-4.", actualExceptionThrown.getMessage());
    }

    @Test
    void addGame_totalTeamIsMoreThanFour_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> gameServiceUnderTest.addGame("FHM Test", "5"));
        Assertions.assertEquals("The amount of teams have to be between 2-4.", actualExceptionThrown.getMessage());
    }

    @Test
    void getGameByGameId_positive() throws GamesDoesNotExistException {

        Game actual = gameServiceUnderTest.getGameByGameId(1);

        Word word = new Word(1, "FRUIT", "MANGO");
        Game expected = new Game(1, "FHM Test", 1, word, "STARTED", "FHM", 1, 1, 2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Sql("/test-delete-data.sql")
    void getGameByGameId_gameDoesNotExist_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(GamesDoesNotExistException.class, () -> gameServiceUnderTest.getGameByGameId(1));
        Assertions.assertEquals("No game by the game ID of 1", actualExceptionThrown.getMessage());
    }

    @Test
    void updateGameByGameId_updateRoundId_positive() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Game actual = gameServiceUnderTest.updateGameByGameId(1, "2", null, null, null, null, null);

        Word word = new Word(1, "FRUIT", "MANGO");
        Game expected = new Game(1, "FHM Test", 2, word, "STARTED", "FHM", 1, 1, 2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateGameByGameId_updateWord_positive() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Game actual = gameServiceUnderTest.updateGameByGameId(1, null, "2", null, null, null, null);

        Word wordToUpdate = new Word(2, "ANIMAL", "CAT");
        Game expected = new Game(1, "FHM Test", 1, wordToUpdate, "STARTED", "FHM", 1, 1, 2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateGameByGameId_updateGameStatus_positive() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Game actual = gameServiceUnderTest.updateGameByGameId(1, null, null, "IN PROGRESS", null, null, null);

        Word word = new Word(1, "FRUIT", "MANGO");
        Game expected = new Game(1, "FHM Test", 1, word, "IN PROGRESS", "FHM", 1, 1, 2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateGameByGameId_updateLetterGuessed_positive() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Game actual = gameServiceUnderTest.updateGameByGameId(1, null, null, null, "FHMTES", null, null);

        Word word = new Word(1, "FRUIT", "MANGO");
        Game expected = new Game(1, "FHM Test", 1, word, "STARTED", "FHMTES", 1, 1, 2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateGameByGameId_updateCurrentTeamTurn_positive() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Game actual = gameServiceUnderTest.updateGameByGameId(1, null, null, null, null, "2", null);

        Word word = new Word(1, "FRUIT", "MANGO");
        Game expected = new Game(1, "FHM Test", 1, word, "STARTED", "FHM", 2, 1, 2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateGameByGameId_updateCurrentRound_positive() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Game actual = gameServiceUnderTest.updateGameByGameId(1, null, null, null, null, null, "2");

        Word word = new Word(1, "FRUIT", "MANGO");
        Game expected = new Game(1, "FHM Test", 1, word, "STARTED", "FHM", 1, 2, 2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateGameByGameId_updateAll_positive() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Game actual = gameServiceUnderTest.updateGameByGameId(1, "2", "2", "IN PROGRESS", "FHMTES", "2", "2");

        Word wordToUpdate = new Word(2, "ANIMAL", "CAT");
        Game expected = new Game(1, "FHM Test", 2, wordToUpdate, "IN PROGRESS", "FHMTES", 2, 2, 2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateGameByGameId_roundIdNotInt_negative() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> gameServiceUnderTest.updateGameByGameId(1, "two", null, null, null, null, null));
        Assertions.assertEquals("Round ID must be whole number.", actualExceptionThrown.getMessage());
    }

    @Test
    void updateGameByGameId_worIDNotInt_negative() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> gameServiceUnderTest.updateGameByGameId(1, null, "two", null, null, null, null));
        Assertions.assertEquals("Word ID must be whole number.", actualExceptionThrown.getMessage());
    }

    @Test
    void updateGameByGameId_gameStatusNotAllowed_negative() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> gameServiceUnderTest.updateGameByGameId(1, null, null, "DELETED", null, null, null));
        Assertions.assertEquals("Status must be either IN PROGRESS or ENDED.", actualExceptionThrown.getMessage());
    }

    @Test
    void updateGameByGameId_currentTeamTurnNotInt_negative() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> gameServiceUnderTest.updateGameByGameId(1, null, null, null, null, "two", null));
        Assertions.assertEquals("Current team turn must be whole number.", actualExceptionThrown.getMessage());
    }

    @Test
    void updateGameByGameId_currentRoundNotInt_negative() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> gameServiceUnderTest.updateGameByGameId(1, null, null, null, null, null, "two"));
        Assertions.assertEquals("Current round must be whole number.", actualExceptionThrown.getMessage());
    }

    @Test
    void updateGameByGameId_allIntValueNotInt_negative() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> gameServiceUnderTest.updateGameByGameId(1, "two", "two", null, null, "two", "two"));
        Assertions.assertEquals("Round ID, word ID, current team turn, current round must be whole number.", actualExceptionThrown.getMessage());
    }
}