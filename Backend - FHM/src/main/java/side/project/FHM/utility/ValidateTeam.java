package side.project.FHM.utility;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.service.TeamService;

public class ValidateTeam {

    private static Logger logger = LoggerFactory.getLogger(ValidateTeam.class);

    @Autowired
    TeamService teamService;

    public static void validateTeamNameBlank(String teamName) throws InvalidParameterException {
        logger.info("ValidateTeam.validateTeamNameBlank() invoked");

        if (StringUtils.isBlank(teamName)) {
            throw new InvalidParameterException("Team name cannot be blank");
        }
    }
}
