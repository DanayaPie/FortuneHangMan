import '../App.css';
import Game from '../Components/Game';
import { useState } from 'react';


function GamePage() {

    const [game, setGame] = useState({
        gameId: 1,
        gameName: "Fun Time",
        roundId: 1,
        wordId: 1,
        gameStatus: "In Progress",
        letterGuessed: "",
        totalTeam: 2,
        currentTeamTurn: 1,
        currentRound: 1
    });
    const DUMMY_TEAMS = [
        {
            teamId: 10,
            teamName: "Jumbos",
            teamTurn: 1,
            gameId: 1,
            totalScore: 0
        },
        {
            teamId: 11,
            teamName: "Mumbos",
            teamTurn: 2,
            gameId: 1,
            totalScore: 0
        }
    ]

    const DUMMY_ROUNDS = [
        {
            roundId: 1,
            teamId: 10,
            gameId: 1,
            roundScore: 0,
            spinScore: 0
        },
        {
            roundId: 1,
            teamId: 11,
            gameId: 1,
            roundScore: 0,
            spinScore: 0
        }
    ]

    const DUMMY_WORDS = [
        {
            wordId:1,
            category:"Fruits",
            word:"Mango"
        },
        {
            wordId:2,
            category:"Fruits",
            word:"Apple"
        },
        {
            wordId:3,
            category:"Fruits",
            word:"Kiwi"
        },
        {
            wordId:4,
            category:"Anime Titles",
            word:"WorldEnd: What Do You Do At The End Of The World? Are You Busy? Will You Save Us?"
        },
        {
            wordId:5,
            category:"Anime Titles",
            word:"The Misfit Of Demon King Academy: History's Strongest Demon King Reincarnates & Goes To School With His Descendants"
        },
        {
            wordId:6,
            category:"Anime Titles",
            word:"Suppose A Kid From The Last Dungeon Boonies Moved To A Starter Town"
        }

    ]

    const [teams, setTeams] = useState({ DUMMY_TEAMS });

    const updateTeamsHandler = teams => {
        setTeams((prevTeams) => {
            return [teams, ...prevTeams];
        })
    }

    const [roundScores, setRoundScores] = useState({ DUMMY_ROUNDS });

    const updateRoundScoreHandler = roundScores => {
        setRoundScores((prevRoundScores) => {
            return [roundScores, ...prevRoundScores];
        })
    }

    // const updateGameIdHandler = (event) => {
    //     setGame((prevState) => {
    //         return { ...prevState, gameId: event.target.value }
    //     });
    // };

    // const updateGameNameHandler = (event) => {
    //     setGame((prevState) => {
    //         return { ...prevState, gameName: event.target.value }
    //     });
    // };

    const updateRoundIdHandler = (event) => {
        setGame((prevState) => {
            return { ...prevState, roundId: event.target.value }
        });
    };

    const updateWordIdHandler = (event) => {
        setGame((prevState) => {
            return { ...prevState, wordId: event.target.value }
        });
    };

    const updateGameStatusHandler = (event) => {
        setGame((prevState) => {
            return { ...prevState, gameStatus: event.target.value }
        });
    };

    const updateLetterGuessedHandler = (letter) => {
        console.log(letter)
        setGame((prevState) => {
            return { ...prevState, letterGuessed: prevState.letterGuessed.concat(letter) }
        });
    };

    // const updateTotalTeamHandler = (event) => {
    //     setGame((prevState) => {
    //         return { ...prevState, totalTeam: event.target.value }
    //     });
    // };

    const updateCurrentTeamTurnHandler = (event) => {
        setGame((prevState) => {
            return { ...prevState, currentTeamTurn: event.target.value }
        });
    };

    const updateCurrentRoundHandler = (event) => {
        setGame((prevState) => {
            return { ...prevState, currentRound: event.target.value }
        });
    };

    return (
        <div className='App'>
            <Game game={game}
                teams={teams}
                roundScores={roundScores}
                words={DUMMY_WORDS}
                onLetterGuessed={updateLetterGuessedHandler}
                onChangeTeamTurn={updateCurrentTeamTurnHandler}
                onNewRound={{ updateRoundIdHandler, updateCurrentRoundHandler, updateWordIdHandler, updateCurrentTeamTurnHandler }}
                onTeamUpdate={updateTeamsHandler}
                onGameStatusChange={updateGameStatusHandler}
                onRoundScoreUpdate={updateRoundScoreHandler}
            />
        </div>
    );
}
export default GamePage;