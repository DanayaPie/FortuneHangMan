package side.project.FHM.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.service.RoundService;

public class ValidateRound {

    private static Logger logger = LoggerFactory.getLogger(ValidateRound.class);

    @Autowired
    private RoundService roundService;

    public static void validateSpinToken(String spinToken) throws InvalidParameterException {
        logger.info("ValidateRound.validateSpinToken() invoked");

        if (!spinToken.equalsIgnoreCase("true") && !spinToken.equalsIgnoreCase("false")) {
            throw new InvalidParameterException("Spin token must be either TRUE or FALSE.");
        }

    }
}
