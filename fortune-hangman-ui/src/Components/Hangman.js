import Word from "./Word";

function splitSentenceToWords(sentence) {
    return sentence.split(" ");
}

function Hangman(props) {
    const words = splitSentenceToWords(props.sentence)

    return (
        <div className='hangmanBox'>
            {/* Dynamic list of words needed */}
            {words.map((word) => (
                <Word 
                word={word}
                wordId={props.wordId}
                key={props.wordId + Math.random()}
                showGuessedLetters={props.showGuessedLetters} 
                />
            ))}
        </div>
    );
}


export default Hangman;