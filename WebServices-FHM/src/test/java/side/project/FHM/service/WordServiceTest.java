package side.project.FHM.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import side.project.FHM.dao.WordDao;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.exception.WordDoesNotExist;
import side.project.FHM.model.Word;
import side.project.FHM.utility.ValidateWord;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class WordServiceTest {

    @InjectMocks
    private WordService wordServiceUnderTest;

    @Mock
    private ValidateWord validateWord;

    @Mock
    private WordDao wordDao;

    @BeforeEach
    @Sql("/test-data.sql")
    void setUp() {
        MockitoAnnotations.openMocks(this);
        wordServiceUnderTest = new WordService(wordDao);
        validateWord = new ValidateWord();
    }

    @Test
    @Sql("/test-data.sql")
    void getAllWords_positive() throws InvalidParameterException, WordDoesNotExist {

        // given
        Word word1 = new Word(1, "FRUIT", "MANGO");
        Word word2 = new Word(2, "ANIMAL", "CAT");

        Set<Word> words = new HashSet<>();
        words.add(word1);
        words.add(word2);

        // when
        when(wordDao.getALlWords()).thenReturn(words);
        wordServiceUnderTest = new WordService(wordDao);

        Set<Word> actual = wordServiceUnderTest.getAllWords();

        // then
        Set<Word> expected = new HashSet<>();
        expected.add(word1);
        expected.add(word2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Disabled
    void addWord() {
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