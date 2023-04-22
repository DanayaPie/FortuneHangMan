package side.project.FHM.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import side.project.FHM.dao.GameDao;
import side.project.FHM.exception.GamesDoesNotExistException;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.exception.TeamDoesNotExistException;
import side.project.FHM.exception.WordDoesNotExistException;
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

    @Autowired
    private TeamService teamService;

    public List<Game> getAllGames() throws GamesDoesNotExistException {
        logger.info("GameService.getAllGames() invoked");

        List<Game> allGames = gameDao.getAllGames();

        if (allGames.isEmpty()) {
            throw new GamesDoesNotExistException("No games on file.");
        }
        return allGames;
    }

    public Game addGame(String gameName, String totalTeam) throws InvalidParameterException {
        logger.info("GameService.addGame() invoked");

        ValidateGame.validateGameNameTotalTeamBlank(gameName, totalTeam);

        gameName = gameName.trim();

        int totalTeamNumber;
        String gameStatus = "STARTED";

        try {
            totalTeamNumber = Integer.parseInt(totalTeam.trim());
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Total number of team must be a whole number between 2-4.");
        }

        if (totalTeamNumber < 2 || totalTeamNumber > 4) {
            throw new InvalidParameterException("The amount of teams have to be between 2-4.");
        }

        Game addedGame = new Game();
        addedGame.setGameName(gameName);
        addedGame.setGameStatus(gameStatus);
        addedGame.setTotalTeam(totalTeamNumber);

        gameDao.addGame(addedGame);
        return addedGame;
    }

    public Game getGameByGameId(int gameId) throws GamesDoesNotExistException {
        logger.info("GameService.getGameByGameId() invoked");

        Game gameToGet = gameDao.getGameByGameId(gameId);

        if (gameToGet == null) {
            throw new GamesDoesNotExistException("No game by the game ID of " + gameId);
        }
        return gameToGet;
    }

    public Game updateGameByGameId(int gameId, String roundId, String wordId, String gameStatus, String letterGuessed, String currentTeamTurn, String currentRound) throws InvalidParameterException, WordDoesNotExistException, TeamDoesNotExistException, GamesDoesNotExistException {
        logger.info("GameService.updateGameByGameId() invoked");

        Game gameToUpdate = getGameByGameId(gameId);
        Integer roundIdNumber;
        Integer wordIdNumber = null;
        Integer currentTeamTurnNumber = null;
        Word wordToGet;
        /*
            parse int inputs and set gameToUpdate instance
         */
        boolean gameInputsIntegerErrorBoolean = false;
        StringBuilder gameInputsIntegerErrorString = new StringBuilder();

//        try {

        // round ID
        if (roundId != null) {
            logger.info("Parse and update round ID");

            if (roundId.matches("^[0-9]*$")) {
                // parse round ID
                roundIdNumber = Integer.parseInt(roundId.trim());
                gameToUpdate.setRoundId(roundIdNumber);

            } else {
                logger.info("Round ID is not an int");

                gameInputsIntegerErrorString.append("Round ID");
                gameInputsIntegerErrorBoolean = true;
            }
        }

        // word ID
        if (wordId != null) {
            logger.info("Parsing word ID");

            if (wordId.matches("[0-9]+")) {
                // parse word ID
                wordIdNumber = Integer.parseInt(wordId.trim());
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
            logger.info("Parsing current team turn");

            if (currentTeamTurn.matches("[0-9]+")) {
                // parse current team turn
                currentTeamTurnNumber = Integer.parseInt(currentTeamTurn.trim());
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
            logger.info("Parse and update current round");

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
            throw new InvalidParameterException(gameInputsIntegerErrorString.toString());
        }
//
//        } catch (NumberFormatException e) {
//            throw new InvalidParameterException(gameInputsIntegerErrorString.toString());
//        }

        /*
            set word
         */
        if (wordId != null) {
            logger.info("Updating word ID");

            wordToGet = wordService.getWordByWordId(wordIdNumber);
            gameToUpdate.setWord(wordToGet);
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
            set letter guessed
         */
        if (letterGuessed != null) {
            logger.info("Updating letter guessed");

            /*
                UPDATE letterGuessed as STRING
             */
//            String letterGuessedStr;
//            StringBuilder letterGuessedStrB = new StringBuilder();
//            String letterAlreadyGuessed = gameToUpdate.getLetterGuessed();
//
//            if (letterAlreadyGuessed != null) {
//                letterGuessedStrB.append(letterAlreadyGuessed);
//            }
//
//            letterGuessedStrB.append(letterGuessed);
//            letterGuessedStr = letterGuessedStrB.toString();
//            gameToUpdate.setLetterGuessed(letterGuessedStr);

            gameToUpdate.setLetterGuessed(letterGuessed);
        }

        /*
            set current team turn
         */
        if (currentTeamTurn != null) {
            logger.info("Updating current team turn");

            teamService.getTeamsByGameIdCurrentTeamTurn(gameId, currentTeamTurnNumber);
            gameToUpdate.setCurrentTeamTurn(currentTeamTurnNumber);
        }

        Game updatedGame = gameDao.updateGameByGameId(gameToUpdate);
        return updatedGame;
    }
}
