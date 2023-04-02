package model;

import java.util.Objects;

public class RoundsInGame {

    private RoundId roundId;
    private int roundScore;
    private int spinScore;
    private boolean spinToken;

    public RoundsInGame() {
    }

    public RoundsInGame(RoundId roundId, int roundScore, int spinScore, boolean spinToken) {
        this.roundId = roundId;
        this.roundScore = roundScore;
        this.spinScore = spinScore;
        this.spinToken = spinToken;
    }

    public RoundId getRoundId() {
        return roundId;
    }

    public void setRoundId(RoundId roundId) {
        this.roundId = roundId;
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
        if (!(o instanceof RoundsInGame)) return false;
        RoundsInGame that = (RoundsInGame) o;
        return Objects.equals(roundId, that.roundId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roundId);
    }

    @Override
    public String toString() {
        return "RoundResponse{" +
                "roundId=" + roundId +
                ", roundScore=" + roundScore +
                ", spinScore=" + spinScore +
                ", spinToken=" + spinToken +
                '}';
    }
}
