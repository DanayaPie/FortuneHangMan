package side.project.FHM.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import side.project.FHM.dao.WordDao;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.exception.WordDoesNotExist;
import side.project.FHM.model.Word;
import side.project.FHM.utility.ValidateWord;

import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class WordService {

    @Autowired
    private WordDao wordDao;

    private Logger logger = LoggerFactory.getLogger(WordService.class);

    public Set<Word> getAllWords() throws WordDoesNotExist, InvalidParameterException {
        logger.info("WordService.getAllWords() invoked");

        Set<Word> words = wordDao.getALlWords();

        try {
            if (words.isEmpty()) {
                throw new WordDoesNotExist("No words on file.");
            }
            return words;
        } catch (DataAccessException e) {
            throw new InvalidParameterException("No words on file.");
        }
    }

    public Word addWord(Set<Word> wordInDb, String category, String word) throws InvalidParameterException, WordDoesNotExist {
        logger.info("WordService.addWord() invoked");

        String categoryCaps = category.trim().toUpperCase();
        String wordCaps = word.trim().toUpperCase();

        ValidateWord.validateCategoryAndWordBlank(categoryCaps, wordCaps); // validate blank inputs
        ValidateWord.validateCategoryAndWordChar(categoryCaps, wordCaps); // validate characters in inputs

        // create wordToAdd instance to be compares to wordInDb
        Word wordToAdd = new Word();
        wordToAdd.setCategory(categoryCaps);
        wordToAdd.setWord(wordCaps);

        logger.debug("wordInDb {}" + wordInDb);
        logger.debug("wordToAdd {} " + wordToAdd);

        // if word already exist in the category, dont add the word
        try {
            if (!wordInDb.contains(wordToAdd)) {
                logger.debug("wordInDb not contains wordToAdd");

                Word addedWord = wordDao.addWord(wordToAdd);
                wordInDb.add(wordToAdd);

                return addedWord;
            } else {
                throw new InvalidParameterException(wordCaps + " already exist in " + categoryCaps);

            }
        } catch (DataAccessException e) {
            throw new InvalidParameterException(wordCaps + " already exist in " + categoryCaps);

        }
    }

    public Word getWordByWordId(int wordId) throws InvalidParameterException, WordDoesNotExist {
        logger.info("WordService.getWordByWordId() invoked");

        Word wordToGet = wordDao.getWordByWordId(wordId);

        try {
            if (wordToGet == null) {
                throw new WordDoesNotExist("No word with the word ID of " + wordId);
            }
            return wordToGet;

        } catch (DataAccessException e) {
            throw new InvalidParameterException("No word with the word ID of " + wordId);
        }
    }

    public Word getRandomWordByCategory(String category) throws WordDoesNotExist, InvalidParameterException {
        logger.info("WordService.getRandomWordByCategory() invoked");

        String categoryCaps = category.trim().toUpperCase();
        ValidateWord.validateCategoryChar(categoryCaps); // validate characters

        List<Word> wordsInCategory = wordDao.getWordsByCategory(categoryCaps);

        logger.info("wordsIncategory {}" + wordsInCategory);
        try {
            if (!wordsInCategory.isEmpty()) {
                Random rand = new Random();
                Word randomWord = wordsInCategory.get(rand.nextInt(wordsInCategory.size()));
                return randomWord;
            }
            throw new WordDoesNotExist(categoryCaps + " category does not exist.");

        } catch (DataAccessException e) {
            throw new InvalidParameterException(categoryCaps + " category does not exist.");

        }
    }
}
