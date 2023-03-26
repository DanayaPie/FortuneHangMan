package side.project.FHM.utility;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.service.RoundService;

public class ValidateRound {

    private static Logger logger = LoggerFactory.getLogger(ValidateRound.class);

    @Autowired
    private RoundService roundService;


}
