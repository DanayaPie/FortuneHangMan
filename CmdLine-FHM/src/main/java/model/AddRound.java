package model;

import java.util.List;
import java.util.Objects;

public class AddRound {

    private int roundId;
    private List<Integer> teamIds;
    private int gameId;
    private int roundScore;
    private int spinScore;
    private boolean spinToken;

    public AddRound(int roundId, List<Integer> teamIds, int gameId, int roundScore, int spinScore, boolean spinToken) {
        this.roundId = roundId;
        this.teamIds = teamIds;
        this.gameId = gameId;
        this.roundScore = roundScore;
        this.spinScore = spinScore;
        this.spinToken = spinToken;
    }

    public AddRound() {

    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public List<Integer> getTeamIds() {
        return teamIds;
    }

    public void setTeamIds(List<Integer> teamIds) {
        this.teamIds = teamIds;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getRoundScore() {
        return roundScore;
    }

    public void setRoundScore(int roundScore) {
        this.roundScore = roundScore;
    }

    public int getSpinScore() {
        return spinScore;
    }

    public void setSpinScore(int spinScore) {
        this.spinScore = spinScore;
    }

    public boolean isSpinToken() {
        return spinToken;
    }

    public void setSpinToken(boolean spinToken) {
        this.spinToken = spinToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddRound)) return false;
        AddRound addRound = (AddRound) o;
        return roundId == addRound.roundId && gameId == addRound.gameId && Objects.equals(teamIds, addRound.teamIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roundId, teamIds, gameId);
    }

    @Override
    public String toString() {
        return "AddRound{" +
                "roundId=" + roundId +
                ", teamIds=" + teamIds +
                ", gameId=" + gameId +
                ", roundScore=" + roundScore +
                ", spinScore=" + spinScore +
                ", spinToken=" + spinToken +
                '}';
    }
}
