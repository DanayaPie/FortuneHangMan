package side.project.FHM.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import side.project.FHM.dao.WordDao;
import side.project.FHM.exception.CategoryDoesNotExistException;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.exception.WordAlreadyExistException;
import side.project.FHM.exception.WordDoesNotExistException;
import side.project.FHM.model.Word;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@Sql("/test-data.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class WordServiceIntegrationTest {

    @Autowired
    private WordService wordServiceUnderTest;

    @Autowired
    private WordDao wordDao;

    @Test
    void getAllWords_positive() throws WordDoesNotExistException {

        // when
        Set<Word> actual = wordServiceUnderTest.getAllWords();

        // then
        Word word1 = new Word(1, "FRUIT", "MANGO");
        Word word2 = new Word(2, "ANIMAL", "CAT");

        Set<Word> expected = new HashSet<>();
        expected.add(word1);
        expected.add(word2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Sql("/test-delete-data.sql")
    void getAllWords_wordDoesNotExist_negative() throws WordDoesNotExistException {

        Throwable actualExceptionThrown = Assertions.assertThrows(WordDoesNotExistException.class, () -> wordServiceUnderTest.getAllWords());
        Assertions.assertEquals("No words on file.", actualExceptionThrown.getMessage());
    }

    @Test
    void addWord_positive() throws InvalidParameterException, WordAlreadyExistException {

        Set<Word> wordSet = new HashSet<>();

        Word wordToAdd = new Word("FRUIT", "ORANGE");
        wordToAdd.setWordId(1);

        Word actual = wordServiceUnderTest.addWord(wordSet, "FRUIT", "ORANGE");

        Word expected = new Word(1, "FRUIT", "ORANGE");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addWord_wordAlreadyExist_negative() {

        Word word = new Word(1, "FRUIT", "ORANGE");

        Set<Word> wordSet = new HashSet<>();
        wordSet.add(word);

        Throwable actualExceptionThrown = Assertions.assertThrows(WordAlreadyExistException.class, () -> wordServiceUnderTest.addWord(wordSet, "FRUIT", "ORANGE"));
        Assertions.assertEquals("ORANGE already exist in FRUIT", actualExceptionThrown.getMessage());
    }

    @Test
    void getWordByWordId_positive() throws WordDoesNotExistException {

        Word actual = wordServiceUnderTest.getWordByWordId(1);
        Word expected = new Word(1, "FRUIT", "MANGO");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Sql("/test-delete-data.sql")
    void getWordByWordId_wordDoesNotExist_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(WordDoesNotExistException.class, () -> wordServiceUnderTest.getWordByWordId(1));
        Assertions.assertEquals("No word with the word ID of 1", actualExceptionThrown.getMessage());
    }

    @Test
    void getRandomWordByCategory_positive() throws InvalidParameterException, CategoryDoesNotExistException {

        Word actual = wordServiceUnderTest.getRandomWordByCategory("FRUIT");
        Word expected = new Word(1, "FRUIT", "MANGO");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Sql("/test-delete-data.sql")
    void getRandomWordByCategory_categoryDoesNotExist_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(CategoryDoesNotExistException.class, () -> wordServiceUnderTest.getRandomWordByCategory("FRUIT"));
        Assertions.assertEquals("FRUIT category does not exist.", actualExceptionThrown.getMessage());
    }

    @Test
    void getAllCategories_positive() throws CategoryDoesNotExistException {

        List<String> actual = wordServiceUnderTest.getAllCategories();

        String category1 = "FRUIT";
        String category2 = "ANIMAL";

        List<String> expected = new ArrayList<>();
        expected.add(category1);
        expected.add(category2);

        Assertions.assertTrue(expected.containsAll(actual));
        Assertions.assertTrue(actual.containsAll(expected));
    }

    @Test
    @Sql("/test-delete-data.sql")
    void getAllCategories_categoryDoesNotExist_negative() throws CategoryDoesNotExistException {

        Throwable actualExceptionThrown = Assertions.assertThrows(CategoryDoesNotExistException.class, () -> wordServiceUnderTest.getAllCategories());
        Assertions.assertEquals("No categories on file.", actualExceptionThrown.getMessage());
    }
}