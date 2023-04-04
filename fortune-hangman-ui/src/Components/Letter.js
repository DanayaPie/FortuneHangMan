import { useState } from 'react';

function Letter(props) {

    //Lift this state up to game, handle changes then do visibility check in game
    const [letterVisibility, setLetterVisibility] = useState('');

    function letterVisibilityHandler(letter) {
        setLetterVisibility(letter.toUpperCase());
    }

    function checkLetterGuessed(){

        console.log("current Guessed " + props.lettersGuessed);
        if(props.lettersGuessed.includes(props.letter)){
            letterVisibilityHandler(props.letter)
        }
    }

    return (
        <div className="letter" onClick={checkLetterGuessed}>
            {letterVisibility}
        </ div>
    )
}

export default Letter;