package side.project.FHM.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import side.project.FHM.exception.InvalidParameterException;
import side.project.FHM.exception.WordDoesNotExist;
import side.project.FHM.model.Word;
import side.project.FHM.service.WordService;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin(origins = {"http://localhost:8080","http://localhost:3000"},maxAge = 3600)
@RestController
public class WordController implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(WordController.class);
    private Set<Word> wordsInDb = new HashSet<>();

    @Autowired
    private WordService wordService;


    @GetMapping(path = "/word")
    public ResponseEntity<Object> getAllWords() {
        logger.info("WordController.getAllWords() invoked");

        try {
            Set<Word> allWords = wordService.getAllWords();
            return ResponseEntity.status(200).body(allWords);

        } catch (WordDoesNotExist | InvalidParameterException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping(path = "/word")
    public ResponseEntity<Object> addWord(@RequestBody Map<String, String> json) {
        logger.info("WordController.addWord() invoked");

        try {
            Word wordToAdd = wordService.addWord(wordsInDb, json.get("category"), json.get("word"));
            return ResponseEntity.status(200).body(wordToAdd);

        } catch (InvalidParameterException | WordDoesNotExist e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping(path = "/word/{wordId}")
    public ResponseEntity<Object> getWordByWordId(@PathVariable int wordId) {
        logger.info("WordController.getWordByWordId() invoked");

        try {
            Word wordToGet = wordService.getWordByWordId(wordId);
            return ResponseEntity.status(200).body(wordToGet);

        } catch (InvalidParameterException | WordDoesNotExist e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping(path = "/randWord/{category}")
    public ResponseEntity<Object> getRandomWordByCategory(@PathVariable String category) {
        logger.info("WordController.getRandomWordByCategory() invoked");

        try {
            Word randomWord = wordService.getRandomWordByCategory(category);
            return ResponseEntity.status(200).body(randomWord);

        } catch (InvalidParameterException | WordDoesNotExist e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping(path = "/category")
    public ResponseEntity<Object> getAllCategories() {
        logger.info("WordController.getAllCategories() invoked");

        try {
            List<String> allCategories = wordService.getAllCategories();
            return ResponseEntity.status(200).body(allCategories);

        } catch (InvalidParameterException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            wordsInDb = wordService.getAllWords();
        } catch (WordDoesNotExist | InvalidParameterException e) {
            e.getMessage();
        }
    }
}
