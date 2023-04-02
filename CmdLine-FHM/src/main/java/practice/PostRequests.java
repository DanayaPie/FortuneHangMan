package practice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Game;
import model.Word;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class PostRequests {

    // support async or sync connection
    final static CloseableHttpClient httpClient = HttpClients.createDefault();

    public static void addNewWord(String category, String word) throws IOException {

        HttpPost addWordRequest = new HttpPost("http://localhost:8080/word");

        Word wordToAdd = new Word();
        wordToAdd.setCategory(category);
        wordToAdd.setWord(word);

        // convert word object to String entity (http client string object that has content type attached)
        ObjectMapper mapper = new ObjectMapper();
        StringEntity wordToAddJson = new StringEntity(mapper.writeValueAsString(wordToAdd)
                , ContentType.APPLICATION_JSON);

        addWordRequest.setEntity(wordToAddJson); // set json object to the request body

        // create http response
        CloseableHttpResponse addedWordResponse = httpClient.execute(addWordRequest);

        if (addedWordResponse.getStatusLine().getStatusCode() != 200) {
            System.out.println("Word is not added! Status code: " + addedWordResponse.getStatusLine().getStatusCode());
        }

        HttpEntity responseEntity = addedWordResponse.getEntity();

        System.out.println(EntityUtils.toString(responseEntity));
    }

    public static void addNewGame(String gameName, int totalTeam) throws IOException {
        HttpPost addGameRequest = new HttpPost("http://localhost:8080/game");

        Game gameToAdd = new Game();
        gameToAdd.setGameName(gameName);
        gameToAdd.setTotalTeam(totalTeam);

        ObjectMapper gameMapper = new ObjectMapper();
        StringEntity gameToAddJson = new StringEntity(gameMapper.writeValueAsString(gameToAdd)
                , ContentType.APPLICATION_JSON);

        addGameRequest.setEntity(gameToAddJson);

        CloseableHttpResponse addedGameResponse = httpClient.execute(addGameRequest);

        if (addedGameResponse.getStatusLine().getStatusCode() != 200) {
            System.out.println("Game is not added! Status code: " + addedGameResponse.getStatusLine().getStatusCode());
        }

        HttpEntity responseEntity = addedGameResponse.getEntity();
        Game addedGame = gameMapper.readValue(EntityUtils.toString(responseEntity)
                , new TypeReference<Game>() {});

        System.out.println("Game ID: " + addedGame.getGameId());
    }
}
