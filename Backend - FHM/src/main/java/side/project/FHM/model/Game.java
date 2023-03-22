package side.project.FHM.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id", insertable = false)
    private int gameId;

    @OneToOne
    private Round roundId;

    @OneToOne
    private Word wordId;

    @Column(name = "game_status", nullable = false)
    private String gameStatus;

    @Column(name = "letter_guessed")
    private String letterGuessed;

    @Column(name = "current_team_turn", nullable = false)
    private int currentTeamTurn;

    @Column(name = "current_round", nullable = false)
    private int currentRound;

    public Game() {
    }

    public Game(int gameId, Round roundId, Word wordId, String gameStatus, String letterGuessed, int currentTeamTurn, int currentRound) {
        this.gameId = gameId;
        this.roundId = roundId;
        this.wordId = wordId;
        this.gameStatus = gameStatus;
        this.letterGuessed = letterGuessed;
        this.currentTeamTurn = currentTeamTurn;
        this.currentRound = currentRound;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public Round getRoundId() {
        return roundId;
    }

    public void setRoundId(Round roundId) {
        this.roundId = roundId;
    }

    public Word getWordId() {
        return wordId;
    }

    public void setWordId(Word wordId) {
        this.wordId = wordId;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getLetterGuessed() {
        return letterGuessed;
    }

    public void setLetterGuessed(String letterGuessed) {
        this.letterGuessed = letterGuessed;
    }

    public int getCurrentTeamTurn() {
        return currentTeamTurn;
    }

    public void setCurrentTeamTurn(int currentTeamTurn) {
        this.currentTeamTurn = currentTeamTurn;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return gameId == game.gameId && currentTeamTurn == game.currentTeamTurn && currentRound == game.currentRound && Objects.equals(roundId, game.roundId) && Objects.equals(wordId, game.wordId) && Objects.equals(gameStatus, game.gameStatus) && Objects.equals(letterGuessed, game.letterGuessed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, roundId, wordId, gameStatus, letterGuessed, currentTeamTurn, currentRound);
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", roundId=" + roundId +
                ", wordId=" + wordId +
                ", gameStatus='" + gameStatus + '\'' +
                ", letterGuessed='" + letterGuessed + '\'' +
                ", currentTeamTurn=" + currentTeamTurn +
                ", currentRound=" + currentRound +
                '}';
    }
}
