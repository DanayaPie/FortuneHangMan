package side.project.FHM.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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
            List<Team> teamToGet = entityManager.createQuery("FROM Team t", Team.class).getResultList();

            return teamToGet;
        } catch (NoResultException e) {
            e.printStackTrace();

            return null;
        }
    }

    @Transactional
    public Team addTeam(Team teamToAdd) {
        logger.info("TeamDao.addTeam() invoked");

        this.entityManager.persist(teamToAdd);
        return teamToAdd;
    }

    @Transactional
    public Team getTeamByTeamId(int teamId) {
        logger.info("TeamDao.getTeamByTeamId() invoked");

        try {
            Team teamToGet = entityManager.createQuery("FROM Team t WHERE t.teamId = :teamId", Team.class)
                    .setParameter("teamId", teamId)
                    .getSingleResult();

            return teamToGet;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public Team updateTeamByTeamId(Team teamToUpdate) {
        logger.info("TeamDao.updateTeamByTeamId() invoked");

        this.entityManager.merge(teamToUpdate);
        return teamToUpdate;
    }

    public List<Team> getTeamsByGameId(int gameId) {
        logger.info("TeamDao.getTeamsByGameId() invoked");

        try {
            List<Team> teamsToGet = entityManager.createQuery("FROM Team t WHERE t.gameId = :gameId", Team.class)
                    .setParameter("gameId", gameId)
                    .getResultList();
            return teamsToGet;
        } catch (NoResultException e) {
            return null;
        }
    }
}
