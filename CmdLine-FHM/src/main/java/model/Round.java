package model;

import java.util.List;
import java.util.Objects;

public class Round {

    private int roundId;
    private int teamId;
    private List<Integer> teamIds;
    private int gameId;
    private int roundScore;
    private int spinScore;
    private boolean spinToken;

    public Round() {
    }

    public Round(int roundId, int teamId, List<Integer> teamIds, int gameId, int roundScore, int spinScore, boolean spinToken) {
        this.roundId = roundId;
        this.teamId = teamId;
        this.teamIds = teamIds;
        this.gameId = gameId;
        this.roundScore = roundScore;
        this.spinScore = spinScore;
        this.spinToken = spinToken;
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

    public List<Integer> getTeamIds() {
        return teamIds;
    }

    public void setTeamIds(List<Integer> teamIds) {
        this.teamIds = teamIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Round)) return false;
        Round round = (Round) o;
        return roundId == round.roundId && teamId == round.teamId && gameId == round.gameId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roundId, teamId, gameId);
    }

    @Override
    public String toString() {
        return "Round{" +
                "roundId=" + roundId +
                ", teamId=" + teamId +
                ", teamIds=" + teamIds +
                ", gameID=" + gameId +
                ", roundScore=" + roundScore +
                ", spinScore=" + spinScore +
                ", spinToken=" + spinToken +
                '}';
    }
}
