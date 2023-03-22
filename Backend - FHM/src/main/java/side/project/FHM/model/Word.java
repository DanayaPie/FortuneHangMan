package side.project.FHM.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_id", insertable = false)
    private int wordId;

    @Column(name = "word_category", nullable = false)
    private String wordCategory;

    @Column(name = "word", nullable = false)
    private String word;

    public Word() {
    }

    public Word(int wordId) {
    }

    public Word(int wordId, String wordCategory, String word) {
        this.wordId = wordId;
        this.wordCategory = wordCategory;
        this.word = word;
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public String getWordCategory() {
        return wordCategory;
    }

    public void setWordCategory(String wordCategory) {
        this.wordCategory = wordCategory;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return wordId == word1.wordId && Objects.equals(wordCategory, word1.wordCategory) && Objects.equals(word, word1.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wordId, wordCategory, word);
    }

    @Override
    public String toString() {
        return "Word{" +
                "wordId=" + wordId +
                ", wordCategory='" + wordCategory + '\'' +
                ", word='" + word + '\'' +
                '}';
    }
}
