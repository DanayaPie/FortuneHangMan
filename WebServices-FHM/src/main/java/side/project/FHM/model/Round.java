package side.project.FHM.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "round")
public class Round {

    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private RoundId roundId;

    @Column(name = "round_score", nullable = false)
    private int roundScore;

    @Column(name = "spin_score", nullable = false)
    private int spinScore;

    @Column(name = "spin_token", nullable = false)
    private boolean spinToken;

    public Round() {
    }

    public Round(RoundId roundId, int roundScore, int spinScore, boolean spinToken) {
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
    public String toString() {
        return "Round{" +
                "roundId=" + roundId +
                ", roundScore=" + roundScore +
                ", spinScore=" + spinScore +
                ", spinToken=" + spinToken +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Round)) return false;
        Round round = (Round) o;
        return roundScore == round.roundScore && spinScore == round.spinScore && spinToken == round.spinToken && Objects.equals(roundId, round.roundId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roundId, roundScore, spinScore, spinToken);
    }
}



