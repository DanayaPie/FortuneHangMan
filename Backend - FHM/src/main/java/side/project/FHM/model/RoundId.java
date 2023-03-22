package side.project.FHM.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RoundId implements Serializable {

    @Column(name = "round_id", insertable = false)
    private int roundId;

    @OneToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team teamId;

    public RoundId() {
    }

    public RoundId(int roundId, Team teamId) {
        this.roundId = roundId;
        this.teamId = teamId;
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public Team getTeamId() {
        return teamId;
    }

    public void setTeamId(Team teamId) {
        this.teamId = teamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoundId roundId1 = (RoundId) o;
        return roundId == roundId1.roundId && Objects.equals(teamId, roundId1.teamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roundId, teamId);
    }

    @Override
    public String toString() {
        return "RoundId{" +
                "roundId=" + roundId +
                ", teamId=" + teamId +
                '}';
    }
}
