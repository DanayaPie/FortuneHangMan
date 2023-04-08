package side.project.FHM.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id", insertable = false)
    private Integer gameId;

    @Column(name = "game_name", nullable = false)
    private String gameName;

//    @OneToMany
//    @JoinColumn(name = "round_id", nullable = true)
//    private List<Round> round;

    @Column(name = "round_id", nullable = true)
    private Integer roundId;

    @OneToOne
    @JoinColumn(name = "word_id", nullable = true)
    private Word word;

    @Column(name = "game_status", nullable = false)
    private String gameStatus;

    @Column(name = "letter_guessed")
    private String letterGuessed;

    @Column(name = "current_team_turn")
    private Integer currentTeamTurn;

    @Column(name = "current_round")
    private Integer currentRound;

    @Column(name = "total_team")
    private Integer totalTeam;

    public Game() {
    }

    public Game(Integer gameId, String gameName, Integer roundId, Word word, String gameStatus, String letterGuessed, Integer currentTeamTurn, Integer currentRound, Integer totalTeam) {
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

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
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

    public Integer getCurrentTeamTurn() {
        return currentTeamTurn;
    }

    public void setCurrentTeamTurn(Integer currentTeamTurn) {
        this.currentTeamTurn = currentTeamTurn;
    }

    public Integer getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(Integer currentRound) {
        this.currentRound = currentRound;
    }

    public Integer getTotalTeam() {
        return totalTeam;
    }

    public void setTotalTeam(Integer totalTeam) {
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
