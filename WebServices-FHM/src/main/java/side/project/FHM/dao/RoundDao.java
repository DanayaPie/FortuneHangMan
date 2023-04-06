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
        logger.info("RoundDao.getAllRounds() invoked");

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
        logger.info("RoundDao.addRound() invoked");

        this.entityManager.persist(roundToAdd);
        return roundToAdd;
    }

    @Transactional
    public List<Round> getRoundsByRoundId(long roundId) {
        logger.info("RoundDao.getRoundsByRoundId() invoked");

        try {
            List<Round> roundsToGet = entityManager.createQuery("FROM Round r WHERE r.roundId.roundId = :roundId", Round.class)
                    .setParameter("roundId", roundId)
                    .getResultList();
            return roundsToGet;

        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public Round getRoundByRoundIdTeamId(long roundId, int teamId) {
        logger.info("RoundDao.getRoundByRoundIdTeamId() invoked");

        try {
            Round roundToGet = entityManager.createQuery("FROM Round r WHERE r.roundId.roundId = :roundId"
                            + " AND r.roundId.teamId = :teamId", Round.class)
                    .setParameter("roundId", roundId)
                    .setParameter("teamId", teamId)
                    .getSingleResult();
            return roundToGet;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public List<Round> getRoundsByRoundIdGameId(long roundId, int gameId) {
        logger.info("RoundDao.getRoundsByRoundIdGameId() invoked");

        try {
            List<Round> roundsToGet = entityManager.createQuery("FROM Round r WHERE r.roundId.roundId = :roundId"
                            + " AND r.roundId.gameId = :gameId", Round.class)
                    .setParameter("roundId", roundId)
                    .setParameter("gameId", gameId)
                    .getResultList();
            return roundsToGet;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public Round updateRoundByRoundIdTeamId(Round roundToUpdate) {
        logger.info("RoundDao.updateRoundByRoundIdTeamId() invoked");

        this.entityManager.merge(roundToUpdate);
        return roundToUpdate;
    }

    public Long getSequenceId() {
        logger.info("RoundDao.getSequenceId() invoked");

        Long roundSeqId = (Long) entityManager.createNativeQuery("SELECT nextval('fhm.round_seq') ").getSingleResult();
        return roundSeqId;
    }
}
