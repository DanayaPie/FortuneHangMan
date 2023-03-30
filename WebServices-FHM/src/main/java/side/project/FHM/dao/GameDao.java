package side.project.FHM.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import side.project.FHM.model.Game;

import java.util.List;

@Repository
public class GameDao {

    private Logger logger = LoggerFactory.getLogger(GameDao.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<Game> getAllGames() {
        logger.info("GameDao.getAllGame() invoked");

        try {
            List<Game> allGames = entityManager.createQuery("FROM Game g").getResultList();
            return allGames;

        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Transactional
    public Game addGame(Game gameToAdd) {
        logger.info("GameDao.addGame() invoked");

        this.entityManager.persist(gameToAdd);
        return gameToAdd;
    }

    @Transactional
    public Game getGameByGameId(int gameId) {
        logger.info("GameDao.getGameByGameId() invoked");

        try {
            Game gameToGet = entityManager.createQuery("FROM Game g WHERE g.gameId = :gameId", Game.class)
                    .setParameter("gameId", gameId)
                    .getSingleResult();
            return gameToGet;

        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public Game updateGameByGameId(Game gameToUpdate) {
        logger.info("GameDao.updateGameByGameId() invoked");

        this.entityManager.merge(gameToUpdate);
        return gameToUpdate;
    }
}
