package play;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import model.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayGame {

    private static Scanner scan = new Scanner(System.in);
    private static ObjectMapper mapper = new ObjectMapper();
    private static HttpClient client = HttpClient.newHttpClient();
    private static Gson gson = new Gson();

    private static Game game = new Game();
    private static List<RoundsInGame> roundList = new ArrayList<>();
    private static Round round = new Round();
    private static Word word = new Word();
    private static List<Integer> wordIdList = new ArrayList<>();

    // support async or sync connection
    final static CloseableHttpClient httpClient = HttpClients.createDefault();

    public static void addNewGame() throws IOException {

        System.out.print("Game Name: ");
        String gameName = scan.nextLine();
        game.setGameName(gameName);

        System.out.print("Total Number of Team: ");
        int totalTeam;

        while (true) {
            try {
                totalTeam = Integer.parseInt(scan.nextLine());

                if (totalTeam < 2 || totalTeam > 4) {
                    System.out.println("int");
                    System.out.println("Total number of team must be a whole number and must be between 2-4.");
                    System.out.print("Total Number of Team: ");
                } else {
                    game.setTotalTeam(totalTeam);
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("not int");
                System.out.println("Total number of team must be a whole number and must be between 2-4.");
                System.out.print("Total Number of Team: ");
            }
        }

    /*
        Request
     */
        HttpPost addGameRequest = new HttpPost("http://localhost:8080/game");
        StringEntity gameToAddEntity = new StringEntity(mapper.writeValueAsString(game)
                , ContentType.APPLICATION_JSON);
        addGameRequest.setEntity(gameToAddEntity);

    /*
        Response
     */
        CloseableHttpResponse addedGameResponse = httpClient.execute(addGameRequest);

        if (addedGameResponse.getStatusLine().

                getStatusCode() != 200) {
            System.out.println("Round is not added! Status code: " + addedGameResponse.getStatusLine().getStatusCode() + " - " + addedGameResponse.getStatusLine().getReasonPhrase());
        }

        HttpEntity responseEntity = addedGameResponse.getEntity();
        mapper.readValue(EntityUtils.toString(responseEntity), new TypeReference<Game>() {
        });

        addTeam();

    }

    private static void addTeam() throws IOException {

        int totalTeam = game.getTotalTeam();
        String teamName;
        int teamTurn;
        int gameId = game.getGameId();
        List<Integer> teamIds = new ArrayList<>();

        for (int i = 1; i < totalTeam + 1; i++) {

            System.out.print("Team " + i + " Name: ");
            teamName = scan.nextLine();
            teamTurn = i;

            Team teamToAdd = new Team();
            teamToAdd.setTeamName(teamName);
            teamToAdd.setTeamTurn(teamTurn);
            teamToAdd.setGameId(gameId);

            HttpPost addTeamRequest = new HttpPost("http://localhost:8080/team");
            StringEntity teamToAddEntity = new StringEntity(mapper.writeValueAsString(teamToAdd)
                    , ContentType.APPLICATION_JSON);
            addTeamRequest.setEntity(teamToAddEntity);

            CloseableHttpResponse addedTeamResponse = httpClient.execute(addTeamRequest);

            if (addedTeamResponse.getStatusLine().getStatusCode() != 200) {
                System.out.println("Round is not added! Status code: " + addedTeamResponse.getStatusLine().getStatusCode() + " - " + addedTeamResponse.getStatusLine().getReasonPhrase());
            }

            HttpEntity responseEntity = addedTeamResponse.getEntity();
            Team teamAdded = mapper.readValue(EntityUtils.toString(responseEntity)
                    , new TypeReference<Team>() {
                    });

            teamAdded.getTeamId();
//            System.out.println("teamAdded.getTeamId(): " + teamAdded.getTeamId());
            teamIds.add(teamAdded.getTeamId());
//            System.out.println("teamIds: " + teamIds);
        }

        addRound(teamIds);
    }

    public static void addRound(List<Integer> teamIds) throws IOException {

        AddRound roundToAdd = new AddRound();
        roundToAdd.setTeamIds(teamIds);
        game.setGameId(2);
        roundToAdd.setGameId(game.getGameId());

        HttpPost addRoundRequest = new HttpPost("http://localhost:8080/round");
        StringEntity roundToAddEntity = new StringEntity(mapper.writeValueAsString(roundToAdd)
                , ContentType.APPLICATION_JSON);

        addRoundRequest.setEntity(roundToAddEntity);
//        System.out.println("addRoundRequest: " + EntityUtils.toString(roundToAddEntity));

        CloseableHttpResponse addedRoundResponse = httpClient.execute(addRoundRequest);

        if (addedRoundResponse.getStatusLine().getStatusCode() != 200) {
            System.out.println("Round is not added! Status code: " + addedRoundResponse.getStatusLine().getStatusCode() + " - " + addedRoundResponse.getStatusLine().getReasonPhrase());
        }
        HttpEntity responseEntity = addedRoundResponse.getEntity();
        roundList = mapper.readValue(EntityUtils.toString(responseEntity)
                , new TypeReference<>() {
                });

        setRound();
    }

    private static void setRound() throws IOException {

        for (int i = 0; i < roundList.size(); i++) {

            round.setRoundId(roundList.get(i).getRoundId().getRoundId());
            round.setTeamId(roundList.get(i).getRoundId().getTeamId());
            round.setGameId(roundList.get(i).getRoundId().getGame().getGameId());

            chooseCategory();
            updateGame();
        }
    }

    public static void chooseCategory() throws IOException {
        HttpGet getCategoriesRequest = new HttpGet("http://localhost:8080/category");

        CloseableHttpResponse getCategoriesResponse = httpClient.execute(getCategoriesRequest);
        HttpEntity getCategoriesEntity = getCategoriesResponse.getEntity();

        if (getCategoriesResponse.getStatusLine().getStatusCode() != 200) {
            System.out.println("Round is not added! Status code: " + getCategoriesResponse.getStatusLine().getStatusCode() + " - " + getCategoriesResponse.getStatusLine().getReasonPhrase());
        }

        List<String> categories = mapper.readValue(EntityUtils.toString(getCategoriesEntity), new TypeReference<>() {
        });
        StringBuilder categoriesList = new StringBuilder();

        for (int i = 0; i < categories.size(); i++) {
            if (i != categories.size() - 1) {
                categoriesList.append(categories.get(i) + ", ");
            } else {
                categoriesList.append(categories.get(i));
            }
        }

        // choose category
        System.out.println("Choose one of these categories: " + categoriesList);
        System.out.print("Category: ");
        String categoryName;

        while (true) {
            try {
                categoryName = scan.nextLine().toUpperCase();

                if (!categories.contains(categoryName.toUpperCase())) {
                    System.out.println("Category chosen is not in the list. Please type one of these categories:" + categoriesList);
                    System.out.print("Category: ");
                } else {
                    categoryName = categoryName.replaceAll(" ", "%20");

                    // get random word
                    getRamdomWord(categoryName);
//                    System.out.println("word before: " + word);
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Category chosen is not in the list. Please type one of these categories:" + categoriesList);
                System.out.print("Category: ");
            }
        }

        if (wordIdList.contains(word.getWordId())) {
            getRamdomWord(categoryName);
        } else {
            wordIdList.add(word.getWordId());
        }
//        System.out.println("word: " + word);
//        System.out.println("wordIdList: " + wordIdList);
    }

    private static void getRamdomWord(String categoryName) throws IOException {
        HttpGet getRandomWordRequest = new HttpGet("http://localhost:8080/randWord/" + categoryName);

        CloseableHttpResponse getRandomWordReqponse = httpClient.execute(getRandomWordRequest);
        HttpEntity getRandomWordEntity = getRandomWordReqponse.getEntity();

        if (getRandomWordReqponse.getStatusLine().getStatusCode() != 200) {
            System.out.println("Round is not added! Status code: " + getRandomWordReqponse.getStatusLine().getStatusCode() + " - " + getRandomWordReqponse.getStatusLine().getReasonPhrase());
        }

        word = mapper.readValue(EntityUtils.toString(getRandomWordEntity), new TypeReference<Word>() {
        });
    }

    private static void updateGame() {

    }
}
