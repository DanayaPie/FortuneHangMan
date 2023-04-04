import { useState } from 'react';
import './Game.css'
import Hangman from './Hangman';
import GuessLetter from './Forms/GuessLetter';
import TeamBox from './TeamBox';

function Game(props) {

    const [currentLetterGuessed, setCurrentLetterGuessed] = useState('');
    const [pickedSentence, setPickedSentence] = useState(props.words[5].word.toUpperCase());

    //Handles two way binding of user input letter
    function currentLetterGuessHandler(event) {
        setCurrentLetterGuessed(event.target.value);
    }

    //Remove letters from the picked sentence, add score by number of letters in string
    function pickedSentenceHandler(letter) {
        let numberOfLetters = (pickedSentence.match(new RegExp(letter, "g")) || []).length; 
        setPickedSentence((prevSentence) => prevSentence.replaceAll(letter,'').trim());
        //TODO fix this later
        let roundId = 1;
        let teamId = 11;
        let spinScore = 100;
        props.onRoundScoreUpdate(numberOfLetters*spinScore,roundId,teamId);
    }

    //On submit add letter to game data letter guessed. reset user input box
    function submitLetterGuessedHandler() {
        if (currentLetterGuessed !== '' && !props.game.letterGuessed.includes(currentLetterGuessed.toUpperCase().trim())) {
            props.onLetterGuessed(currentLetterGuessed.toUpperCase());
            pickedSentenceHandler(currentLetterGuessed.toUpperCase());
            setCurrentLetterGuessed('');
        }
    }

    //pass in letter from letter of sentence, show object if true
    function showGuessedLetters(letter) {
        if (props.game.letterGuessed.includes(letter)) {
            return true;
        } else {
            return false
        }
    }

    function wordSolved(){
        if(pickedSentence != null && pickedSentence === ""){
            return true;
        }
        else return false;
    }
    return (
        <div className='game box'>

            <Hangman
                sentence={props.words[5].word}
                wordId={props.words[5].wordId}
                showGuessedLetters={showGuessedLetters}
            />

            <GuessLetter onLetterGuessedChange={currentLetterGuessHandler} onLetterSubmit={submitLetterGuessedHandler} letterGuessed={currentLetterGuessed} />
            {wordSolved()? "You Win": ""}

            <TeamBox
                teams={props.teams}
                roundScores={props.roundScores}
            />
        </div>
    );
}


export default Game;