package side.project.FHM.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import side.project.FHM.model.Team;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Sql("/test-data.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TeamDaoUnitTest {

    @Autowired
    private TeamDao teamDaoUnderTest;

    @Test
    void getAllTeams_positive() {

        List<Team> actual = teamDaoUnderTest.getAllTeams();

        Team team1 = new Team(1, "Shrek", 1, 1, 0);
        Team team2 = new Team(2, "Donkey", 2, 1, 0);

        List<Team> expected = new ArrayList<>();
        expected.add(team1);
        expected.add(team2);

        Assertions.assertTrue(expected.containsAll(actual));
        Assertions.assertTrue(actual.containsAll(expected));
    }

    @Test
    @Sql("/test-delete-data.sql")
    void getAllTeams_teamDoesNotExist_negative() {

        List<Team> actual = teamDaoUnderTest.getAllTeams();
        List<Team> expected = new ArrayList<>();

        Assertions.assertTrue(expected.containsAll(actual));
        Assertions.assertTrue(actual.containsAll(expected));
    }

    @Test
    void addTeam_positive() {

        Team teamToAdd = new Team("Fiona", 3, 1, 0);

        Team actual = teamDaoUnderTest.addTeam(teamToAdd);
        Team expected = new Team(3, "Fiona", 3, 1, 0);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTeam_noTeamInfo_negative() {

        Team teamToAdd = new Team();
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> teamDaoUnderTest.addTeam(teamToAdd));
    }

    @Test
    void getTeamByTeamId_positive() {

        Team actual = teamDaoUnderTest.getTeamByTeamId(1);
        Team expected = new Team(1, "Shrek", 1, 1, 0);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Sql("/test-delete-data.sql")
    void getTeamByTeamId_teamDoesNotExist_negative() {

        Team actual = teamDaoUnderTest.getTeamByTeamId(1);
        Assertions.assertNull(actual);
    }

    @Test
    void updateTeamByTeamId_updateGameId_positive() {

        Team teamToUpdate = new Team(1, "Shrek", 1, 2, 0);
        Team actual = teamDaoUnderTest.updateTeamByTeamId(teamToUpdate);

        Team expected = new Team(1, "Shrek", 1, 2, 0);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateTeamByTeamId_updateTotalScore_positive() {

        Team teamToUpdate = new Team(1, "Shrek", 1, 1, 100);
        Team actual = teamDaoUnderTest.updateTeamByTeamId(teamToUpdate);

        Team expected = new Team(1, "Shrek", 1, 1, 100);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateTeamByTeamId_updateGameIdAndTotalScore_positive() {

        Team teamToUpdate = new Team(1, "Shrek", 1, 2, 100);
        Team actual = teamDaoUnderTest.updateTeamByTeamId(teamToUpdate);

        Team expected = new Team(1, "Shrek", 1, 2, 100);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateTeamByTeamId_updateTeamId_negative() {

        Team teamToUpdate = new Team(2, "Shrek", 1, 1, 0);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> teamDaoUnderTest.updateTeamByTeamId(teamToUpdate));
    }

    @Test
    void getTeamsByGameId_positive() {

        List<Team> actual = teamDaoUnderTest.getTeamsByGameId(1);

        Team team1 = new Team(1, "Shrek", 1, 1, 0);
        Team team2 = new Team(2, "Donkey", 2, 1, 0);

        List<Team> expected = new ArrayList<>();
        expected.add(team1);
        expected.add(team2);

        Assertions.assertTrue(expected.containsAll(actual));
        Assertions.assertTrue(actual.containsAll(expected));
    }

    @Test
    @Sql("/test-delete-data.sql")
    void getTeamsByGameId_teamDoesNotExist_negative() {

        List<Team> actual = teamDaoUnderTest.getTeamsByGameId(1);
        List<Team> expected = new ArrayList<>();

        Assertions.assertTrue(expected.containsAll(actual));
        Assertions.assertTrue(actual.containsAll(expected));
    }
}