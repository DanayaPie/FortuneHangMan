package side.project.FHM.utility;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.service.WordService;

public class ValidateWord {

    private static Logger logger = LoggerFactory.getLogger(ValidateWord.class);

    @Autowired
    private WordService wordService;

    public static void validateCategoryAndWordBlank(String category, String word) throws InvalidParameterException {
        logger.info("ValidateWord.validateWordBlank() invoked");

        boolean wordBlankErrorBoolean = false;
        StringBuilder wordBlankErrorString = new StringBuilder();

        if (StringUtils.isBlank(category)) {
            wordBlankErrorString.append("Category");
            wordBlankErrorBoolean = true;
        }

        if (StringUtils.isBlank(word)) {
            if (wordBlankErrorBoolean) {
                wordBlankErrorString.append(", word");
                wordBlankErrorBoolean = true;
            } else {
                wordBlankErrorString.append("Word");
                wordBlankErrorBoolean = true;
            }
        }

        if (wordBlankErrorBoolean) {
            wordBlankErrorString.append(" cannot be blank and can only contain letters.");
            throw new InvalidParameterException(wordBlankErrorString.toString());
        }
    }

    public static void validateCategoryAndWordChar(String category, String word) throws InvalidParameterException {
        logger.info("ValidateWord.validateWordChar() invoked");

        boolean wordCharErrorBoolean = false;
        StringBuilder wordCharErrorString = new StringBuilder();

        if (!category.matches("^[\\sA-Z]+$")) {
            wordCharErrorString.append("Categories can only contain alphabets");
            wordCharErrorBoolean = true;
        }

        if (!word.matches("^[-\\sA-Z$,;:'!%&]+$")) {
            if (wordCharErrorBoolean) {
                wordCharErrorString.append(", but words can contain alphabets and some special characters such as ;, :, $, !, &, %, -, ', and ,. If words contain numbers, please spell them out");
                wordCharErrorBoolean = true;
            } else {
                wordCharErrorString.append("Words can contain alphabets and some special characters such as ;, :, $, !, &, %, -, ', and ,. If words contain numbers, please spell them out");
                wordCharErrorBoolean = true;
            }
        }

        if (wordCharErrorBoolean) {
            wordCharErrorString.append(".");
            throw new InvalidParameterException(wordCharErrorString.toString());
        }
    }

    public static void validateCategoryChar(String categoryCaps) throws InvalidParameterException {
        logger.info("ValidateWord.validateCategoryChar() invoked");

        if (!categoryCaps.matches("^[\\sA-Z]+$")) {
            throw new InvalidParameterException("Category can only contain alphabets.");
        }
    }
}
