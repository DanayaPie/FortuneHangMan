import '../App.css';
import Game from '../Components/GameFunction/Game';
import { useState, useEffect } from 'react';


function GamePage() {
    const [game, setGame] = useState();
    const [teams, setTeams] = useState();
    const [categories, setCategories] = useState();
    const [words, setWords] = useState([]);
    const [roundScores, setRoundScores] = useState();
    const [isGameLoading, setIsGameLoading] = useState(true);
    const [isTeamsLoading, setIsTeamsLoading] = useState(true);
    const [isRoundsLoading, setIsRoundsLoading] = useState(true);
    const [isCategoriesLoading, setIsCategoriesLoading] = useState(true);
    const [isWordLoading, setIsWordLoading] = useState(false);
    const [error, setError] = useState(false);
    const SYMBOL_REGEX = /[-!$%^&*()_+|~=`{}[]:";'<>?,.\/]/g;
    const [isWordSolved, setIsWordSolved] = useState();
    const [wordAlreadyUsed, setWordsAlreadyUsed] = useState(new Set());
    //TODO dynamically set this later
    const gameId = 1;

    useEffect(() => {
        console.info(`Initializing Game with gamedId:[${gameId}]`);
            if (gameId !== null || game === null || game === undefined) {
                fetchTeamDataByGameId(gameId, fetchGameDataByGameId);
                fetchCategoryData();
            } 
    },[]);

    useEffect(() => {
        const timeOut = setTimeout(() => {
                if (game.word !== null) {
                    console.info(`Updating Game Record with GameId:[${gameId}]`);
                    updateGameTable(game);
                }
                if (teams !== null || teams !== undefined) {
                    updateTeamTable(teams);
                }
                if (roundScores !== null || roundScores !== undefined) {
                    updateRoundTable(roundScores);
                }
            // }
        }, 600);
        return () => {
            clearTimeout(timeOut);
        }
    }, [game, teams, roundScores]);

    async function fetchGameDataByGameId(gameId, teams) {
        setIsGameLoading(true);
        try {
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
            if (mappedGame.letterGuessed === null) mappedGame.letterGuessed = "";
            if (mappedGame.roundId === null) {
                let teamIds = teams.map((team) => team.teamId);
                postNewRoundsByTeamAndGameId(teamIds, gameId);
            } else {
                //after game is loaded load rounds by round id
                fetchRoundDataByRoundId(mappedGame.roundId);
            }
            setGame({ ...mappedGame });
            setIsGameLoading(false);
        }
        catch (error) {
            console.error(error);
            setError(true);
        }
    }

    async function fetchTeamDataByGameId(gameId, gameDataCallback) {
        setIsTeamsLoading(true);
        const teamResponse = await fetch(`http://localhost:8080/team/game/${gameId}`);
        const teamDataArray = await teamResponse.json();
        const mappedTeams = teamDataArray.map((teamData) => {
            return {
                teamId: teamData.teamId,
                teamName: teamData.teamName,
                teamTurn: teamData.teamTurn,
                gameId: teamData.gameId,
                totalScore: teamData.totalScore
            }
        })
        setTeams([...mappedTeams]);
        setIsTeamsLoading(false);
        gameDataCallback(gameId, mappedTeams);
    }

    async function fetchCategoryData() {
        setIsCategoriesLoading(true);
        const wordResponse = await fetch(`http://localhost:8080/category`);
        const wordDataArray = await wordResponse.json();
        setCategories([...wordDataArray]);
        setIsCategoriesLoading(false);
    }

    async function fetchRandomWordByCategory(category, callback) {
        setIsWordLoading(true);
        const wordResponse = await fetch(`http://localhost:8080/randWord/${category}`);
        const wordData = await wordResponse.json();
        const mappedWord = {
            wordId: wordData.wordId,
            word: wordData.word,
            category: wordData.category
        }
        if(!wordAlreadyUsed.has(mappedWord.wordId)){
            setWords([...words], mappedWord);
            setGame((prevState) => {
                return { ...prevState, word: mappedWord }
            });
            callback(mappedWord.word.replace(SYMBOL_REGEX, ""))
            setWordsAlreadyUsed((prevSet) => new Set(prevSet.add(mappedWord.wordId)))
            setIsWordLoading(false);
            console.debug(`Current Phrase being used [${mappedWord.word}]`)
        }else{
            fetchRandomWordByCategory(category, callback)
        }
    }


    async function fetchRoundDataByRoundId(roundId) {
        setIsRoundsLoading(true);

        const roundResponse = await fetch(`http://localhost:8080/round/${roundId}`);
        const roundDataArray = await roundResponse.json();
        const mappedRounds = roundDataArray.map((roundData) => {
            return {
                roundId: roundData.roundId.roundId,
                teamId: roundData.roundId.teamId,
                gameId: roundData.roundId.gameId,
                roundScore: roundData.roundScore,
                spinScore: roundData.spinScore,
                spinToken: roundData.spinToken
            }
        })
        setRoundScores([...mappedRounds]);
        setIsRoundsLoading(false);
    }



    async function updateGameTable(game) {
        await fetch(`http://localhost:8080/game/${game.gameId}?${new URLSearchParams({
            roundId: game.roundId,
            wordId: game.word.wordId,
            gameStatus: game.gameStatus,
            letterGuessed: game.letterGuessed,
            currentTeamTurn: game.currentTeamTurn,
            currentRound: game.currentRound
        })}`, {
            method: "PUT",
            headers: {
                "Content-type": "application/json;"
            }
        });
    }

    async function updateRoundTable(rounds) {
        for (let i = 0; i < rounds.length; i++) {
            let round = rounds[i];
            await fetch(`http://localhost:8080/round/${round.roundId}/${round.teamId}?${new URLSearchParams({
                spinScore: round.spinScore,
                roundScore: round.roundScore,
                // spinToken:  false
            })}`, {
                method: "PUT",
                headers: {
                    "Content-type": "application/json;"
                }
            });
        }
    }

    async function updateTeamTable(teams) {
        for (let i = 0; i < teams.length; i++) {
            let team = teams[i];
            await fetch(`http://localhost:8080/team/${team.teamId}?${new URLSearchParams({
                gameId: team.gameId,
                totalScore: team.totalScore
            })}`, {
                method: "PUT",
                headers: {
                    "Content-type": "application/json;"
                }
            });
        }
    }


    //state of game does not exist so gameId has to be passed in, does not increment round number
    async function postNewRoundsByTeamAndGameId(teamIdArray, gameId) {
        setIsRoundsLoading(true);
        const roundResponse = await fetch(`http://localhost:8080/round`, {
            method: "POST",
            body: JSON.stringify({
                teamIds: teamIdArray,
                gameId: gameId
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        });
        const roundDataArray = await roundResponse.json();
        const mappedRounds = await roundDataArray.map((roundData) => {
            return {
                roundId: roundData.roundId.roundId,
                teamId: roundData.roundId.teamId,
                gameId: roundData.roundId.gameId,
                roundScore: roundData.roundScore,
                spinScore: roundData.spinScore,
                spinToken: roundData.spinToken
            }
        })
        setRoundScores([...mappedRounds]);
        setGame((prevState) => {
            return { ...prevState, roundId: mappedRounds[0].roundId, letterGuessed: "", gameStatus: "In Progress",currentTeamTurn: 1}
        });
        setIsRoundsLoading(false);
    }
    //When State of game Exists this method to create rounds can be used
    async function postNewRoundsByTeam(teamIdArray) {
        setIsRoundsLoading(true);
        const roundResponse = await fetch(`http://localhost:8080/round`, {
            method: "POST",
            body: JSON.stringify({
                teamIds: teamIdArray,
                gameId: game.gameId
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        });
        const roundDataArray = await roundResponse.json();
        const mappedRounds = await roundDataArray.map((roundData) => {
            return {
                roundId: roundData.roundId.roundId,
                teamId: roundData.roundId.teamId,
                gameId: roundData.roundId.gameId,
                roundScore: roundData.roundScore,
                spinScore: roundData.spinScore,
                spinToken: roundData.spinToken
            }
        })
        setRoundScores([...mappedRounds]);
        setGame((prevState) => {
            return { ...prevState, roundId: mappedRounds[0].roundId, currentRound: prevState.currentRound + 1,currentTeamTurn:prevState.currentTeamTurn +1, letterGuessed: "", gameStatus: "In Progress" }
        });
        setIsRoundsLoading(false);
    }



    //Game Updates ***********************
    const updateLetterGuessedHandler = (letter) => {
        setGame((prevState) => {
            if (!prevState.letterGuessed.includes(letter)) {
                return { ...prevState, letterGuessed: prevState.letterGuessed.concat(letter) }
            }
            return prevState;
        });
    };

    const isWordSolvedHandler = (status) => {
        setIsWordSolved(status);
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


    const updateTotalScoreHandler = (teamId, roundScore) => {
        console.info(`Score updating for team ${teamId} with score ${roundScore}`);
        setTeams((prevTeams) => {
            const updatedTotalScore = prevTeams.map((team) => {
                if (team.teamId === teamId) {
                    return { ...team, totalScore: team.totalScore + roundScore };
                }
                return team
            })
            return [...updatedTotalScore];
        })
    }

    return (
        <div className='App'>
            {!isGameLoading && !isTeamsLoading && !isCategoriesLoading && !isRoundsLoading && game !== undefined &&
                <Game
                    game={game}
                    teams={teams}
                    categories={categories}
                    roundScores={roundScores}
                    isWordLoading={isWordLoading}
                    isWordSolved={isWordSolved}
                    onWordIsSolved={isWordSolvedHandler}
                    onLetterGuessed={updateLetterGuessedHandler}
                    changeTeamTurn={updateCurrentTeamTurnHandler}
                    onNewRound={{ updateRoundIdHandler, updateCurrentRoundHandler, updateWordIdHandler, updateCurrentTeamTurnHandler }}
                    onTotalScoreUpdate={updateTotalScoreHandler}
                    onGameStatusChange={updateGameStatusHandler}
                    onRoundScoreUpdate={updateRoundScoreHandler}
                    onRoundScoreReset={resetRoundScoreHandler}
                    onSpinScoreUpdate={updateSpinScoreHandler}
                    fetchRandomWordByCategory={fetchRandomWordByCategory}
                    postNewRoundsByTeam={postNewRoundsByTeam}
                />
            }
            {isGameLoading && isTeamsLoading && isCategoriesLoading && isRoundsLoading && !error && <p>Loading</p>}
            {error && <p>error</p>}

        </div>
    );
}
export default GamePage;