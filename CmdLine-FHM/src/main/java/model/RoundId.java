package model;

import java.util.Objects;

public class RoundId {

    private int roundId;
    private int teamId;
    private int gameId;

    public RoundId() {
    }

    public RoundId(int roundId, int teamId, int gameId) {
        this.roundId = roundId;
        this.teamId = teamId;
        this.gameId = gameId;
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
