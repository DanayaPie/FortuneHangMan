package side.project.FHM.utility;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.service.GameService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ValidateGame {

    private static Logger logger = LoggerFactory.getLogger(ValidateGame.class);
    private static Set<String> validGameStatus = new HashSet<>(Arrays.asList("IN PROGRESS", "ENDED"));

    @Autowired
    private GameService gameService;

    public static void validateGameStatus(String gameStatusInput) throws InvalidParameterException {
        logger.info("ValidateGame.validateGameStatus() invoked");

        if (!validGameStatus.contains(gameStatusInput)) {
            throw new InvalidParameterException("Status must be either IN PROGRESS or ENDED.");
        }
    }

    public static void gameNameAndTotalTeamBlank(String gameName, String totalTeam) throws InvalidParameterException {
        logger.info("ValidateGame.gameNameAndTotalTeamBlank() invoked");

        boolean gameBlankErrorBoolean = false;
        StringBuilder gameBlankErrorString = new StringBuilder();

        if (StringUtils.isBlank(gameName)) {
            gameBlankErrorBoolean = true;
            gameBlankErrorString.append("Game name");
        }

        if (StringUtils.isBlank(totalTeam)) {

            if (gameBlankErrorBoolean) {
                gameBlankErrorBoolean = true;
                gameBlankErrorString.append(", total team");
            } else {
                gameBlankErrorBoolean = true;
                gameBlankErrorString.append("Total team");
            }
        }

        if (gameBlankErrorBoolean) {
            gameBlankErrorString.append(" cannot be blank.");
            throw new InvalidParameterException(gameBlankErrorString.toString());
        }
    }
}
