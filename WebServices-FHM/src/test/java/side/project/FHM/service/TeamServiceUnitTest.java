package side.project.FHM.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import side.project.FHM.dao.TeamDao;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.exception.TeamDoesNotExistException;
import side.project.FHM.model.Team;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TeamServiceUnitTest {

    @InjectMocks
    private TeamService teamServiceUnderTest;

    @Mock
    private TeamDao teamDao;

    @Test
    void getAllTeams_positive() throws TeamDoesNotExistException {

        // given
        Team team1 = new Team(1, "Shrek", 1, 1, 0);
        Team team2 = new Team(2, "Donkey", 2, 1, 0);

        List<Team> teamList = new ArrayList<>();
        teamList.add(team1);
        teamList.add(team2);

        // when
        when(teamDao.getAllTeams()).thenReturn(teamList);
        List<Team> actual = teamServiceUnderTest.getAllTeams();

        // then
        List<Team> expected = new ArrayList<>();
        expected.add(team1);
        expected.add(team2);

        Assertions.assertTrue(expected.containsAll(actual));
        Assertions.assertTrue(actual.containsAll(expected));
    }

    @Test
    void getAllTeams_teamDoesNotExist_negative() {

        List<Team> teamList = new ArrayList<>();
        when(teamDao.getAllTeams()).thenReturn(teamList);

        Throwable actualExceptionThrown = Assertions.assertThrows(TeamDoesNotExistException.class, () -> teamServiceUnderTest.getAllTeams());
        Assertions.assertEquals("No teams on file.", actualExceptionThrown.getMessage());
    }

    @Test
    void addTeam_positive() throws InvalidParameterException {

        Team teamToAdd = new Team("Fiona", 1, 1, 0);

        when(teamDao.addTeam(teamToAdd)).thenReturn(teamToAdd);
        Team actual = teamServiceUnderTest.addTeam("Fiona", "1", "1");
        actual.setTeamId(1);

        Team expected = new Team(1, "Fiona", 1, 1, 0);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTeam_teamTurnIsNotInt_negative() throws InvalidParameterException {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> teamServiceUnderTest.addTeam("Fiona", "one", "1"));
        Assertions.assertEquals("Team turn must be whole number.", actualExceptionThrown.getMessage());
    }

    @Test
    void addTeam_gameIdIsNotInt_negative() throws InvalidParameterException {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> teamServiceUnderTest.addTeam("Fiona", "1", "one"));
        Assertions.assertEquals("Game ID must be whole number.", actualExceptionThrown.getMessage());
    }

    @Test
    void addTeam_teamTurnGameIdAreNotInt_negative() throws InvalidParameterException {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> teamServiceUnderTest.addTeam("Fiona", "one", "one"));
        Assertions.assertEquals("Team turn, game ID must be whole number.", actualExceptionThrown.getMessage());
    }

    @Test
    void getTeamByTeamId_positive() throws TeamDoesNotExistException {

        Team team = new Team(1, "Shrek", 1, 1, 0);

        when(teamDao.getTeamByTeamId(1)).thenReturn(team);
        Team actual = teamServiceUnderTest.getTeamByTeamId(1);

        Team expected = new Team(1, "Shrek", 1, 1, 0);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getTeamByTeamId_teamDoesNotExist_negative() {

        when(teamDao.getTeamByTeamId(1)).thenReturn(null);

        Throwable actualExceptionThrown = Assertions.assertThrows(TeamDoesNotExistException.class, () -> teamServiceUnderTest.getTeamByTeamId(1));
        Assertions.assertEquals("No team with the team ID of 1", actualExceptionThrown.getMessage());
    }

    @Test
    void updateTeamByTeamId_updateGameId_positive() throws InvalidParameterException, TeamDoesNotExistException {

        Team team = new Team(1, "Shrek", 1, 1, 0);
        Team teamToUpdate = new Team(1, "Shrek", 1, 2, 0);

        when(teamDao.getTeamByTeamId(1)).thenReturn(team);
        when(teamDao.updateTeamByTeamId(teamToUpdate)).thenReturn(teamToUpdate);

        Team actual = teamServiceUnderTest.updateTeamByTeamId(1, "2", null);

        Team expected = new Team(1, "Shrek", 1, 2, 0);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateTeamByTeamId_updateTotalScore_positive() throws InvalidParameterException, TeamDoesNotExistException {

        Team team = new Team(1, "Shrek", 1, 1, 0);
        Team teamToUpdate = new Team(1, "Shrek", 1, 1, 100);

        when(teamDao.getTeamByTeamId(1)).thenReturn(team);
        when(teamDao.updateTeamByTeamId(teamToUpdate)).thenReturn(teamToUpdate);

        Team actual = teamServiceUnderTest.updateTeamByTeamId(1, null, "100");

        Team expected = new Team(1, "Shrek", 1, 1, 100);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateTeamByTeamId_updateGameIdTotalScore_positive() throws InvalidParameterException, TeamDoesNotExistException {

        Team team = new Team(1, "Shrek", 1, 1, 0);
        Team teamToUpdate = new Team(1, "Shrek", 1, 2, 100);

        when(teamDao.getTeamByTeamId(1)).thenReturn(team);
        when(teamDao.updateTeamByTeamId(teamToUpdate)).thenReturn(teamToUpdate);

        Team actual = teamServiceUnderTest.updateTeamByTeamId(1, "2", "100");

        Team expected = new Team(1, "Shrek", 1, 2, 100);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateTeamByTeamId_gameIdIsNotInt_negative() {

        Team team = new Team(1, "Shrek", 1, 1, 0);

        when(teamDao.getTeamByTeamId(1)).thenReturn(team);

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> teamServiceUnderTest.updateTeamByTeamId(1, "two", null));
        Assertions.assertEquals("Game ID must be whole number.", actualExceptionThrown.getMessage());
    }

    @Test
    void updateTeamByTeamId_totalScoreIsNotInt_negative() {

        Team team = new Team(1, "Shrek", 1, 1, 0);

        when(teamDao.getTeamByTeamId(1)).thenReturn(team);

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> teamServiceUnderTest.updateTeamByTeamId(1, null, "nine"));
        Assertions.assertEquals("Total score must be whole number.", actualExceptionThrown.getMessage());
    }

    @Test
    void updateTeamByTeamId_totalScoreGameIdAreNotInt_negative() {

        Team team = new Team(1, "Shrek", 1, 1, 0);

        when(teamDao.getTeamByTeamId(1)).thenReturn(team);

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> teamServiceUnderTest.updateTeamByTeamId(1, "two", "nine"));
        Assertions.assertEquals("Game ID, total score must be whole number.", actualExceptionThrown.getMessage());
    }

    @Test
    void getTeamsByGameId_positive() throws TeamDoesNotExistException {

        Team team1 = new Team(1, "Shrek", 1, 1, 0);
        Team team2 = new Team(2, "Donkey", 2, 1, 0);

        List<Team> teamList = new ArrayList<>();
        teamList.add(team1);
        teamList.add(team2);

        when(teamDao.getTeamsByGameId(1)).thenReturn(teamList);
        List<Team> actual = teamServiceUnderTest.getTeamsByGameId(1);

        List<Team> expected = new ArrayList<>();
        expected.add(team1);
        expected.add(team2);

        Assertions.assertTrue(expected.containsAll(actual));
        Assertions.assertTrue(actual.containsAll(expected));
    }

    @Test
    void getTeamsByGameId_teamDoesNotExist_negative() throws TeamDoesNotExistException {

        List<Team> teamList = new ArrayList<>();
        when(teamDao.getTeamsByGameId(1)).thenReturn(teamList);

        Throwable actualExceptionThrown = Assertions.assertThrows(TeamDoesNotExistException.class, () -> teamServiceUnderTest.getTeamsByGameId(1));
        Assertions.assertEquals("No teams with the game ID of 1", actualExceptionThrown.getMessage());
    }

    @Test
    void getTeamsByGameIdTeamTurn_positive() throws TeamDoesNotExistException, InvalidParameterException {

        Team team = new Team(2, "Donkey", 2, 1, 0);

        when(teamDao.getTeamsByGameIdTeamTurn(1, 2)).thenReturn(team);
        Team actual = teamServiceUnderTest.getTeamsByGameIdTeamTurn(1, 2);

        Team expected = new Team(2, "Donkey", 2, 1, 0);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getTeamsByGameIdTeamTurn_teamDoesNotExist_negative() throws TeamDoesNotExistException {

        when(teamDao.getTeamsByGameIdTeamTurn(1, 2)).thenReturn(null);

        Throwable actualExceptionThrown = Assertions.assertThrows(TeamDoesNotExistException.class, () -> teamServiceUnderTest.getTeamsByGameIdTeamTurn(1, 2));
        Assertions.assertEquals("No teams with the game ID of 1 team turn of 2", actualExceptionThrown.getMessage());
    }
}