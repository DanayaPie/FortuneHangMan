package side.project.FHM.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("RoundDaoTest")
public class RoundSeqForRoundDaoTesting extends RoundDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long getSequenceId() {

        Long roundSeqId = (Long) entityManager.createNativeQuery("SELECT nextval('round_seq_dao')").getSingleResult();
        return roundSeqId;
    }
}
