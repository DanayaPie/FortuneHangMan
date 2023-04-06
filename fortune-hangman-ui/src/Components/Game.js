import { useState } from 'react';
import './Game.css'
import Hangman from './Hangman';
import TeamBox from './TeamBox';
import EnterSpinScore from './Forms/EnterSpinScore';
import InputOptions from './InputOptions';

function Game(props) {

    // const SYMBOL_REGEX = /[-!$%^&*()_+|~=`{}[]:";'<>?,.\\\/]/g;
    const SYMBOL_PATTERN = "-!$%^&*()_+|~=`{}:\";'<>?,.\\/";
    const VOWELS = "AEIOU";
    const VOWEL_PRICE = 250;

    const [currentLetterGuessed, setCurrentLetterGuessed] = useState('');
    const [currentPuzzleGuessed, setCurrentPuzzleGuessed] = useState('');
    const [pickedSentence, setPickedSentence] = useState(props.game.word.word);
    const [selectedSpinScore, setSelectedSpinScore] = useState('');
    const [spinScoreSubmitted, setSpinScoreSubmitted] = useState(false);

    //Handles two way binding of user input letter
    function currentLetterGuessHandler(event) {
        setCurrentLetterGuessed(event.target.value);
    }
    function currentPuzzleGuessHandler(event) {
        setCurrentPuzzleGuessed(event.target.value);
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
                    props.onLetterGuessed(currentLetterGuessed.toUpperCase());
                    //Vowels do not give money
                    pickedSentenceHandler(currentLetterGuessed.toUpperCase(), 0);
                    setCurrentLetterGuessed('');
                }
            } else {
                props.onLetterGuessed(currentLetterGuessed.toUpperCase());
                pickedSentenceHandler(currentLetterGuessed.toUpperCase(), teamRoundScore.spinScore);
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
            } else {
                console.log("Wrong!");
                props.changeTeamTurn();
            }
        }
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
            pickedSentenceHandler(char, 0)
            props.onLetterGuessed(char.toUpperCase());
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

    //pass in letter from letter of sentence, show object if true
    function showGuessedLetters(letter) {
        if (props.game.letterGuessed.includes(letter) || SYMBOL_PATTERN.includes(letter)) {
            return true;
        } else {
            return false
        }
    }

    function wordSolved() {
        if (pickedSentence != null && pickedSentence === "") {
            return true;
        }
        else return false;
    }
    return (

        <div className='game box'>


            <Hangman
                sentence={props.game.word.word.toUpperCase()}
                wordId={props.game.word.wordId}
                showGuessedLetters={showGuessedLetters}

            />

            {
                !wordSolved() ?
                    spinScoreSubmitted ?
                        <InputOptions currentLetterGuessHandler={currentLetterGuessHandler}
                            submitLetterGuessedHandler={submitLetterGuessedHandler}
                            currentLetterGuessed={currentLetterGuessed}
                            currentPuzzleGuessHandler={currentPuzzleGuessHandler}
                            submitPuzzleGuessedHandler={submitPuzzleGuessedHandler}
                            puzzleGuessed={currentPuzzleGuessed}
                        />
                        : <EnterSpinScore onSelectSpinScoreChange={currentSelectedSpinScoreHandler} onSpinScoreSubmit={submitSelectedSpinScoreHandler} onBadSpin={handleBadSpin} />
                    : <div className='btn btn-primary'> Continue </div>
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