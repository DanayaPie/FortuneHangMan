import '../App.css';
import Game from '../Components/Game';
import { useState, useEffect } from 'react';


function GamePage() {

    const [game, setGame] = useState();
    const [teams, setTeams] = useState();
    const [words, setWords] = useState();
    const [isGameLoading, setIsGameLoading] = useState(false);
    const [isTeamsLoading, setIsTeamsLoading] = useState(false);
    const [isWordsLoading, setIsWordsLoading] = useState(false);
    const [error, setError] = useState(false);

    useEffect(() => {
        fetchGameDataByGameId(1);
        fetchTeamDataByGameId(1);
        fetchWordData();
    }, []);

    async function fetchGameDataByGameId(gameId) {
        try {
            setIsGameLoading(true);
            const gameResponse = await fetch(`http://localhost:8080/game/${gameId}`);
            const gameData = await gameResponse.json();
            if (!gameResponse.ok) {
                throw new Error(gameResponse.Error);
            }
            const mappedGame = {
                gameId: gameData.gameId,
                gameName: gameData.gameName,
                roundId: gameData.roundId,
                word: gameData.word,
                gameStatus: gameData.gameStatus,
                letterGuessed: gameData.letterGuessed,
                totalTeam: gameData.totalTeam,
                currentTeamTurn: gameData.currentTeamTurn,
                currentRound: gameData.currentRound
            }
            if(mappedGame.letterGuessed === null) mappedGame.letterGuessed = "";
            console.log(mappedGame);
            setGame({ ...mappedGame });
            setIsGameLoading(false);
        }
        catch (error) {
            console.log(error);
            setError(true);
        }
    }

    async function fetchTeamDataByGameId(gameId) {
        setIsTeamsLoading(true);
        const teamResponse = await fetch(`http://localhost:8080/team/game/${gameId}`);
        const teamDataArray = await teamResponse.json();
        const mappedTeams = teamDataArray.map((teamData) => {
            return {
                teamId: teamData.teamId,
                teamName: teamData.teamName,
                teamTurn: teamData.teamTurn,
                gameId: teamData.gameId,
                totalScore: 0
            }
        })
        console.log(mappedTeams);
        setTeams([...mappedTeams]);
        setIsTeamsLoading(false);
    }

    async function fetchWordData() {
        setIsWordsLoading(true);
        const wordResponse = await fetch(`http://localhost:8080/word`);
        const wordDataArray = await wordResponse.json();
        const mappedWords = wordDataArray.map((wordData) => {
            return {
                wordId: wordData.wordId,
                category: wordData.category,
                word: wordData.word
            }
        })
        console.log(mappedWords);
        setWords([...mappedWords]);
        setIsWordsLoading(false);
    }

    const DUMMY_ROUNDS = [
        {
            roundId: 1,
            teamId: 1,
            gameId: 1,
            roundScore: 0,
            spinScore: 0
        },
        {
            roundId: 1,
            teamId: 2,
            gameId: 1,
            roundScore: 0,
            spinScore: 0
        }
    ]

    const [roundScores, setRoundScores] = useState([...DUMMY_ROUNDS]);


    //Game Updates ***********************
    const updateLetterGuessedHandler = (letter) => {
        setGame((prevState) => {
            return { ...prevState, letterGuessed: prevState.letterGuessed.concat(letter) }
        });
    };

    //Updates current team turn of a round already started
    const updateCurrentTeamTurnHandler = () => {
        setGame((prevState) => {
            if (prevState.currentTeamTurn === prevState.totalTeam) {
                return { ...prevState, currentTeamTurn: 1 }
            } else {
                return { ...prevState, currentTeamTurn: prevState.currentTeamTurn + 1 }
            }
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



    const updateCurrentRoundHandler = (event) => {
        setGame((prevState) => {
            return { ...prevState, currentRound: event.target.value }
        });
    };

    //Updates Round score 
    const updateRoundScoreHandler = (numberToAdd, roundId, teamId) => {
        setRoundScores((prevRoundScores) => {
            const updatedRoundScore = prevRoundScores.map((roundScore) => {
                if (roundScore.roundId === roundId && roundScore.teamId === teamId) {
                    return { ...roundScore, roundScore: roundScore.roundScore + numberToAdd };
                }
                return roundScore
            })
            return [...updatedRoundScore];
        })
    }
    const resetRoundScoreHandler = (roundId, teamId) => {
        setRoundScores((prevRoundScores) => {
            const updatedRoundScore = prevRoundScores.map((roundScore) => {
                if (roundScore.roundId === roundId && roundScore.teamId === teamId) {
                    return { ...roundScore, roundScore: 0 };
                }
                return roundScore
            })
            return [...updatedRoundScore];
        })
    }

    const updateSpinScoreHandler = (spinScore, roundId, teamId) => {
        setRoundScores((prevRoundScores) => {
            const updatedSpinScore = prevRoundScores.map((roundScore) => {
                if (roundScore.roundId === roundId && roundScore.teamId === teamId) {
                    return { ...roundScore, spinScore: spinScore };
                }
                return roundScore
            })
            return [...updatedSpinScore];
        })
    }

    const updateRoundIdHandler = (event) => {
        setGame((prevState) => {
            return { ...prevState, roundId: event.target.value }
        });
    };


    const updateTeamsHandler = teams => {
        setTeams((prevTeams) => {
            return [teams, ...prevTeams];
        })
    }

    return (
        <div className='App'>
            {isGameLoading !== true && isTeamsLoading !== true && isWordsLoading !== true && game !== undefined &&
                <Game
                    game={game}
                    teams={teams}
                    roundScores={roundScores}
                    onLetterGuessed={updateLetterGuessedHandler}
                    changeTeamTurn={updateCurrentTeamTurnHandler}
                    onNewRound={{ updateRoundIdHandler, updateCurrentRoundHandler, updateWordIdHandler, updateCurrentTeamTurnHandler }}
                    onTeamUpdate={updateTeamsHandler}
                    onGameStatusChange={updateGameStatusHandler}
                    onRoundScoreUpdate={updateRoundScoreHandler}
                    onRoundScoreReset={resetRoundScoreHandler}
                    onSpinScoreUpdate={updateSpinScoreHandler}
                />

            }
            {isGameLoading && isTeamsLoading && isWordsLoading && <p>Loading</p>}
            {error && <p>error</p>}

        </div>
    );
}
export default GamePage;