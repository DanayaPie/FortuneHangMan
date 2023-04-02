import model.Game;
import play.GameSetUp;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class CmdLineApp {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to Fortune Hang Man!");

//        GameSetUp.addNewGame();

//        GameSetUp.addTeam();

        List<Integer> teamIds = new ArrayList() {{
            add(12);
            add(13);
        }};
        GameSetUp.addRound(teamIds);

//        GetRequests.getAllWordsRequest();
//        GetRequests.getAllGamesRequest();
//        GetRequests.getWordByWordId(3);

//        PostRequests.addNewWord("fruit", "mango");
//        PostRequests.addNewGame("GirlPower", 4);
    }
}
