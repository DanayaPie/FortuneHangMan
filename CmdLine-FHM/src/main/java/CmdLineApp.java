import play.PlayGame;
import practice.GetRequests;
import practice.PostRequests;

import java.util.ArrayList;
import java.util.List;

public class CmdLineApp {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to Fortune Hang Man!");

//        PlayGame.addNewGame();

//        PlayGame.addTeam();

//        List<Integer> teamIds = new ArrayList() {{
//            add(12);
//            add(13);
//        }};
//        PlayGame.addRound(teamIds);

        PlayGame.chooseCategory();





        /*
        GetRequests.getAllWordsRequest();
        GetRequests.getAllGamesRequest();
        GetRequests.getWordByWordId(3);

        PostRequests.addNewWord("fruit", "mango");
        PostRequests.addNewGame("GirlPower", 4);
         */
    }
}
