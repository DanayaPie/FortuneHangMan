package side.project.FHM.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import side.project.FHM.model.Round;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoundDao {

    private Logger logger = LoggerFactory.getLogger(RoundDao.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Set<Round> getAllRounds() {
        logger.info("RoundService.getAllRounds() invoked");

        try {
            List<Round> rounds = entityManager.createQuery("FROM Round r", Round.class).getResultList();
            Set<Round> roundsSet = new HashSet<>(rounds);

            return roundsSet;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public Round addRound(Round roundToAdd) {
        logger.info("RoundService.addRound() invoked");

        this.entityManager.persist(roundToAdd);
        return roundToAdd;
    }

    @Transactional
    public Round getRoundByRoundId(int roundId) {
        logger.info("RoundService.getRoundByRoundId() invoked");

        try {
            Round roundToGet = entityManager.createQuery("FROM Round r WHERE r.roundId.roundId = :roundId", Round.class)
                    .setParameter("roundId", roundId)
                    .getSingleResult();
            return roundToGet;

        } catch (NoResultException e) {
            return null;
        }
    }
}
