package side.project.FHM.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.service.GameService;

public class ValidateGame {

    private static Logger logger = LoggerFactory.getLogger(ValidateGame.class);

    @Autowired
    private GameService gameService;


}
