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
    private int game;

    public RoundId() {
    }

    public RoundId(long roundId, int teamId, int game) {
        this.roundId = roundId;
        this.teamId = teamId;
        this.game = game;
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

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoundId)) return false;
        RoundId roundId1 = (RoundId) o;
        return roundId == roundId1.roundId && teamId == roundId1.teamId && game == roundId1.game;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roundId, teamId, game);
    }

    @Override
    public String toString() {
        return "RoundId{" +
                "roundId=" + roundId +
                ", teamId=" + teamId +
                ", game=" + game +
                '}';
    }
}
