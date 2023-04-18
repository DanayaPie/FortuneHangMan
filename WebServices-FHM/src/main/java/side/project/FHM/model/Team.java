package side.project.FHM.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", insertable = false,nullable = false)
    private int teamId;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @Column(name = "team_turn", nullable = false, unique = true)
    private int teamTurn;

    @Column(name = "game_id", nullable = true)
    private int gameId;

    @Column(name = "total_score", nullable = false)
    private int totalScore;

    public Team() {
    }

    public Team(String teamName, int teamTurn, int gameId, int totalScore) {
        this.teamName = teamName;
        this.teamTurn = teamTurn;
        this.gameId = gameId;
        this.totalScore = totalScore;
    }

    public Team(int teamId, String teamName, int teamTurn, int gameId, int totalScore) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamTurn = teamTurn;
        this.gameId = gameId;
        this.totalScore = totalScore;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTeamTurn() {
        return teamTurn;
    }

    public void setTeamTurn(int teamTurn) {
        this.teamTurn = teamTurn;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;
        Team team = (Team) o;
        return teamId == team.teamId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamId);
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", teamTurn=" + teamTurn +
                ", gameId=" + gameId +
                ", totalScore=" + totalScore +
                '}';
    }
}
