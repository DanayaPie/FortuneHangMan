package play;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import model.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.util.*;

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

    private static StringBuilder wordToGuess = new StringBuilder();
    private static Map<Character, List<Integer>> charMap = new HashMap<>();

    // support async or sync connection
    final static CloseableHttpClient httpClient = HttpClients.createDefault();

    public static void addNewGame() throws IOException, URISyntaxException {

        System.out.print("Game Name: ");
        String gameName = scan.nextLine();
        game.setGameName(gameName);

        System.out.print("Total Number of Team: ");
        int totalTeam;

        while (true) {
            try {
                totalTeam = Integer.parseInt(scan.nextLine());

                if (totalTeam < 2 || totalTeam > 4) {
//                    System.out.println("int");
                    System.out.println("Total number of team must be a whole number and must be between 2-4.");
                    System.out.print("Total Number of Team: ");
                } else {
                    game.setTotalTeam(totalTeam);
                    break;
                }
            } catch (NumberFormatException e) {
//                System.out.println("not int");
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
            System.out.println("Game is not added! Status code: " + addedGameResponse.getStatusLine().getStatusCode() + " - " + addedGameResponse.getStatusLine().getReasonPhrase());
        }

        HttpEntity responseEntity = addedGameResponse.getEntity();
        game = mapper.readValue(EntityUtils.toString(responseEntity), new TypeReference<Game>() {
        });

        addTeam();

    }

    private static void addTeam() throws IOException, URISyntaxException {

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

    public static void addRound(List<Integer> teamIds) throws IOException, URISyntaxException {

        AddRound roundToAdd = new AddRound();
        roundToAdd.setTeamIds(teamIds);
//        game.setGameId(2);
        roundToAdd.setGameId(game.getGameId());

        HttpPost addRoundRequest = new HttpPost("http://localhost:8080/round");
        StringEntity roundToAddEntity = new StringEntity(mapper.writeValueAsString(roundToAdd)
                , ContentType.APPLICATION_JSON);

        addRoundRequest.setEntity(roundToAddEntity);
//        System.out.println("addRoundRequest: " + EntityUtils.toString(roundToAddEntity));

        CloseableHttpResponse addedRoundResponse = httpClient.execute(addRoundRequest);

        if (addedRoundResponse.getStatusLine().getStatusCode() != 200) {
            System.out.println("Team is not added! Status code: " + addedRoundResponse.getStatusLine().getStatusCode() + " - " + addedRoundResponse.getStatusLine().getReasonPhrase());
        }
        HttpEntity responseEntity = addedRoundResponse.getEntity();
        roundList = mapper.readValue(EntityUtils.toString(responseEntity)
                , new TypeReference<>() {
                });

        setRound();
    }

    private static void setRound() throws IOException, URISyntaxException {

//        System.out.println("roundList: " + roundList);

        for (int i = 0; i < roundList.size(); i++) {

            round.setRoundId(roundList.get(i).getRoundId().getRoundId());
            round.setTeamId(roundList.get(i).getRoundId().getTeamId());
            round.setGameId(roundList.get(i).getRoundId().getGame().getGameId());

            chooseCategory();
            updateGame();
            updateCurrentRound(i + 1);
            playTheGame();
        }
    }

    public static void updateCurrentRound(int currentRound) throws IOException, URISyntaxException {

//        game.setGameId(2);

        URIBuilder builder = new URIBuilder("http://localhost:8080/game/" + game.getGameId());

        if (game.getCurrentRound() != currentRound) {
            System.out.println("updatingGame currentRound: currentRound = " + currentRound);
            builder.setParameter("currentRound", Integer.toString(currentRound));
        }

        HttpPut updateGameRequest = new HttpPut(builder.build());
        StringEntity updateGameEntity = new StringEntity(mapper.writeValueAsString(updateGameRequest), ContentType.APPLICATION_JSON);
        updateGameRequest.setEntity(updateGameEntity);

        System.out.println(updateGameRequest.toString());

        CloseableHttpResponse updateGameResponse = httpClient.execute(updateGameRequest);

        if (updateGameResponse.getStatusLine().getStatusCode() != 200) {
            System.out.println("Current round in game is not updated! Status code: " + updateGameResponse.getStatusLine().getStatusCode() + " - " + updateGameResponse.getStatusLine().getReasonPhrase());
        }

        HttpEntity responseEntity = updateGameResponse.getEntity();
        game = mapper.readValue(EntityUtils.toString(responseEntity), new TypeReference<Game>() {
        });

//        System.out.println("Game: " + game.toString());
    }

    public static void chooseCategory() throws IOException, URISyntaxException {
        HttpGet getCategoriesRequest = new HttpGet("http://localhost:8080/category");

        CloseableHttpResponse getCategoriesResponse = httpClient.execute(getCategoriesRequest);
        HttpEntity getCategoriesEntity = getCategoriesResponse.getEntity();

        if (getCategoriesResponse.getStatusLine().getStatusCode() != 200) {
            System.out.println("Cannot get categories! Status code: " + getCategoriesResponse.getStatusLine().getStatusCode() + " - " + getCategoriesResponse.getStatusLine().getReasonPhrase());
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


    private static void getRamdomWord(String categoryName) throws IOException, URISyntaxException {
        HttpGet getRandomWordRequest = new HttpGet("http://localhost:8080/randWord/" + categoryName);

        CloseableHttpResponse getRandomWordReqponse = httpClient.execute(getRandomWordRequest);
        HttpEntity getRandomWordEntity = getRandomWordReqponse.getEntity();

        if (getRandomWordReqponse.getStatusLine().getStatusCode() != 200) {
            System.out.println("Cannot get random word! Status code: " + getRandomWordReqponse.getStatusLine().getStatusCode() + " - " + getRandomWordReqponse.getStatusLine().getReasonPhrase());
        }

        word = mapper.readValue(EntityUtils.toString(getRandomWordEntity), new TypeReference<Word>() {
        });

        updateWordInGame();
    }

    public static void updateWordInGame() throws URISyntaxException, IOException {

//        game.setGameId(2);
//        word.setWordId(1);

        URIBuilder builder = new URIBuilder("http://localhost:8080/game/" + game.getGameId());

        if (game.getWord() == null || (word.getWordId() != game.getWord().getWordId())) {
            System.out.println("updatingGame Word: wordId = " + word.getWordId());
            builder.setParameter("wordId", Integer.toString(word.getWordId()));
        }

        HttpPut updateGameRequest = new HttpPut(builder.build());
        StringEntity updateGameEntity = new StringEntity(mapper.writeValueAsString(updateGameRequest), ContentType.APPLICATION_JSON);
        updateGameRequest.setEntity(updateGameEntity);

        System.out.println(updateGameRequest.toString());

        CloseableHttpResponse updateGameResponse = httpClient.execute(updateGameRequest);

        if (updateGameResponse.getStatusLine().getStatusCode() != 200) {
            System.out.println("Word in game is not updated! Status code: " + updateGameResponse.getStatusLine().getStatusCode() + " - " + updateGameResponse.getStatusLine().getReasonPhrase());
        }

        HttpEntity responseEntity = updateGameResponse.getEntity();
        game = mapper.readValue(EntityUtils.toString(responseEntity), new TypeReference<Game>() {
        });

//        System.out.println("Game: " + game.toString());
    }

    public static void updateGame() throws URISyntaxException, IOException {

//        game.setGameId(1);
//        round.setRoundId(4);
//        game.setRoundId(3);

        URIBuilder builder = new URIBuilder("http://localhost:8080/game/" + game.getGameId());

        if (round.getRoundId() != game.getRoundId()) {
            System.out.println("updatingGame Round: roundId = " + round.getRoundId() + " gameId = " + game.getRoundId());
            builder.setParameter("roundId", Integer.toString(round.getRoundId()));
        }

        if (game.getGameStatus() == null) {
            System.out.println("updating game status");
            builder.addParameter("gameStatus", "in progress");
        }

        if (round.getTeamId() != game.getCurrentTeamTurn()) {
            System.out.println("updatingGame currentTeamTurn: roundTeamId = " + round.getTeamId() + " game.currentTeamTurn = " + game.getCurrentTeamTurn());
            builder.addParameter("currentTeamTurn", Integer.toString(round.getTeamId()));
        }

        HttpPut updateGameRequest = new HttpPut(builder.build());
        StringEntity updateGameEntity = new StringEntity(mapper.writeValueAsString(updateGameRequest), ContentType.APPLICATION_JSON);
        updateGameRequest.setEntity(updateGameEntity);

//        System.out.println(updateGameRequest.toString());

        CloseableHttpResponse updateGameResponse = httpClient.execute(updateGameRequest);

        if (updateGameResponse.getStatusLine().getStatusCode() != 200) {
            System.out.println("Game is not updated! Status code: " + updateGameResponse.getStatusLine().getStatusCode() + " - " + updateGameResponse.getStatusLine().getReasonPhrase());
        }

        HttpEntity responseEntity = updateGameResponse.getEntity();
        game = mapper.readValue(EntityUtils.toString(responseEntity), new TypeReference<Game>() {
        });

//        System.out.println("Game: " + game.toString());
    }

    public static void playTheGame() {

        setWordToGuess();

        while (true) {
            spinTheWheel();
            guessChar();
        }

    }

    private static void guessChar() {

        System.out.print("Guess a character: ");
        String guessedInput = scan.nextLine().trim().toUpperCase();
        char charGuessed = guessedInput.charAt(0);

        if (charMap.containsKey(charGuessed)) {

            List<Integer> charguessedList = charMap.get(charGuessed);
            int blankChar = 0;

            for (int i = 0; i < wordToGuess.length(); i++) {

                if (wordToGuess.charAt(i) == '_') {
                    blankChar += 1;

                    if (charguessedList.contains(blankChar)) {
                        wordToGuess.setCharAt(blankChar, charGuessed);
                    }
                }
            }

        } else {
            // change team
            System.out.println("WRONG!!!");
        }

        System.out.println(wordToGuess);

    }

    public static void setWordToGuess() {

        List<Character> symbolsNoSpaceAfter = new ArrayList<>();
        symbolsNoSpaceAfter.add('\'');
        symbolsNoSpaceAfter.add('-');

        List<Character> symbolsWithSpaceAfter = new ArrayList<>();
        symbolsWithSpaceAfter.add('!');
        symbolsWithSpaceAfter.add('%');
        symbolsWithSpaceAfter.add('&');
        symbolsWithSpaceAfter.add(':');
        symbolsWithSpaceAfter.add(';');
        symbolsWithSpaceAfter.add('$');

        String w = "It's fun to 'code!";
        word.setWord(w.toUpperCase());
        String wordToSensor = word.getWord();

//        game.getWord().setWord(w.toUpperCase());
//        String wordToSensor = game.getWord().getWord();

        for (int i = 0; i < wordToSensor.length(); i++) {

            char currentChar = wordToSensor.charAt(i);

            if (!symbolsNoSpaceAfter.contains(currentChar) && !symbolsWithSpaceAfter.contains(currentChar) && currentChar != ' ') {
                if (!charMap.containsKey(currentChar)) {
                    charMap.put(currentChar, new ArrayList<>()); // instantiate arrayList at the first char
                    charMap.get(currentChar).add(i); // add the first index to the value of map
                } else {
                    charMap.get(currentChar).add(i);
                }

            } else if (currentChar == '\'' && (i + 1) < wordToSensor.length()) { // add character after ' because it will get skip
                char nextChar = wordToSensor.charAt(i+1);

                if (!charMap.containsKey(nextChar)) {
                    charMap.put(nextChar, new ArrayList<>()); // instantiate arrayList at the first char
                    charMap.get(nextChar).add(i+1); // add the first index to the value of map
                } else {
                    charMap.get(nextChar).add(i+1);
                }
            }

            if (symbolsWithSpaceAfter.contains(currentChar)) { // if symbols allows space after
                wordToGuess.append(currentChar);
            } else if (symbolsNoSpaceAfter.contains(currentChar)) { // if symbols dont allows space after if next char is an alphabet
                wordToGuess.append(currentChar);

                // if char after ' or - is an alphabet and if i < wordToSensor length, add space after
                if ((i + 1) < wordToSensor.length() && wordToSensor.charAt(i + 1) >= 'A' && wordToSensor.charAt(i + 1) <= 'Z') {
                    wordToGuess.append("_");
                    i++;
                }
            } else if (i == 0) {
                wordToGuess.append("_");
            } else if (wordToSensor.charAt(i) == ' ') {
                wordToGuess.append("  ");
            } else {
                wordToGuess.append(" _");
            }
        }

        System.out.println(charMap.toString());

        System.out.println(wordToGuess);
    }

    private static void spinTheWheel() {
        // spin the wheel
        System.out.print("Spin the Wheel and enter amount here: ");
//        System.out.println("Amount: ");

        int spinScore;

        while (true) {
            try {
                spinScore = Integer.parseInt(scan.nextLine());
                round.setSpinScore(spinScore);
                break;
            } catch (NumberFormatException e) {
                System.out.print("Amount must be a whole number.");
                System.out.print("Amount: ");
            }
        }
    }
}
