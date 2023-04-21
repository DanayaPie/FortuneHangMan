package side.project.FHM.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import side.project.FHM.dao.TeamDao;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.exception.TeamDoesNotExistException;
import side.project.FHM.model.Team;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Sql("/test-data.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TeamServiceIntegrationTest {

    @Autowired
    private TeamService teamServiceUnderTest;

    @Autowired
    private TeamDao teamDao;

    @Test
    void getAllTeams_positive() throws TeamDoesNotExistException {

        // when
        List<Team> actual = teamServiceUnderTest.getAllTeams();

        // then
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

        Throwable actualExceptionThrown = Assertions.assertThrows(TeamDoesNotExistException.class, () -> teamServiceUnderTest.getAllTeams());
        Assertions.assertEquals("No teams on file.", actualExceptionThrown.getMessage());
    }

    @Test
    void addTeam_positive() throws InvalidParameterException {

        Team actual = teamServiceUnderTest.addTeam("Fiona", "3", "1");
        Team expected = new Team(3, "Fiona", 3, 1, 0);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTeam_teamTurnIsNotInt_negative() throws InvalidParameterException {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> teamServiceUnderTest.addTeam("Fiona", "three", "1"));
        Assertions.assertEquals("Team turn must be whole number.", actualExceptionThrown.getMessage());
    }

    @Test
    void addTeam_gameIdIsNotInt_negative() throws InvalidParameterException {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> teamServiceUnderTest.addTeam("Fiona", "1", "one"));
        Assertions.assertEquals("Game ID must be whole number.", actualExceptionThrown.getMessage());
    }

    @Test
    void addTeam_teamTurnGameIdAreNotInt_negative() throws InvalidParameterException {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> teamServiceUnderTest.addTeam("Fiona", "three", "one"));
        Assertions.assertEquals("Team turn, game ID must be whole number.", actualExceptionThrown.getMessage());
    }

    @Test
    void getTeamByTeamId_positive() throws TeamDoesNotExistException {

        Team actual = teamServiceUnderTest.getTeamByTeamId(1);
        Team expected = new Team(1, "Shrek", 1, 1, 0);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Sql("/test-delete-data.sql")
    void getTeamByTeamId_teamDoesNotExist_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(TeamDoesNotExistException.class, () -> teamServiceUnderTest.getTeamByTeamId(1));
        Assertions.assertEquals("No team with the team ID of 1", actualExceptionThrown.getMessage());
    }

    @Test
    void updateTeamByTeamId_updateGameId_positive() throws InvalidParameterException, TeamDoesNotExistException {

        Team actual = teamServiceUnderTest.updateTeamByTeamId(1, "2", "100");
        Team expected = new Team(1, "Shrek", 1, 2, 0);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateTeamByTeamId_updateTotalScore_positive() throws InvalidParameterException, TeamDoesNotExistException {

        Team actual = teamServiceUnderTest.updateTeamByTeamId(1, "1", "100");
        Team expected = new Team(1, "Shrek", 1, 1, 100);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateTeamByTeamId_updateGameIdTotalScore_positive() throws InvalidParameterException, TeamDoesNotExistException {

        Team actual = teamServiceUnderTest.updateTeamByTeamId(1, "2", "100");
        Team expected = new Team(1, "Shrek", 1, 2, 100);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateTeamByTeamId_gameIdIsNotInt_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> teamServiceUnderTest.updateTeamByTeamId(1, "two", null));
        Assertions.assertEquals("Game ID must be whole number.", actualExceptionThrown.getMessage());
    }

    @Test
    void updateTeamByTeamId_totalScoreIsNotInt_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> teamServiceUnderTest.updateTeamByTeamId(1, null, "nine"));
        Assertions.assertEquals("Total score must be whole number.", actualExceptionThrown.getMessage());
    }

    @Test
    void updateTeamByTeamId_totalScoreGameIdAreNotInt_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> teamServiceUnderTest.updateTeamByTeamId(1, "two", "nine"));
        Assertions.assertEquals("Game ID, total score must be whole number.", actualExceptionThrown.getMessage());
    }

    @Test
    void getTeamsByGameId_positive() throws TeamDoesNotExistException {

        List<Team> actual = teamServiceUnderTest.getTeamsByGameId(1);

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
    void getTeamsByGameId_teamDoesNotExist_negative() throws TeamDoesNotExistException {

        Throwable actualExceptionThrown = Assertions.assertThrows(TeamDoesNotExistException.class, () -> teamServiceUnderTest.getTeamsByGameId(1));
        Assertions.assertEquals("No teams with the game ID of 1", actualExceptionThrown.getMessage());
    }
}