package model;

import java.util.Objects;

public class RoundId {

    private int roundId;
    private int teamId;
    private Game game;

    public RoundId() {
    }

    public RoundId(int roundId, int teamId, Game game) {
        this.roundId = roundId;
        this.teamId = teamId;
        this.game = game;
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
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
        return roundId == roundId1.roundId && teamId == roundId1.teamId && Objects.equals(game, roundId1.game);
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
