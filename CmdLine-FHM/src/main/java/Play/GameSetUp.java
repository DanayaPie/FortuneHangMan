package play;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import model.Game;
import model.Round;
import model.Team;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.*;

public class GameSetUp {

    private static Scanner scan = new Scanner(System.in);
    private static ObjectMapper mapper = new ObjectMapper();
    private static HttpClient client = HttpClient.newHttpClient();
    private static Gson gson = new Gson();
    private static Game game = new Game();

    // support async or sync connection
    final static CloseableHttpClient httpClient = HttpClients.createDefault();

    public static void addNewGame() throws IOException {

        System.out.print("Game Name: ");
        String gameName = scan.nextLine();
        game.setGameName(gameName);

        System.out.print("Total Number of Team: ");
        int totalTeam = Integer.parseInt(scan.nextLine());
        game.setTotalTeam(totalTeam);

        /*
            Request
         */
        HttpPost addGameRequest = new HttpPost("http://localhost:8080/game");
        StringEntity gameToAdd = new StringEntity(mapper.writeValueAsString(game)
                , ContentType.APPLICATION_JSON);
        addGameRequest.setEntity(gameToAdd);

        /*
            Response
         */
        CloseableHttpResponse addedGameResponse = httpClient.execute(addGameRequest);

        if (addedGameResponse.getStatusLine().getStatusCode() != 200) {
            {
                System.out.println("Game is not added! Status code: " + addedGameResponse.getStatusLine().getStatusCode());
            }
        } else {
            HttpEntity responseEntity = addedGameResponse.getEntity();
            mapper.readValue(EntityUtils.toString(responseEntity), new TypeReference<Game>() {
            });

            addTeam();
        }
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
            StringEntity teamToAddJson = new StringEntity(mapper.writeValueAsString(teamToAdd)
                    , ContentType.APPLICATION_JSON);
            addTeamRequest.setEntity(teamToAddJson);

            CloseableHttpResponse addedTeamResponse = httpClient.execute(addTeamRequest);

            if (addedTeamResponse.getStatusLine().getStatusCode() != 200) {
                System.out.println("Team is not added! Status code: " + addedTeamResponse.getStatusLine().getStatusCode());
            }

            HttpEntity responseEntity = addedTeamResponse.getEntity();
            Team teamAdded = mapper.readValue(EntityUtils.toString(responseEntity)
                    , new TypeReference<Team>() {
                    });

            teamAdded.getTeamId();
            System.out.println("teamAdded.getTeamId(): " + teamAdded.getTeamId());
            teamIds.add(teamAdded.getTeamId());
            System.out.println("teamIds: " + teamIds);
        }

        addRound(teamIds);
    }

    public static void addRound(List<Integer> teamIds) throws IOException {

        Round roundToAdd = new Round();
        roundToAdd.setTeamIds(teamIds);
        roundToAdd.setGameId(game.getGameId());

        HttpPost addRoundRequest = new HttpPost("http://localhost:8080/round");
        StringEntity roundToAddJson = new StringEntity(mapper.writeValueAsString(roundToAdd)
                , ContentType.APPLICATION_JSON);

        System.out.println("roundToAddJson");
        addRoundRequest.setEntity(roundToAddJson);

        CloseableHttpResponse addedRoundResponse = httpClient.execute(addRoundRequest);
        System.out.println("executed");

        if (addedRoundResponse.getStatusLine().getStatusCode() != 200) {
            System.out.println("Round is not added! Status code: " + addedRoundResponse.getStatusLine().getStatusCode() + " - " + addedRoundResponse.getEntity());
        } else {
            HttpEntity responseEntity = addedRoundResponse.getEntity();
            Map<String, String> map = gson.fromJson(responseEntity.toString(), HashMap.class);

            System.out.println(map);

//            Round addedRound = mapper.readValue(EntityUtils.toString(responseEntity)
//                    , new TypeReference<Round>() {
//                    });
        }
    }
}
