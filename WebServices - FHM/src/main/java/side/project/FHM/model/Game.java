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

    @Column(name = "game_name", nullable = false)
    private String gameName;

//    @OneToMany
//    @JoinColumn(name = "round_id", nullable = true)
//    private List<Round> round;

    @Column(name = "round_id", nullable = true)
    private int roundId;

    @OneToOne
    @JoinColumn(name = "word_id", nullable = true)
    private Word word;

    @Column(name = "game_status", nullable = false)
    private String gameStatus;

    @Column(name = "letter_guessed")
    private String letterGuessed;

    @Column(name = "current_team_turn")
    private int currentTeamTurn;

    @Column(name = "current_round")
    private int currentRound;

    @Column(name = "total_team")
    private int totalTeam;

    public Game() {
    }

    public Game(int gameId, String gameName, int roundId, Word word, String gameStatus, String letterGuessed, int currentTeamTurn, int currentRound, int totalTeam) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.roundId = roundId;
        this.word = word;
        this.gameStatus = gameStatus;
        this.letterGuessed = letterGuessed;
        this.currentTeamTurn = currentTeamTurn;
        this.currentRound = currentRound;
        this.totalTeam = totalTeam;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Integer getRoundId() {
        return roundId;
    }

    public void setRoundId(Integer roundId) {
        this.roundId = roundId;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
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

    public int getTotalTeam() {
        return totalTeam;
    }

    public void setTotalTeam(int totalTeam) {
        this.totalTeam = totalTeam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;
        Game game = (Game) o;
        return gameId == game.gameId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId);
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", gameName='" + gameName + '\'' +
                ", roundId=" + roundId +
                ", word=" + word +
                ", gameStatus='" + gameStatus + '\'' +
                ", letterGuessed='" + letterGuessed + '\'' +
                ", currentTeamTurn=" + currentTeamTurn +
                ", currentRound=" + currentRound +
                ", totalTeam=" + totalTeam +
                '}';
    }
}
