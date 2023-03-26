package side.project.FHM.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RoundId implements Serializable {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "round_generator")
    @SequenceGenerator(name = "round_generator", sequenceName = "round_seq")
    @Column(name = "round_id",updatable = false, nullable = false)
    private int roundId;

    @OneToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @OneToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    public RoundId() {
    }

    public RoundId(int roundId, Team team, Game game) {
        this.roundId = roundId;
        this.team = team;
        this.game = game;
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoundId)) return false;
        RoundId roundId1 = (RoundId) o;
        return roundId == roundId1.roundId && Objects.equals(team, roundId1.team) && Objects.equals(game, roundId1.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roundId, team, game);
    }

    @Override
    public String toString() {
        return "RoundId{" +
                "roundId=" + roundId +
                ", team=" + team +
                ", game=" + game +
                '}';
    }
}
