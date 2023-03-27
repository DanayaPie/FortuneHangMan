package side.project.FHM.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import side.project.FHM.dao.GameDao;
import side.project.FHM.exception.GamesDoesNotExist;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.exception.WordDoesNotExist;
import side.project.FHM.model.Game;
import side.project.FHM.model.Word;
import side.project.FHM.utility.ValidateGame;

import java.util.List;
import java.util.Locale;

@Service
public class GameService {

    private Logger logger = LoggerFactory.getLogger(GameService.class);

    @Autowired
    private GameDao gameDao;

    @Autowired
    private WordService wordService;

    public List<Game> getAllGames() throws GamesDoesNotExist, InvalidParameterException {
        logger.info("GameService.getAllGames() invoked");

        List<Game> allGames = gameDao.getAllGames();

        try {
            if (allGames.isEmpty()) {
                throw new GamesDoesNotExist("No games on file.");
            }
            return allGames;

        } catch (DataAccessException e) {
            throw new InvalidParameterException("No games on file.");
        }
    }

    public Game addGame(String gameName, String totalTeam) throws InvalidParameterException {
        logger.info("GameService.addGame() invoked");

        gameName = gameName.trim();

        int totalTeamNumber;
        String gameStatus = "STARTED";

        try {
            totalTeamNumber = Integer.parseInt(totalTeam.trim());
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Total number of team must be a whole number.");
        }

        if (totalTeamNumber < 2 || totalTeamNumber > 4) {
            throw new InvalidParameterException("The amount of players have to be between 2-4.");
        }

        Game addedGame = new Game();
        addedGame.setGameName(gameName);
        addedGame.setGameStatus(gameStatus);
        addedGame.setTotalTeam(totalTeamNumber);

        gameDao.addGame(addedGame);
        return addedGame;
    }

    public Game getGameByGameId(int gameId) throws InvalidParameterException {
        logger.info("GameService.getGameByGameId() invoked");

        Game gameToGet = gameDao.getGameByGameId(gameId);

        try {
            if (gameToGet == null) {
                throw new InvalidParameterException("No game by the game ID of " + gameId);
            }
            return gameToGet;

        } catch (DataAccessException e) {
            throw new InvalidParameterException("No game by the game ID of " + gameId);
        }
    }

    public Game updateGameByGameId(int gameId, String roundId, String wordId, String gameStatus, String letterGuessed, String currentTeamTurn, String currentRound) throws InvalidParameterException {
        logger.info("GameService.updateGameByGameId() invoked");

        Game gameToUpdate = new Game();
        gameToUpdate = getGameByGameId(gameId);

        /*
            parse int inputs and set gameToUpdate instance
         */
        boolean gameInputsIntegerErrorBoolean = false;
        StringBuilder gameInputsIntegerErrorString = new StringBuilder();

        try {

            // round ID
            if (roundId != null) {
                logger.info("Updating round ID");

                if (roundId.matches("^[0-9]*$")) {
                    // set round ID
                    int roundIdNumber = Integer.parseInt(roundId.trim());
                    gameToUpdate.setRoundId(roundIdNumber);

                } else {
                    logger.info("Round ID is not an int");

                    gameInputsIntegerErrorString.append("Round ID");
                    gameInputsIntegerErrorBoolean = true;
                }

            }

            // word ID
            if (wordId != null) {
                logger.info("Updating word ID");

                if (wordId.matches("[0-9]+")) {
                    // set word
                    int wordIdNumber = Integer.parseInt(wordId.trim());
                    Word wordToGet = wordService.getWordByWordId(wordIdNumber);
                    gameToUpdate.setWord(wordToGet);

                } else {
                    logger.info("Word ID is not an int");

                    if (gameInputsIntegerErrorBoolean) {
                        gameInputsIntegerErrorString.append(", word ID");
                    } else {
                        gameInputsIntegerErrorString.append("Word ID");
                    }
                    gameInputsIntegerErrorBoolean = true;
                }
            }

            // current team turn
            if (currentTeamTurn != null) {
                logger.info("Updating current team turn");

                if (currentTeamTurn.matches("[0-9]+")) {
                    // set current team turn
                    int currentTeamTurnNumber = Integer.parseInt(currentTeamTurn.trim());
                    gameToUpdate.setCurrentTeamTurn(currentTeamTurnNumber);

                } else {
                    logger.info("Current team turn is not an int");

                    if (gameInputsIntegerErrorBoolean) {
                        gameInputsIntegerErrorString.append(", current team turn");
                    } else {
                        gameInputsIntegerErrorString.append("Current team turn");
                    }
                    gameInputsIntegerErrorBoolean = true;
                }
            }

            // current round
            if (currentRound != null) {
                logger.info("Update current round");

                if (currentRound.matches("[0-9]+")) {
                    // set current round
                    int currentRoundNumber = Integer.parseInt(currentRound.trim());
                    gameToUpdate.setCurrentRound(currentRoundNumber);

                } else {
                    logger.info("Current round ID is not an int");

                    if (gameInputsIntegerErrorBoolean) {
                        gameInputsIntegerErrorString.append(", current round");
                    } else {
                        gameInputsIntegerErrorString.append("Current round");
                    }
                    gameInputsIntegerErrorBoolean = true;
                }
            }

            // append int error message
            if (gameInputsIntegerErrorBoolean) {
                gameInputsIntegerErrorString.append(" must be whole number.");
                throw new NumberFormatException(gameInputsIntegerErrorString.toString());
            }

        } catch (NumberFormatException | WordDoesNotExist e) {
            throw new InvalidParameterException(gameInputsIntegerErrorString.toString());
        }

        /*
            validate and set game status
         */
        if (gameStatus != null) {
            logger.info("Updating game status");

            String gameStatusInput = gameStatus.toUpperCase(Locale.ROOT);
            ValidateGame.validateGameStatus(gameStatusInput);

            // set game status
            gameToUpdate.setGameStatus(gameStatusInput);
        }

        /*
            set letter guessed of gameToUpdate instance
         */
        if (letterGuessed != null) {
            logger.info("Updating letter guessed");

            String letterGuessedStr;
            StringBuilder letterGuessedStrB = new StringBuilder();
            String letterAlreadyGuessed = gameToUpdate.getLetterGuessed();

            if (letterAlreadyGuessed != null) {
                letterGuessedStrB.append(letterAlreadyGuessed);
            }

            letterGuessedStrB.append(letterGuessed);
            letterGuessedStr = letterGuessedStrB.toString();
            gameToUpdate.setLetterGuessed(letterGuessedStr);
        }

        Game updatedGame = gameDao.updateGameByGameId(gameToUpdate);
        return updatedGame;
    }
}
