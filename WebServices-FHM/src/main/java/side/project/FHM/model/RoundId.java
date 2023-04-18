package side.project.FHM.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RoundId implements Serializable {

    @Column(name = "round_id")
    private long roundId;

    @Column(name = "team_id", insertable = false, updatable = false)
    private int teamId;

    @Column(name = "game_id", insertable = false, updatable = false)
    private int gameId;

    public RoundId() {
    }

    public RoundId(int teamId, int gameId) {
        this.teamId = teamId;
        this.gameId = gameId;
    }

    public RoundId(long roundId, int teamId, int gameId) {
        this.roundId = roundId;
        this.teamId = teamId;
        this.gameId = gameId;
    }

    public long getRoundId() {
        return roundId;
    }

    public void setRoundId(long roundId) {
        this.roundId = roundId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoundId)) return false;
        RoundId roundId1 = (RoundId) o;
        return roundId == roundId1.roundId && teamId == roundId1.teamId && gameId == roundId1.gameId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roundId, teamId, gameId);
    }

    @Override
    public String toString() {
        return "RoundId{" +
                "roundId=" + roundId +
                ", teamId=" + teamId +
                ", game=" + gameId +
                '}';
    }
}
