package side.project.FHM.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import side.project.FHM.dao.GameDao;
import side.project.FHM.exception.GamesDoesNotExistException;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.exception.TeamDoesNotExistException;
import side.project.FHM.exception.WordDoesNotExistException;
import side.project.FHM.model.Game;
import side.project.FHM.model.Team;
import side.project.FHM.model.Word;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class GameServiceUnitTest {

    @InjectMocks
    private GameService gameServiceUnderTest;

    @Mock
    private GameDao gameDao;

    @Mock
    private WordService wordService;

    @Mock
    private TeamService teamService;

    @Test
    void getAllGames_positive() throws GamesDoesNotExistException {

        Game game = new Game(1, "FHM Test", null, null, "STARTED", null, 1, 1, 2);

        List<Game> gameList = new ArrayList<>();
        gameList.add(game);

        when(gameDao.getAllGames()).thenReturn(gameList);
        List<Game> actual = gameServiceUnderTest.getAllGames();

        List<Game> expected = new ArrayList<>();
        expected.add(game);

        Assertions.assertTrue(expected.containsAll(actual));
        Assertions.assertTrue(actual.containsAll(expected));
    }

    @Test
    void getAllGames_gameDoesNotExist_negative() {

        List<Game> gameList = new ArrayList<>();
        when(gameDao.getAllGames()).thenReturn(gameList);

        Throwable actualExceptionThrown = Assertions.assertThrows(GamesDoesNotExistException.class, () -> gameServiceUnderTest.getAllGames());
        Assertions.assertEquals("No games on file.", actualExceptionThrown.getMessage());
    }

    @Test
    void addGame_positive() throws InvalidParameterException {

        Word word = new Word(1, "FRUIT", "MANGO");
        Game gameToAdd = new Game("FHM Test", 1, word, "STARTED", "FHM", 1, 1, 2);

        when(gameDao.addGame(gameToAdd)).thenReturn(gameToAdd);
        Game actual = gameServiceUnderTest.addGame("FHM Test", "2");
        actual.setGameId(1);

        Game expected = new Game(1, "FHM Test", 1, word, "STARTED", "FHM", 1, 1, 2);
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

        Word word = new Word(1, "FRUIT", "MANGO");
        Game game = new Game(1, "FHM Test", 1, word, "STARTED", "FHM", 1, 1, 2);

        when(gameDao.getGameByGameId(1)).thenReturn(game);
        Game actual = gameServiceUnderTest.getGameByGameId(1);

        Game expected = new Game(1, "FHM Test", 1, word, "STARTED", "FHM", 1, 1, 2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getGameByGameId_gameDoesNotExist_negative() {

        when(gameDao.getGameByGameId(1)).thenReturn(null);

        Throwable actualExceptionThrown = Assertions.assertThrows(GamesDoesNotExistException.class, () -> gameServiceUnderTest.getGameByGameId(1));
        Assertions.assertEquals("No game by the game ID of 1", actualExceptionThrown.getMessage());
    }

    @Test
    void updateGameByGameId_updateRoundId_positive() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Word word = new Word(1, "FRUIT", "MANGO");
        Game game = new Game(1, "FHM Test", 1, word, "STARTED", "FHM", 1, 1, 2);
        Game gameToUpdate = new Game(1, "FHM Test", 2, word, "STARTED", "FHM", 1, 1, 2);

        when(gameDao.getGameByGameId(1)).thenReturn(game);
        when(gameDao.updateGameByGameId(gameToUpdate)).thenReturn(gameToUpdate);

        Game actual = gameServiceUnderTest.updateGameByGameId(1, "2", null, null, null, null, null);

        Game expected = new Game(1, "FHM Test", 2, word, "STARTED", "FHM", 1, 1, 2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateGameByGameId_updateWord_positive() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Word word = new Word(1, "FRUIT", "MANGO");
        Word wordToUpdate = new Word(2, "ANIMAL", "CAT");
        Game game = new Game(1, "FHM Test", 1, word, "STARTED", "FHM", 1, 1, 2);
        Game gameToUpdate = new Game(1, "FHM Test", 2, word, "STARTED", "FHM", 1, 1, 2);

        when(gameDao.getGameByGameId(1)).thenReturn(game);
        when(wordService.getWordByWordId(2)).thenReturn(wordToUpdate);
        when(gameDao.updateGameByGameId(gameToUpdate)).thenReturn(gameToUpdate);

        Game actual = gameServiceUnderTest.updateGameByGameId(1, null, "2", null, null, null, null);

        Game expected = new Game(1, "FHM Test", 1, wordToUpdate, "STARTED", "FHM", 1, 1, 2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateGameByGameId_updateGameStatus_positive() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Word word = new Word(1, "FRUIT", "MANGO");
        Game game = new Game(1, "FHM Test", 1, word, "STARTED", "FHM", 1, 1, 2);
        Game gameToUpdate = new Game(1, "FHM Test", 2, word, "STARTED", "FHM", 1, 1, 2);

        when(gameDao.getGameByGameId(1)).thenReturn(game);
        when(gameDao.updateGameByGameId(gameToUpdate)).thenReturn(gameToUpdate);

        Game actual = gameServiceUnderTest.updateGameByGameId(1, null, null, "IN PROGRESS", null, null, null);

        Game expected = new Game(1, "FHM Test", 1, word, "IN PROGRESS", "FHM", 1, 1, 2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateGameByGameId_updateLetterGuessed_positive() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Word word = new Word(1, "FRUIT", "MANGO");
        Game game = new Game(1, "FHM Test", 1, word, "STARTED", "FHM", 1, 1, 2);
        Game gameToUpdate = new Game(1, "FHM Test", 2, word, "STARTED", "FHM", 1, 1, 2);

        when(gameDao.getGameByGameId(1)).thenReturn(game);
        when(gameDao.updateGameByGameId(gameToUpdate)).thenReturn(gameToUpdate);

        Game actual = gameServiceUnderTest.updateGameByGameId(1, null, null, null, "FHMTES", null, null);

        Game expected = new Game(1, "FHM Test", 1, word, "STARTED", "FHMTES", 1, 1, 2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateGameByGameId_updateCurrentTeamTurn_positive() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Word word = new Word(1, "FRUIT", "MANGO");
        Team team = new Team(2, "Donkey", 2, 1, 0);
        Game game = new Game(1, "FHM Test", 1, word, "STARTED", "FHM", 1, 1, 2);
        Game gameToUpdate = new Game(1, "FHM Test", 2, word, "STARTED", "FHM", 1, 1, 2);

        when(gameDao.getGameByGameId(1)).thenReturn(game);
        when(teamService.getTeamsByGameIdCurrentTeamTurn(1, 2)).thenReturn(team);
        when(gameDao.updateGameByGameId(gameToUpdate)).thenReturn(gameToUpdate);

        Game actual = gameServiceUnderTest.updateGameByGameId(1, null, null, null, null, "2", null);

        Game expected = new Game(1, "FHM Test", 1, word, "STARTED", "FHM", 2, 1, 2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateGameByGameId_updateCurrentRound_positive() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Word word = new Word(1, "FRUIT", "MANGO");
        Game game = new Game(1, "FHM Test", 1, word, "STARTED", "FHM", 1, 1, 2);
        Game gameToUpdate = new Game(1, "FHM Test", 2, word, "STARTED", "FHM", 1, 1, 2);

        when(gameDao.getGameByGameId(1)).thenReturn(game);
        when(gameDao.updateGameByGameId(gameToUpdate)).thenReturn(gameToUpdate);

        Game actual = gameServiceUnderTest.updateGameByGameId(1, null, null, null, null, null, "2");

        Game expected = new Game(1, "FHM Test", 1, word, "STARTED", "FHM", 1, 2, 2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateGameByGameId_updateAll_positive() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Word word = new Word(1, "FRUIT", "MANGO");
        Word wordToUpdate = new Word(2, "ANIMAL", "CAT");
        Team team = new Team(2, "Donkey", 2, 1, 0);
        Game game = new Game(1, "FHM Test", 1, word, "STARTED", "FHM", 1, 1, 2);
        Game gameToUpdate = new Game(1, "FHM Test", 2, word, "STARTED", "FHM", 1, 1, 2);

        when(wordService.getWordByWordId(2)).thenReturn(wordToUpdate);
        when(teamService.getTeamsByGameIdCurrentTeamTurn(1, 2)).thenReturn(team);
        when(gameDao.getGameByGameId(1)).thenReturn(game);
        when(gameDao.updateGameByGameId(gameToUpdate)).thenReturn(gameToUpdate);

        Game actual = gameServiceUnderTest.updateGameByGameId(1, "2", "2", "IN PROGRESS", "FHMTES", "2", "2");

        Game expected = new Game(1, "FHM Test", 2, wordToUpdate, "IN PROGRESS", "FHMTES", 2, 2, 2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateGameByGameId_roundIdNotInt_negative() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Word word = new Word(1, "FRUIT", "MANGO");
        Game game = new Game(1, "FHM Test", 1, word, "STARTED", "FHM", 1, 1, 2);

        when(gameDao.getGameByGameId(1)).thenReturn(game);

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> gameServiceUnderTest.updateGameByGameId(1, "two", null, null, null, null, null));
        Assertions.assertEquals("Round ID must be whole number.", actualExceptionThrown.getMessage());
    }

    @Test
    void updateGameByGameId_worIDNotInt_negative() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Word word = new Word(1, "FRUIT", "MANGO");
        Game game = new Game(1, "FHM Test", 1, word, "STARTED", "FHM", 1, 1, 2);

        when(gameDao.getGameByGameId(1)).thenReturn(game);

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> gameServiceUnderTest.updateGameByGameId(1, null, "two", null, null, null, null));
        Assertions.assertEquals("Word ID must be whole number.", actualExceptionThrown.getMessage());
    }

    @Test
    void updateGameByGameId_gameStatusNotAllowed_negative() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Word word = new Word(1, "FRUIT", "MANGO");
        Game game = new Game(1, "FHM Test", 1, word, "STARTED", "FHM", 1, 1, 2);

        when(gameDao.getGameByGameId(1)).thenReturn(game);

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> gameServiceUnderTest.updateGameByGameId(1, null, null, "DELETED", null, null, null));
        Assertions.assertEquals("Status must be either IN PROGRESS or ENDED.", actualExceptionThrown.getMessage());
    }

    @Test
    void updateGameByGameId_currentTeamTurnNotInt_negative() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Word word = new Word(1, "FRUIT", "MANGO");
        Game game = new Game(1, "FHM Test", 1, word, "STARTED", "FHM", 1, 1, 2);

        when(gameDao.getGameByGameId(1)).thenReturn(game);

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> gameServiceUnderTest.updateGameByGameId(1, null, null, null, null, "two", null));
        Assertions.assertEquals("Current team turn must be whole number.", actualExceptionThrown.getMessage());
    }

    @Test
    void updateGameByGameId_currentRoundNotInt_negative() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Word word = new Word(1, "FRUIT", "MANGO");
        Game game = new Game(1, "FHM Test", 1, word, "STARTED", "FHM", 1, 1, 2);

        when(gameDao.getGameByGameId(1)).thenReturn(game);

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> gameServiceUnderTest.updateGameByGameId(1, null, null, null, null, null, "two"));
        Assertions.assertEquals("Current round must be whole number.", actualExceptionThrown.getMessage());
    }

    @Test
    void updateGameByGameId_allIntValueNotInt_negative() throws GamesDoesNotExistException, InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException {

        Word word = new Word(1, "FRUIT", "MANGO");
        Game game = new Game(1, "FHM Test", 1, word, "STARTED", "FHM", 1, 1, 2);

        when(gameDao.getGameByGameId(1)).thenReturn(game);

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> gameServiceUnderTest.updateGameByGameId(1, "two", "two", null, null, "two", "two"));
        Assertions.assertEquals("Round ID, word ID, current team turn, current round must be whole number.", actualExceptionThrown.getMessage());
    }
}