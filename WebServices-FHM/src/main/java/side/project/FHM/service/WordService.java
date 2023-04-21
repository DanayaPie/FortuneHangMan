package side.project.FHM.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import side.project.FHM.dao.WordDao;
import side.project.FHM.exception.CategoryDoesNotExistException;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.exception.WordAlreadyExistException;
import side.project.FHM.exception.WordDoesNotExistException;
import side.project.FHM.model.Word;
import side.project.FHM.utility.ValidateWord;

import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class WordService {

    private Logger logger = LoggerFactory.getLogger(WordService.class);

    @Autowired
    private WordDao wordDao;

    public WordService(WordDao wordDao) {
    }

    public Set<Word> getAllWords() throws WordDoesNotExistException {
        logger.info("WordService.getAllWords() invoked");

        Set<Word> allWords = wordDao.getALlWords();

        if (allWords.isEmpty()) {
            throw new WordDoesNotExistException("No words on file.");
        } else {
            return allWords;
        }
    }

    public Word addWord(Set<Word> wordInDb, String category, String word) throws InvalidParameterException, WordAlreadyExistException {
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
        if (!wordInDb.contains(wordToAdd)) {
            logger.debug("wordInDb not contains wordToAdd");

            Word addedWord = wordDao.addWord(wordToAdd);
            wordInDb.add(wordToAdd);

            return addedWord;
        } else {
            throw new WordAlreadyExistException(wordCaps + " already exist in " + categoryCaps);
        }
    }

    public Word getWordByWordId(int wordId) throws WordDoesNotExistException {
        logger.info("WordService.getWordByWordId() invoked");

        Word wordToGet = wordDao.getWordByWordId(wordId);

        if (wordToGet == null) {
            throw new WordDoesNotExistException("No word with the word ID of " + wordId);
        } else {
            return wordToGet;
        }
    }

    public Word getRandomWordByCategory(String category) throws InvalidParameterException, CategoryDoesNotExistException {
        logger.info("WordService.getRandomWordByCategory() invoked");

        String categoryCaps = category.trim().toUpperCase();
        ValidateWord.validateCategoryChar(categoryCaps); // validate characters

        List<Word> wordsInCategory = wordDao.getWordsByCategory(categoryCaps);

        logger.debug("wordsInCategory {}" + wordsInCategory);
        if (!wordsInCategory.isEmpty()) {
            Random rand = new Random();
            Word randomWord = wordsInCategory.get(rand.nextInt(wordsInCategory.size()));
            return randomWord;
        } else {
            throw new CategoryDoesNotExistException(categoryCaps + " category does not exist.");
        }
    }

    public List<String> getAllCategories() throws CategoryDoesNotExistException {
        logger.info("WordService.getAllCategories() invoked");

        List<String> allCategories = wordDao.getAllCategories();

        if (allCategories.isEmpty()) {
            throw new CategoryDoesNotExistException("No categories on file.");
        } else {
            return allCategories;
        }
    }
}
