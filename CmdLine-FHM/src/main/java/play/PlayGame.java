package play;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import constant.WordConstant;
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
    // support async or sync connection
    final static CloseableHttpClient httpClient = HttpClients.createDefault();

    private static Game game = new Game();
    private static List<Team> teamList = new ArrayList<>();
    private static Team team = new Team();
    private static List<RoundsInGame> roundList = new ArrayList<>();
    private static Round round = new Round();
    private static List<Integer> wordIdList = new ArrayList<>();
    private static Word word = new Word();

    private static StringBuilder controlWord = new StringBuilder();
    private static StringBuilder wordToGuess = new StringBuilder();
    private static Map<Character, List<Integer>> charInWordMap = new HashMap<>();
    private static Set<Character> charGuessedSet = new HashSet<>();

    private static Set<Character> symbolsNoSpaceAfter = WordConstant.symbolsNoSpaceAfter();
    private static Set<Character> symbolsWithSpaceAfter = WordConstant.symbolsWithSpaceAfter();
    private static Set<Character> vowels = WordConstant.vowel();

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

    public static void addTeam() throws IOException, URISyntaxException {

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

            teamList.add(teamAdded);

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

    public static void setRound() throws IOException, URISyntaxException {

//        System.out.println("roundList: " + roundList);

        for (int i = 0; i < roundList.size(); i++) {

            System.out.println("-----");
            System.out.println("Round " + (i + 1) + " begin!!!");
            System.out.println("-----");

            wordToGuess.setLength(0);
            controlWord.setLength(0);

            int teamCounter = i;

            round.setRoundId(roundList.get(i).getRoundId().getRoundId());
            round.setTeamId(roundList.get(i).getRoundId().getTeamId());
            round.setGameId(roundList.get(i).getRoundId().getGame().getGameId());

            team.setTeamId(teamList.get(i).getTeamId());
            team.setTeamName(teamList.get(i).getTeamName());
            team.setTeamTurn(teamList.get(i).getTeamTurn());
            team.setGameId(teamList.get(i).getGameId());
            team.setTotalScore(teamList.get(i).getTotalScore());

            System.out.println("Team " + team.getTeamName() + " turn.");
            System.out.println("Your information: " + team);

            chooseCategory();
            updateGameDB();
            updateCurrentRoundInGameDB(i + 1);
            playTheGame(teamCounter);
        }
    }

    public static void updateCurrentRoundInGameDB(int currentRound) throws IOException, URISyntaxException {

//        game.setGameId(2);

        URIBuilder builder = new URIBuilder("http://localhost:8080/game/" + game.getGameId());

        if (game.getCurrentRound() != currentRound) {
//            System.out.println("updatingGame currentRound: currentRound = " + currentRound);
            builder.setParameter("currentRound", Integer.toString(currentRound));
        }

        HttpPut updateGameRequest = new HttpPut(builder.build());
        StringEntity updateGameEntity = new StringEntity(mapper.writeValueAsString(updateGameRequest), ContentType.APPLICATION_JSON);
        updateGameRequest.setEntity(updateGameEntity);

//        System.out.println(updateGameRequest.toString());

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
        System.out.println("-----");
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
                    getRandomWord(categoryName);
//                    System.out.println("word before: " + word);
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Category chosen is not in the list. Please type one of these categories:" + categoriesList);
                System.out.print("Category: ");
            }
        }

        if (wordIdList.contains(word.getWordId())) {
            getRandomWord(categoryName);
        } else {
            wordIdList.add(word.getWordId());
        }

//        System.out.println("word: " + word);
//        System.out.println("wordIdList: " + wordIdList);
    }


    private static void getRandomWord(String categoryName) throws IOException, URISyntaxException {
        HttpGet getRandomWordRequest = new HttpGet("http://localhost:8080/randWord/" + categoryName);

        CloseableHttpResponse getRandomWordReqponse = httpClient.execute(getRandomWordRequest);
        HttpEntity getRandomWordEntity = getRandomWordReqponse.getEntity();

        if (getRandomWordReqponse.getStatusLine().getStatusCode() != 200) {
            System.out.println("Cannot get random word! Status code: " + getRandomWordReqponse.getStatusLine().getStatusCode() + " - " + getRandomWordReqponse.getStatusLine().getReasonPhrase());
        }

        word = mapper.readValue(EntityUtils.toString(getRandomWordEntity), new TypeReference<Word>() {
        });

        updateWordInGameDB();
    }

    public static void updateWordInGameDB() throws URISyntaxException, IOException {

//        game.setGameId(2);
//        word.setWordId(1);

        URIBuilder builder = new URIBuilder("http://localhost:8080/game/" + game.getGameId());

        if (game.getWord() == null || (word.getWordId() != game.getWord().getWordId())) {
            System.out.println("updatingGame Word: wordId = " + word.getWordId());
            builder.setParameter("wordId", Integer.toString(word.getWordId()));
            game.setWord(word);
        }

        HttpPut updateGameRequest = new HttpPut(builder.build());
        StringEntity updateGameEntity = new StringEntity(mapper.writeValueAsString(updateGameRequest), ContentType.APPLICATION_JSON);
        updateGameRequest.setEntity(updateGameEntity);

//        System.out.println(updateGameRequest.toString());

        CloseableHttpResponse updateGameResponse = httpClient.execute(updateGameRequest);

        if (updateGameResponse.getStatusLine().getStatusCode() != 200) {
            System.out.println("Word in game is not updated! Status code: " + updateGameResponse.getStatusLine().getStatusCode() + " - " + updateGameResponse.getStatusLine().getReasonPhrase());
        }

        HttpEntity responseEntity = updateGameResponse.getEntity();
        game = mapper.readValue(EntityUtils.toString(responseEntity), new TypeReference<Game>() {
        });

//        System.out.println("Game: " + game.toString());
    }

    public static void updateGameDB() throws URISyntaxException, IOException {

//        game.setGameId(1);
//        round.setRoundId(4);
//        game.setRoundId(3);

        URIBuilder builder = new URIBuilder("http://localhost:8080/game/" + game.getGameId());

        if (round.getRoundId() != game.getRoundId()) {
//            System.out.println("updatingGame Round: roundId = " + round.getRoundId() + " gameId = " + game.getRoundId());
            builder.setParameter("roundId", Integer.toString(round.getRoundId()));
            game.setRoundId(round.getRoundId());
        }

        if (game.getGameStatus() == null) {
//            System.out.println("updating game status");
            builder.addParameter("gameStatus", "in progress");
            game.setGameStatus("IN PROGRESS");
        }

        if (round.getTeamId() != game.getCurrentTeamTurn()) {
//            System.out.println("updatingGame currentTeamTurn: roundTeamId = " + round.getTeamId() + " game.currentTeamTurn = " + game.getCurrentTeamTurn());
            builder.addParameter("currentTeamTurn", Integer.toString(round.getTeamId()));
            game.setCurrentTeamTurn(round.getTeamId());
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

    private static void updateCharGuessedInGame(char charGuessed) throws URISyntaxException, IOException {

        URIBuilder builder = new URIBuilder("http://localhost:8080/game/" + game.getGameId());

        String charGuessedString = Character.toString(charGuessed);
        builder.addParameter("letterGuessed", charGuessedString);
        game.setLetterGuessed(charGuessedString);

        HttpPut updateGameRequest = new HttpPut(builder.build());
        StringEntity updateGameEntity = new StringEntity(mapper.writeValueAsString(updateGameRequest), ContentType.APPLICATION_JSON);
        updateGameRequest.setEntity(updateGameEntity);

//        System.out.println(updateGameRequest);

        CloseableHttpResponse updateGameResponse = httpClient.execute(updateGameRequest);

        if (updateGameResponse.getStatusLine().getStatusCode() != 200) {
            System.out.println("Character guessed in game is not updated! Status code: " + updateGameResponse.getStatusLine().getStatusCode() + " - " + updateGameResponse.getStatusLine().getReasonPhrase());
        }

        HttpEntity responseEntity = updateGameResponse.getEntity();
        game = mapper.readValue(EntityUtils.toString(responseEntity), new TypeReference<Game>() {
        });

        //        System.out.println("Game: " + game.toString());
    }

    private static void updateTeamInDB() throws URISyntaxException, IOException {

        URIBuilder builder = new URIBuilder("http://localhost:8080/team/" + team.getTeamId());
        builder.addParameter("totalScore", Integer.toString(team.getTotalScore()));

        HttpPut updateGameRequest = new HttpPut(builder.build());
        StringEntity updateGameEntity = new StringEntity(mapper.writeValueAsString(updateGameRequest), ContentType.APPLICATION_JSON);
        updateGameRequest.setEntity(updateGameEntity);

//        System.out.println(updateGameRequest.toString());

        CloseableHttpResponse updateTeamResponse = httpClient.execute(updateGameRequest);

        if (updateTeamResponse.getStatusLine().getStatusCode() != 200) {
            System.out.println("Team's total score is not updated! Status code: " + updateTeamResponse.getStatusLine().getStatusCode() + " - " + updateTeamResponse.getStatusLine().getReasonPhrase());
        }

        HttpEntity responseEntity = updateTeamResponse.getEntity();
        team = mapper.readValue(EntityUtils.toString(responseEntity), new TypeReference<Team>() {
        });

        //        System.out.println("Tame: " + team.toString());
    }

    private static void getTeamByTeamId(int teamId) throws IOException {
        HttpGet getTeamByTeamIdRequest = new HttpGet("http://localhost:8080/team/" + teamId);

        CloseableHttpResponse getTeamByTeamIdResponse = httpClient.execute(getTeamByTeamIdRequest);
        HttpEntity responseEntity = getTeamByTeamIdResponse.getEntity();

        if (getTeamByTeamIdResponse.getStatusLine().getStatusCode() != 200) {
            System.out.println("Cannot get team by team ID of " + teamId + "! Status code: " + getTeamByTeamIdResponse.getStatusLine().getStatusCode() + " - " + getTeamByTeamIdResponse.getStatusLine().getReasonPhrase());
        }

        team = mapper.readValue(EntityUtils.toString(responseEntity), new TypeReference<Team>() {
        });

        //System.out.println("Team: " + team.toString());
    }

    public static void updateRoundDB() throws URISyntaxException, IOException {

//        round.setRoundId(31);
//        round.setSpinToken(true);
//        team.setTeamId(13);

//        System.out.println("TEAM ID: "+ team.getTeamId());
        URIBuilder builder = new URIBuilder("http://localhost:8080/round/" + round.getRoundId() + "/" + team.getTeamId());

        builder.addParameter("roundScore", Integer.toString(round.getRoundScore()));
        builder.addParameter("spinScore", Integer.toString(round.getSpinScore()));
        builder.addParameter("spinToken", Boolean.toString(round.isSpinToken()));

        HttpPut updateRoundRequest = new HttpPut(builder.build());
        StringEntity updateGameEntity = new StringEntity(mapper.writeValueAsString(updateRoundRequest), ContentType.APPLICATION_JSON);
        updateRoundRequest.setEntity(updateGameEntity);

//        System.out.println(updateRoundRequest.toString());

        CloseableHttpResponse updateRoundResponse = httpClient.execute(updateRoundRequest);

        if (updateRoundResponse.getStatusLine().getStatusCode() != 200) {
            System.out.println("Round is not updated! Status code: " + updateRoundResponse.getStatusLine().getStatusCode() + " - " + updateRoundResponse.getStatusLine().getReasonPhrase());
        }

        HttpEntity responseEntity = updateRoundResponse.getEntity();
        RoundResponse roundResponse = mapper.readValue(EntityUtils.toString(responseEntity), new TypeReference<RoundResponse>() {
        });

//        System.out.println("RoundResponse: " + roundResponse.toString());
    }

    public static void getRoundByRoundIdTeamId() throws IOException {

//        round.setRoundId(63);
//        team.setTeamId(57);

        HttpGet getRoundByRoundIdTeamIdRequest = new HttpGet("http://localhost:8080/roundTeam/" + round.getRoundId() + "/" + team.getTeamId());

        CloseableHttpResponse getRoundByRoundIdTeamIdResponse = httpClient.execute(getRoundByRoundIdTeamIdRequest);
        HttpEntity responseEntity = getRoundByRoundIdTeamIdResponse.getEntity();

        if (getRoundByRoundIdTeamIdResponse.getStatusLine().getStatusCode() != 200) {
            System.out.println("Cannot get round with round ID of " + round.getRoundId() + ", team ID of " + team.getTeamId() + " Status code: " + getRoundByRoundIdTeamIdResponse.getStatusLine().getStatusCode() + " - " + getRoundByRoundIdTeamIdResponse.getStatusLine().getReasonPhrase());
        }

        RoundResponse roundResponse = mapper.readValue(EntityUtils.toString(responseEntity), new TypeReference<RoundResponse>() {
        });

//        System.out.println("RoundResponse: " + roundResponse.toString());
    }

    public static void playTheGame(int teamCounter) throws URISyntaxException, IOException {

//        game.setGameId(22);

//        String w = "ab";
//        word.setWord(w.toUpperCase());
        setWordToGuess();

        while (true) {

            System.out.println();
            System.out.println(wordToGuess);

            System.out.print("Guess a character: ");
            String guessedInput = scan.nextLine().trim().toUpperCase();
            char charGuessed = guessedInput.charAt(0);

//            spinTheWheel();
            guessChar(charGuessed, teamCounter);

//            System.out.println("controlWord: " + controlWord);
//            System.out.println("wordToGuess: " + wordToGuess);
            if (controlWord.compareTo(wordToGuess) == 0) {

                System.out.println(wordToGuess);
                System.out.println("*****");
                System.out.println("WIN");
                System.out.println("*****");
                break;
            }
        }

    }

    private static void guessChar(char charGuessed, int teamCounter) throws URISyntaxException, IOException {

        updateCharGuessedInGame(charGuessed);
        if (charGuessedSet.contains(charGuessed)) { // if char guessed already in the set

            System.out.println(charGuessed + " is already guessed. Next team turn to guess.");
            nextTeamFunction(teamCounter); // switch to next team
        } else {

            charGuessedSet.add(charGuessed);

            if (charInWordMap.containsKey(charGuessed)) {

                List<Integer> charGuessedList = charInWordMap.get(charGuessed);

                for (int i = 0; i < charGuessedList.size(); i++) {

                    wordToGuess.setCharAt(charGuessedList.get(i), charGuessed);
                }

            } else {
                // change team
                System.out.println("You guessed wrong! Next team turn to guess.");
                nextTeamFunction(teamCounter); // switch to next team
            }

//        System.out.println(wordToGuess);
        }
    }

    private static void nextTeamFunction(int teamCounter) throws URISyntaxException, IOException {

        System.out.println("Your team information: " + team.toString());
        System.out.println("-----");

//        System.out.println("teamList.size(): " + teamList.size());
        if (teamCounter + 1 < teamList.size()) {
            teamCounter++; // next team in the list turn
//            System.out.println("incrementing teamCounter: " + teamCounter);

        } else {
            teamCounter = 0; // start at 0 index if next team turn is bigger than the teamList size
//            System.out.println("resetting: " + teamCounter);
        }

        updateTeamInDB(); // update current team total score in DB
        updateRoundDB(); // update current round with current team in DB

        // change team instance to next team by getting next team info from db
//        System.out.println("teamCounter: " + teamCounter);
        getTeamByTeamId(teamList.get(teamCounter).getTeamId());
        System.out.println("Next team information: " + team.toString());

        getRoundByRoundIdTeamId(); // get round by round id and team id info from db
        // change round instance to using the round get from db
        round.setRoundId(RoundResponse.getRoundId().getRoundId());
        round.setTeamId(RoundResponse.getRoundId().getTeamId());
        round.setRoundScore(RoundResponse.getRoundScore());
        round.setSpinToken(RoundResponse.isSpinToken());
    }

    public static void setWordToGuess() {

        String wordChosen = word.getWord();

//        game.getWord().setWord(w.toUpperCase());
//        String wordChosen = game.getWord().getWord();

        /*
            setting spaces on in the wordChosen
         */
        for (int i = 0; i < wordChosen.length(); i++) {

            char currentChar = wordChosen.charAt(i);

            if (symbolsWithSpaceAfter.contains(currentChar)) { // if symbols allows space after
                controlWord.append(currentChar);

            } else if (symbolsNoSpaceAfter.contains(currentChar)) { // if symbols don't allow space after then check if char after is alphabet
                controlWord.append(currentChar);

                // if char after ' or - is an alphabet and if i < wordChosen length, add space after
                if ((i + 1) < wordChosen.length() && wordChosen.charAt(i + 1) >= 'A' && wordChosen.charAt(i + 1) <= 'Z') {
                    controlWord.append(wordChosen.charAt(i + 1));
                    i++;
                }
            } else if (i == 0) {
                controlWord.append(wordChosen.charAt(0));
            } else if (wordChosen.charAt(i) == ' ') {
                controlWord.append("  ");
            } else {
                controlWord.append(" " + currentChar);
            }
        }

        /*
            setting word to guess in the game
         */
        for (int i = 0; i < controlWord.length(); i++) {
            char currentChar = controlWord.charAt(i);

            if (!symbolsNoSpaceAfter.contains(currentChar) && !symbolsWithSpaceAfter.contains(currentChar) && currentChar != ' ') {
                if (!charInWordMap.containsKey(currentChar)) {
                    charInWordMap.put(currentChar, new ArrayList<>()); // instantiate arrayList at the first char
                    charInWordMap.get(currentChar).add(i); // add the first index to the value of map
                } else {
                    charInWordMap.get(currentChar).add(i);
                }

            } else if ((currentChar == '\'' || currentChar == '-') && (i + 1) < controlWord.length()) { // add character after ' because it will get skip
                char nextChar = controlWord.charAt(i + 1);

                if (!charInWordMap.containsKey(nextChar)) {
                    charInWordMap.put(nextChar, new ArrayList<>()); // instantiate arrayList at the first char
                    charInWordMap.get(nextChar).add(i + 1); // add the first index to the value of map
                } else {
                    charInWordMap.get(nextChar).add(i + 1);
                }
            }

            if (symbolsWithSpaceAfter.contains(currentChar)) { // if symbols allows space after
                wordToGuess.append(currentChar);
            } else if (symbolsNoSpaceAfter.contains(currentChar)) { // if symbols dont allows space after if next char is an alphabet
                wordToGuess.append(currentChar);

                // if char after ' or - is an alphabet and i + 1 must be less than the length of the controlWord, then add space after
                if ((i + 1) < controlWord.length() && controlWord.charAt(i + 1) >= 'A' && controlWord.charAt(i + 1) <= 'Z') {
                    wordToGuess.append("_");
                    i++;
                }
            } else if (controlWord.charAt(i) == ' ') {
                wordToGuess.append(" ");
            } else {
                wordToGuess.append("_");
            }
        }

//        System.out.println(charMap.toString());
//        System.out.println(controlWord);
//        System.out.println(controlWord.length() - 1);
//        System.out.println(wordToGuess);
//        System.out.println(wordToGuess.length() - 1);
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
