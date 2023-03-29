package side.project.FHM.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", insertable = false)
    private int teamId;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @Column(name = "team_turn", nullable = false, unique = true)
    private int teamTurn;

    @Column(name = "total_score")
    private int totalScore;

    public Team() {
    }

    public Team(int teamId, String teamName, int teamTurn, int totalScore) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamTurn = teamTurn;
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

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return teamId == team.teamId && teamTurn == team.teamTurn && totalScore == team.totalScore && Objects.equals(teamName, team.teamName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamId, teamName, teamTurn, totalScore);
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", teamTurn=" + teamTurn +
                ", totalScore=" + totalScore +
                '}';
    }
}
