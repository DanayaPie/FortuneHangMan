package practice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Game;
import model.Word;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetRequests {

    // support async or sync connection
    final static CloseableHttpClient httpClient = HttpClients.createDefault();

    public void getAllWordsRequest() throws IOException {
        HttpGet getAllWordsRequest = new HttpGet("http://localhost:8080/word");

        CloseableHttpResponse getAllWordsReqponse = httpClient.execute(getAllWordsRequest); // get response as json
        HttpEntity entity = getAllWordsReqponse.getEntity(); // convert response to entity (name and value pairs

        ObjectMapper wordsMapper = new ObjectMapper(); // convert entity to string
        List<Word> wordList = new ArrayList<Word>();

        wordList = wordsMapper.readValue(EntityUtils.toString(entity), new TypeReference<List<Word>>() {
        });

        for (Word word : wordList) {
            System.out.println(word.toString());
        }
    }

    public void getWordByWordId(int wordId) throws IOException {
        String url = String.format("http://localhost:8080/word/" + wordId);
        HttpGet getAllWordsRequest = new HttpGet(url);

        CloseableHttpResponse getAllWordsReqponse = httpClient.execute(getAllWordsRequest); // get response as json
        HttpEntity responseEntity = getAllWordsReqponse.getEntity(); // convert response to entity (name and value pairs

        ObjectMapper wordMapper = new ObjectMapper();
        Word word = new Word();

        // read and parse value in the entity to string and store it in word object
        word = wordMapper.readValue(EntityUtils.toString(responseEntity), new TypeReference<Word>() {
        });

        System.out.println(word);
        System.out.println(word.getWord());
    }

    public static void getAllGamesRequest() throws IOException {
        HttpGet getGamesRequest = new HttpGet("http://localhost:8080/game");

        CloseableHttpResponse getGamesResponse = httpClient.execute(getGamesRequest); // get response as json
        HttpEntity entity = getGamesResponse.getEntity(); // convert response to entity (name value pairs)

//        System.out.println(EntityUtils.toString(entity));

        if (getGamesResponse.getStatusLine().getStatusCode() != 200) {
            System.out.println("Connection is bad, status code: " + getGamesResponse.getStatusLine().getStatusCode());
            return;
        }

        ObjectMapper gamesMapper = new ObjectMapper(); // convert entity to string
        List<Game> gameList = new ArrayList<Game>();

        gameList = gamesMapper.readValue(EntityUtils.toString(entity), new TypeReference<List<Game>>() {
        });

        for (Game game : gameList) {
            System.out.println(game.toString());
        }
    }
}
