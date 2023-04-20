package side.project.FHM.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import side.project.FHM.dao.WordDao;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.exception.WordDoesNotExist;
import side.project.FHM.model.Word;

import java.util.HashSet;
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
    void getAllWords_positive() throws InvalidParameterException, WordDoesNotExist {

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
    void getAllWords_wordDoesNotExist_negative() {

        Set<Word> wordSet = new HashSet<>();
        when(wordDao.getALlWords()).thenReturn(wordSet);

        Throwable actualExceptionThrown = Assertions.assertThrows(WordDoesNotExist.class, () -> wordServiceUnderTest.getAllWords());
        Assertions.assertEquals("No words on file.", actualExceptionThrown.getMessage());
    }

    @Test
    void addWord_positive() throws InvalidParameterException, WordDoesNotExist {

        Set<Word> wordSet = new HashSet<>();

        Word wordToAdd = new Word("FRUIT", "ORANGE");
        wordToAdd.setWordId(1);

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

        Word wordToAdd = new Word("FRUIT", "ORANGE");
        wordToAdd.setWordId(1);

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> wordServiceUnderTest.addWord(wordSet, "FRUIT", "ORANGE"));
        Assertions.assertEquals("ORANGE already exist in FRUIT", actualExceptionThrown.getMessage());
    }

    @Test
    @Disabled
    void getWordByWordId() {
    }

    @Test
    @Disabled
    void getRandomWordByCategory() {
    }

    @Test
    @Disabled
    void getAllCategories() {
    }
}