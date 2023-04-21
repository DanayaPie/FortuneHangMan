package side.project.FHM.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
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

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class WordServiceUnitTest {

    @InjectMocks
    private WordService wordServiceUnderTest;

    @Mock
    private WordDao wordDao;

    @Test
    void getAllWords_positive() throws WordDoesNotExistException {

        // given
        Word word1 = new Word(1, "FRUIT", "MANGO");
        Word word2 = new Word(2, "ANIMAL", "CAT");

        Set<Word> wordSet = new HashSet<>();
        wordSet.add(word1);
        wordSet.add(word2);

        // when
        when(wordDao.getALlWords()).thenReturn(wordSet);
        Set<Word> actual = wordServiceUnderTest.getAllWords();

        // then
        Set<Word> expected = new HashSet<>();
        expected.add(word1);
        expected.add(word2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAllWords_wordDoesNotExist_negative() throws WordDoesNotExistException {

        Set<Word> wordSet = new HashSet<>();
        when(wordDao.getALlWords()).thenReturn(wordSet);

        Throwable actualExceptionThrown = Assertions.assertThrows(WordDoesNotExistException.class, () -> wordServiceUnderTest.getAllWords());
        Assertions.assertEquals("No words on file.", actualExceptionThrown.getMessage());
    }

    @Test
    void addWord_positive() throws InvalidParameterException, WordAlreadyExistException {

        Set<Word> wordSet = new HashSet<>();

        Word wordToAdd = new Word("FRUIT", "ORANGE");

        when(wordDao.addWord(wordToAdd)).thenReturn(wordToAdd);
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

        Word word = new Word(1, "FRUIT", "MANGO");

        when(wordDao.getWordByWordId(1)).thenReturn(word);
        Word actual = wordServiceUnderTest.getWordByWordId(1);

        Word expected = new Word(1, "FRUIT", "MANGO");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getWordByWordId_wordDoesNotExist_negative() {

        when(wordDao.getWordByWordId(1)).thenReturn(null);

        Throwable actualExceptionThrown = Assertions.assertThrows(WordDoesNotExistException.class, () -> wordServiceUnderTest.getWordByWordId(1));
        Assertions.assertEquals("No word with the word ID of 1", actualExceptionThrown.getMessage());
    }

    @Test
    void getRandomWordByCategory_positive() throws InvalidParameterException, CategoryDoesNotExistException {

        Word word = new Word(1, "FRUIT", "MANGO");

        List<Word> wordList = new ArrayList<>();
        wordList.add(word);

        when(wordDao.getWordsByCategory("FRUIT")).thenReturn(wordList);
        Word actual = wordServiceUnderTest.getRandomWordByCategory("FRUIT");

        Word expected = new Word(1, "FRUIT", "MANGO");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getRandomWordByCategory_categoryDoesNotExist_negative() {

        List<Word> wordList = new ArrayList<>();
        when(wordDao.getWordsByCategory("FRUIT")).thenReturn(wordList);

        Throwable actualExceptionThrown = Assertions.assertThrows(CategoryDoesNotExistException.class, () -> wordServiceUnderTest.getRandomWordByCategory("FRUIT"));
        Assertions.assertEquals("FRUIT category does not exist.", actualExceptionThrown.getMessage());
    }

    @Test
    void getAllCategories_positive() throws CategoryDoesNotExistException {

        String category1 = "FRUIT";
        String category2 = "ANIMAL";

        List<String> categoryList = new ArrayList<>();
        categoryList.add(category1);
        categoryList.add(category2);

        when(wordDao.getAllCategories()).thenReturn(categoryList);
        List<String> actual = wordServiceUnderTest.getAllCategories();

        List<String> expected = new ArrayList<>();
        expected.add(category1);
        expected.add(category2);

        Assertions.assertTrue(expected.containsAll(actual));
        Assertions.assertTrue(actual.containsAll(expected));
    }

    @Test
    void getAllCategories_categoryDoesNotExist_negative() throws CategoryDoesNotExistException {

        List<String> categoryList = new ArrayList<>();
        when(wordDao.getAllCategories()).thenReturn(categoryList);

        Throwable actualExceptionThrown = Assertions.assertThrows(CategoryDoesNotExistException.class, () -> wordServiceUnderTest.getAllCategories());
        Assertions.assertEquals("No categories on file.", actualExceptionThrown.getMessage());
    }
}