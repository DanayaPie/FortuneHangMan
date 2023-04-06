import { useState } from 'react';
import GuessLetter from './Forms/GuessLetter';
import SolveThePuzzle from './Forms/SolveThePuzzle';

function InputOptions(props) {
    const [guessLetterSelected, setGuessLetterSelected] = useState(false);
    const [solvePuzzleSelected, setSolvePuzzleSelected] = useState(false);

    function selectLetter() {
        setGuessLetterSelected(!guessLetterSelected);
    }

    function selectPuzzle() {
        setSolvePuzzleSelected(!solvePuzzleSelected);
    }

    return (
        <div className='optionsBox'>

            {!guessLetterSelected && !solvePuzzleSelected ?
                <>
                    <div className='btn btn-primary btn-lg' onClick={selectLetter}>
                        Guess A letter/Buy A Vowel
                    </div>
                    <div className='btn btn-primary btn-lg' onClick={selectPuzzle}>
                        Solve the Puzzle
                    </div>
                </>
                : ""
            }

            {guessLetterSelected && !solvePuzzleSelected ?
                <GuessLetter onLetterGuessedChange={props.currentLetterGuessHandler} onLetterSubmit={props.submitLetterGuessedHandler} letterGuessed={props.currentLetterGuessed} />
                : ""
            }
            {!guessLetterSelected && solvePuzzleSelected ?
                <SolveThePuzzle onPuzzleGuessChange={props.currentPuzzleGuessHandler} onPuzzleGuessSubmit={props.submitPuzzleGuessedHandler} puzzleGuessed={props.currentPuzzleGuessed} />
                : ""
            }
            
        </div>
    );
}

export default InputOptions;