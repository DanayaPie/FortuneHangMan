import play.PlayGame;

public class CmdLineApp {
    public static void main(String[] args) throws Exception {
        System.out.println("===============================");
        System.out.println(" ________         $$$$$");
        System.out.println(" |      |        $  $  $");
        System.out.println(" $      |       $   $");
        System.out.println("/|\\     |        $  $");
        System.out.println(" |      |         $$$$$");
        System.out.println("/ \\     |           $  $");
        System.out.println("        |           $   $");
        System.out.println("        |        $  $  $");
        System.out.println("     -------      $$$$$");
        System.out.println(" ================================");
        System.out.println("|  Welcome to Fortune Hang Man!  |");
        System.out.println(" ================================");

        /*
            START GAME
         */
        PlayGame.addNewGame();


//        PlayGame.addTeam();

//        List<Integer> teamIds = new ArrayList() {{
//            add(5);
//            add(6);
//        }};
//        PlayGame.addRound(teamIds);

//        PlayGame.setRound();
//        PlayGame.chooseCategory();
//        PlayGame.updateGameDB();
//        PlayGame.updateWordInGameDB();
//        PlayGame.updateCurrentRoundInGameDB(2);
//        PlayGame.setWordToGuess();

        PlayGame.playTheGame(0);
//        PlayGame.updateRoundDb();
//        PlayGame.getRoundByRoundIdTeamId();
//        PlayGame.spinTheWheel();
//        PlayGame.getTeamsByGameId();


        /*
        GetRequests.getAllWordsRequest();
        GetRequests.getAllGamesRequest();
        GetRequests.getWordByWordId(3);

        PostRequests.addNewWord("fruit", "mango");
        PostRequests.addNewGame("GirlPower", 4);
         */
    }
}
