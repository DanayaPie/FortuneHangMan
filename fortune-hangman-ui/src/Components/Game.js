import { useState } from 'react';
import './Game.css'
import Hangman from './Hangman';
import TeamCard from './TeamCard';
import GuessLetter from './Forms/GuessLetter';

function Game(props) {

    const [currentLetterGuessed, setCurrentLetterGuessed] = useState('');

    function currentLetterGuessHandler(event) {
        // console.log("Letter changed "+event.target.value)
        setCurrentLetterGuessed(event.target.value);
    }

    function submitLetterGuessedHandler() {
        if(currentLetterGuessed !== '' && !props.game.letterGuessed.includes(currentLetterGuessed.toUpperCase())){
            props.onLetterGuessed(currentLetterGuessed.toUpperCase());
            setCurrentLetterGuessed('');
        }
    }

    return (
        <div className='game box'>


            <Hangman sentence={props.words[5].word} wordId={props.words[5].wordId} lettersGuessed={props.game.letterGuessed}/>

            <GuessLetter onLetterGuessedChange={currentLetterGuessHandler} onLetterSubmit={submitLetterGuessedHandler} letterGuessed={currentLetterGuessed}/>

            <div className='teamBox'>
                <TeamCard />
                <TeamCard />
                <TeamCard />
                <TeamCard />
            </div>

        </div>
    );
}


export default Game;