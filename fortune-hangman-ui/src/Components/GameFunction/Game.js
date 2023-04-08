import { useState, useEffect } from 'react';
import './Game.css'
import Hangman from './Hangman';
import TeamBox from './TeamBox';
import Categories from './Categories';
import EnterSpinScore from '../Forms/EnterSpinScore';
import InputOptions from './InputOptions';
import WinningTeam from './WinningTeam';

function Game(props) {
    const [currentLetterGuessed, setCurrentLetterGuessed] = useState('');
    const [currentPuzzleGuessed, setCurrentPuzzleGuessed] = useState('');
    const [isCategoryPicked, setIsCategoryPicked] = useState(false);
    const [pickedSentence, setPickedSentence] = useState();
    const [selectedSpinScore, setSelectedSpinScore] = useState('');
    const [spinScoreSubmitted, setSpinScoreSubmitted] = useState(false);
    const [isGameOver, setIsGameOver] = useState(false);

    const SYMBOL_PATTERN = "-!$%^&*()_+|~=`{}:\";'<>?,.\\/";
    const VOWELS = "AEIOU";
    const VOWEL_PRICE = 250;
    const letterSet = new Set();

    useEffect(() => {
        while(props.game !== null || props.game !== undefined){
            [...props.game.letterGuessed].forEach((letter)=>{
                letterSet.add(letter)
            })
            break;
        }
    });

    useEffect(() => {
        const timeOut = setTimeout(() => {
            if ((pickedSentence != null && pickedSentence === "") || props.game.letterGuessed === pickedSentence) {
                props.onWordIsSolved(true);
            }
            else props.onWordIsSolved(false);
        }, 50);
        return () => {
            clearTimeout(timeOut);
        }
    }, [props.isWordSolved, pickedSentence]);

    //Handles two way binding of user input
    function currentLetterGuessHandler(event) {
        setCurrentLetterGuessed(event.target.value);
    }
    function currentPuzzleGuessHandler(event) {
        setCurrentPuzzleGuessed(event.target.value);
    }

    function endOfRoundOrEndGame() {
        if (props.game.currentRound >= props.game.totalTeam) {
            addRoundScoresToTotalScore();
            setIsGameOver(true);
        } else {
            console.log("Start New Round");
            let teamIds = props.teams.map((team) => team.teamId);
            addRoundScoresToTotalScore();
            props.postNewRoundsByTeam(teamIds)
        }
    }

    //On submit add letter to game data letter guessed. reset user input box
    function submitLetterGuessedHandler() {
        if (currentLetterGuessed !== '') {
            let currentTeam = props.teams.find((team) => team.teamTurn === props.game.currentTeamTurn).teamId
            let teamRoundScore = props.roundScores.find((roundScore) => roundScore.teamId === currentTeam)
            if (VOWELS.includes(currentLetterGuessed.toUpperCase())) {
                //Get current round score of current team
                if (teamRoundScore.roundScore >= VOWEL_PRICE) {
                    props.onRoundScoreUpdate(-VOWEL_PRICE, props.game.roundId, currentTeam);
                    props.onLetterGuessed(currentLetterGuessed.toUpperCase(), true);
                    letterSet.add(currentLetterGuessed);
                    //Vowels do not give money
                    pickedSentenceHandler(currentLetterGuessed.toUpperCase(), 0);
                    setCurrentLetterGuessed('');
                }
            } else {
                pickedSentenceHandler(currentLetterGuessed.toUpperCase(), teamRoundScore.spinScore);
                props.onLetterGuessed(currentLetterGuessed.toUpperCase(), true);
                letterSet.add(currentLetterGuessed.toUpperCase());
                setCurrentLetterGuessed('');
            }
        }
    }
    function submitPuzzleGuessedHandler() {
        if (currentPuzzleGuessed !== '') {
            let pickedWord = props.game.word.word.toUpperCase();
            if (currentPuzzleGuessed.toUpperCase() === pickedWord) {
                let currentTeam = props.teams.find((team) => team.teamTurn === props.game.currentTeamTurn).teamId
                let teamRoundScore = props.roundScores.find((roundScore) => roundScore.teamId === currentTeam)
                puzzleGuessedCorrectly(teamRoundScore.spinScore, currentTeam);
                setCurrentPuzzleGuessed('');
                setSpinScoreSubmitted(false);
            } else {
                setCurrentPuzzleGuessed('');
                //normally don't need to set this to false since default is false. 
                //Since there is no update at all on state this needs to be forced
                setSpinScoreSubmitted(false);
                props.changeTeamTurn();
            }
        }
    }

    function categoryPicked(category) {
        props.fetchRandomWordByCategory(category, setPickedSentence)
        setIsCategoryPicked(true);
    }

    //Remove letters from the picked sentence, add score by number of letters in string
    function pickedSentenceHandler(letter, spinScore) {
        let numberOfLetters = (pickedSentence.match(new RegExp(letter, "g")) || []).length;
        let currentTeam = props.teams.find((team) => team.teamTurn === props.game.currentTeamTurn).teamId
        if (numberOfLetters > 0) {
            setPickedSentence((prevSentence) => prevSentence.replaceAll(letter, '').trim());
        } else {
            props.changeTeamTurn();
        }
        props.onRoundScoreUpdate(numberOfLetters * spinScore, props.game.roundId, currentTeam);
        setSpinScoreSubmitted(false);
    }

    //Solving the Puzzle only adds the spin score once
    function puzzleGuessedCorrectly(spinScore, currentTeam) {
        Array.from(pickedSentence).forEach((char) => {
            props.onLetterGuessed(char.toUpperCase(), true);
            letterSet.add(char);
            pickedSentenceHandler(char, 0)
        });
        props.onRoundScoreUpdate(1 * spinScore, props.game.roundId, currentTeam);
    }

    function currentSelectedSpinScoreHandler(event) {
        setSelectedSpinScore(event.target.value);
    }

    //Update round score data's spin score
    function submitSelectedSpinScoreHandler() {
        if (selectedSpinScore !== "") {
            let currentTeam = props.teams.find((team) => team.teamTurn === props.game.currentTeamTurn).teamId
            props.onSpinScoreUpdate(selectedSpinScore, props.game.roundId, currentTeam);
            setSpinScoreSubmitted(true);
            setSelectedSpinScore("");
        }
    }

    function handleBadSpin(typeOfBadSpin) {
        if (typeOfBadSpin === "Lose") {
            props.changeTeamTurn();
        } else if (typeOfBadSpin === "Bankrupt") {
            let currentTeam = props.teams.find((team) => team.teamTurn === props.game.currentTeamTurn).teamId
            props.onRoundScoreReset(props.game.roundId, currentTeam);
            props.changeTeamTurn();
        }
    }

    function addRoundScoresToTotalScore() {
        props.roundScores.forEach((round) => {
            console.log("Why Not here?");
            console.log(round.teamId, round.roundScore);
            props.onTotalScoreUpdate(round.teamId, round.roundScore);
        });
    }

    //pass in letter from letter of sentence, show object if true
    function showGuessedLetters(letter) {
        if (props.game.letterGuessed.includes(letter) || SYMBOL_PATTERN.includes(letter)) {
            return true;
        } else {
            return false
        }
    }

    return (

        <div className='game box'>

            {!isGameOver ?
                (props.game.word !== null && props.game.word.word !== null && !props.isWordLoading) ?
                    isCategoryPicked ?
                        //Word is loaded and category picked and game is not over
                        <Hangman
                            sentence={props.game.word.word.toUpperCase()}
                            wordId={props.game.word.wordId}
                            showGuessedLetters={showGuessedLetters}
                        />
                        //category not picked
                        : <Categories
                            categories={props.categories}
                            onCategoryPicked={categoryPicked}
                        />
                    //category picked word is loading
                    :
                    <Categories
                        categories={props.categories}
                        onCategoryPicked={categoryPicked}
                    />
                : <WinningTeam
                    teams={props.teams}
                />
            }

            {isCategoryPicked ?
                !props.isWordSolved ?
                    spinScoreSubmitted ?
                        <InputOptions currentLetterGuessHandler={currentLetterGuessHandler}
                            submitLetterGuessedHandler={submitLetterGuessedHandler}
                            currentLetterGuessed={currentLetterGuessed}
                            currentPuzzleGuessHandler={currentPuzzleGuessHandler}
                            submitPuzzleGuessedHandler={submitPuzzleGuessedHandler}
                            currentPuzzleGuessed={currentPuzzleGuessed}
                        />
                        //if spin score not selected
                        : <EnterSpinScore onSelectSpinScoreChange={currentSelectedSpinScoreHandler} onSpinScoreSubmit={submitSelectedSpinScoreHandler} onBadSpin={handleBadSpin} />
                    //else the word is solved
                    : <div className='btn btn-primary' onClick={endOfRoundOrEndGame}> Continue </div>
                //else category not picked
                : <h1> Round {props.game.currentRound} <br /> Select Your Category</h1>
            }

            <TeamBox
                teams={props.teams}
                roundScores={props.roundScores}
                currentTeamTurn={props.game.currentTeamTurn}
            />
        </div>
    );
}


export default Game;