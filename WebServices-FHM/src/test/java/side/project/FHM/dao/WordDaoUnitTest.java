package side.project.FHM.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import side.project.FHM.model.Word;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@Sql("/test-data.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class WordDaoUnitTest {

    @Autowired
    private WordDao wordDaoUnderTest;

    @Test
    void getALlWords_positive() {

        // when
        Set<Word> actual = wordDaoUnderTest.getALlWords();

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
    void getALlWords_wordDoesNotExist_negative() {

        Set<Word> actual = wordDaoUnderTest.getALlWords();
        Set<Word> expected = new HashSet<>(); // empty set

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addWord_positive() {

        Word wordToAdd = new Word("FRUIT", "ORANGE");

        Word actual = wordDaoUnderTest.addWord(wordToAdd);
        Word expected = new Word(3, "FRUIT", "ORANGE");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addWord_noWordInfo_negative() {

        Word wordToAdd = new Word();
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> wordDaoUnderTest.addWord(wordToAdd));
    }

    @Test
    void addWord_missingCategory_negative() {

        Word wordToAdd = new Word("FRUIT", null);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> wordDaoUnderTest.addWord(wordToAdd));
    }

    @Test
    void addWord_missingWord_negative() {

        Word wordToAdd = new Word("FRUIT", null);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> wordDaoUnderTest.addWord(wordToAdd));
    }

    @Test
    void addWord_missingCategoryAndWord_negative() {

        Word wordToAdd = new Word("FRUIT", null);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> wordDaoUnderTest.addWord(wordToAdd));
    }

    @Test
    void getWordByWordId_positive() {

        Word actual = wordDaoUnderTest.getWordByWordId(1);
        Word expected = new Word(1, "FRUIT", "MANGO");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Sql("/test-delete-data.sql")
    void getWordByWordId_wordDoesNotExist_negative() {

        Word actual = wordDaoUnderTest.getWordByWordId(1);
        Assertions.assertNull(actual);
    }

    @Test
    void getWordsByCategory_positive() {

        Word word = new Word(1, "FRUIT", "MANGO");

        List<Word> actual = wordDaoUnderTest.getWordsByCategory("FRUIT");

        List<Word> expected = new ArrayList<>();
        expected.add(word);

        Assertions.assertTrue(expected.containsAll(actual));
        Assertions.assertTrue(actual.containsAll(expected));
    }

    @Test
    @Sql("/test-delete-data.sql")
    void getWordsByCategory_categoryDoesNotExist_negative() {

        List<Word> actual = wordDaoUnderTest.getWordsByCategory("FRUIT");
        List<Word> expected = new ArrayList<>();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAllCategories_positive() {

        boolean containCategoriesBoolean;

        List<String> actual = wordDaoUnderTest.getAllCategories();

        containCategoriesBoolean = actual.contains("FRUIT") && actual.contains("ANIMAL");

        Assertions.assertTrue(containCategoriesBoolean);
    }

    @Test
    @Sql("/test-delete-data.sql")
    void getAllCategories_categoryDoesNotExist_negative() {

        List<String> actual = wordDaoUnderTest.getAllCategories();
        List<String> expected = new ArrayList<>();

        Assertions.assertEquals(expected, actual);
    }
}