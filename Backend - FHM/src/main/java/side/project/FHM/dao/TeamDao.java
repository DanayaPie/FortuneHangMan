package side.project.FHM.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import side.project.FHM.model.Team;

import java.util.List;

@Repository
public class TeamDao {

    private Logger logger = LoggerFactory.getLogger(TeamDao.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<Team> getAllTeams() {
        logger.info("TeamDao.getAllTeams() invoked");

        try {
            List<Team> teams = entityManager.createQuery("FROM Team t", Team.class).getResultList();

            return teams;
        } catch (NoResultException e) {
            e.printStackTrace();

            return null;
        }
    }
}
