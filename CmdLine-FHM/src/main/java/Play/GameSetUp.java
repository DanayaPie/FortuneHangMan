package Play;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Game;
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
import java.util.Scanner;

public class GameSetUp {

    public static Game game = new Game();
    public static Scanner scan = new Scanner(System.in);


    // support async or sync connection
    final static CloseableHttpClient httpClient = HttpClients.createDefault();

    public static void addNewGame() throws IOException {

        System.out.print("Game Name: ");
        String gameName = scan.next();
        game.setGameName(gameName);

        System.out.print("Total Number of Team: ");
        int totalTeam = scan.nextInt();
        game.setTotalTeam(totalTeam);

        /*
            Request
         */
        HttpPost addGameRequest = new HttpPost("http://localhost:8080/game");
        ObjectMapper mapper = new ObjectMapper();
        StringEntity gameToAddJson = new StringEntity(mapper.writeValueAsString(game)
                , ContentType.APPLICATION_JSON);
        addGameRequest.setEntity(gameToAddJson);

        /*
            Response
         */
        CloseableHttpResponse addedGameResponse = httpClient.execute(addGameRequest);

        if (addedGameResponse.getStatusLine().getStatusCode() != 200) {
            {
                System.out.println("Game is not added! Status code: " + addedGameResponse.getStatusLine().getStatusCode());
            }
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

        for (int i = 0; i < totalTeam; i++) {

            System.out.print("Team " + i + 1 + " Name: ");
            teamName = scan.next();
            teamTurn = i + 1;

            Team teamToAdd = new Team();
            teamToAdd.setTeamName(teamName);
            teamToAdd.setTeamTurn(teamTurn);

            HttpPost addTeamRequest = new HttpPost("http://localhost:8080/team");
            ObjectMapper mapper = new ObjectMapper();
            StringEntity teamToAddJson = new StringEntity(mapper.writeValueAsString(teamToAdd)
                    , ContentType.APPLICATION_JSON);
            addTeamRequest.setEntity(teamToAddJson);

            CloseableHttpResponse addedTeamResponse = httpClient.execute(addTeamRequest);

            if (addedTeamResponse.getStatusLine().getStatusCode() != 200) {
                System.out.println("Team is not added! Status code: " + addedTeamResponse.getStatusLine().getStatusCode());
            }

            HttpEntity responseEntity = addedTeamResponse.getEntity();

            if (teamToAdd.getTeamTurn() == 1) {
                addRound(teamToAdd.getTeamId(), game.getGameId());
            } else {
                continue;
            }
        }
    }

    private static void addRound(int teamId, int gameId) {

    }


}
