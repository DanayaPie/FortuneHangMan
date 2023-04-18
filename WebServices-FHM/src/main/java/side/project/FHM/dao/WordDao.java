package side.project.FHM.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import side.project.FHM.model.Word;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class WordDao {

    private Logger logger = LoggerFactory.getLogger(WordDao.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Set<Word> getALlWords() {
        logger.info("WordDao.getALlWords() invoked");

        List<Word> wordsList = entityManager.createQuery("FROM Word w", Word.class).getResultList();
        Set<Word> wordsSet = new HashSet<>(wordsList);

        return wordsSet;
    }

    @Transactional
    public Word addWord(Word wordToAdd) {
        logger.info("WordDao.addWord() invoked");

        this.entityManager.persist(wordToAdd);
        return wordToAdd;
    }

    @Transactional
    public Word getWordByWordId(int wordId) {
        logger.info("WordDao.getWordByWordId() invoked");

        try {
            Word wordToGet = entityManager.createQuery("FROM Word w WHERE w.wordId = :wordId", Word.class)
                    .setParameter("wordId", wordId)
                    .getSingleResult();
            return wordToGet;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public List<Word> getWordsByCategory(String category) {
        logger.info("WordDao.getWordsInCategory() invoked");

        List<Word> wordsByCategory = entityManager.createQuery("FROM Word w WHERE w.category = :category", Word.class)
                .setParameter("category", category)
                .getResultList();
        return wordsByCategory;
    }

    @Transactional
    public List<String> getAllCategories() {
        logger.info("WordDao.getAllCategories() invoked");

        List<String> allCategories = entityManager.createQuery("SELECT DISTINCT(w.category) FROM Word w", String.class).getResultList();

        logger.debug("allCategories: {}", allCategories);
        return allCategories;
    }
}
